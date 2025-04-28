
Deployment Manual (DM Guide) for quiz.antiochorthodox.at

---

# 1. NGINX Configuration (/etc/nginx/sites-available/quiz.antiochorthodox.at)

server {
    listen 80;
    server_name quiz.antiochorthodox.at;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl;
    server_name quiz.antiochorthodox.at;

    ssl_certificate /etc/letsencrypt/live/quiz.antiochorthodox.at/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/quiz.antiochorthodox.at/privkey.pem;
    include /etc/letsencrypt/options-ssl-nginx.conf;
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem;

    location / {
        proxy_pass https://localhost:8083;
        proxy_http_version 1.1;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}

Commands:
sudo nginx -t
sudo systemctl reload nginx

---

# 2. Dockerfile (Dockerfile)

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

RUN apt-get update && apt-get install -y openssl && rm -rf /var/lib/apt/lists/*

ENV KEYSTORE_PASSWORD=1234567

ENTRYPOINT ["/app/entrypoint.sh"]

Build Docker image:
docker build -t 5rabie5/quizgame-backend-fixed .

---

# 3. Entrypoint Script (entrypoint.sh)

#!/bin/bash

CERT_PATH="/ssl/fullchain1.pem"
KEY_PATH="/ssl/privkey1.pem"
KEYSTORE_PATH="/app/keystore.p12"

echo "🔐 Checking keystore..."

if [ ! -f "$KEYSTORE_PATH" ]; then
  echo "⚙️ Generating keystore.p12 from Let's Encrypt certs..."
  openssl pkcs12 -export     -in "$CERT_PATH"     -inkey "$KEY_PATH"     -out "$KEYSTORE_PATH"     -name tomcat     -password pass:"$KEYSTORE_PASSWORD"
else
  echo "✅ Keystore already exists."
fi

echo "🚀 Starting Spring Boot app..."
exec java -jar /app/app.jar

Make executable:
chmod +x entrypoint.sh

---

# 4. Docker Run Command

sudo docker run -d   --name quizgame-backend   -p 8083:8083   --net mongo-cluster-net   -e KEYSTORE_PASSWORD=1234567   -v /etc/letsencrypt/archive/quiz.antiochorthodox.at:/ssl:ro   5rabie5/quizgame-backend-fixed

Check running containers:
sudo docker ps

---

# 5. SSL Certificate Renewal Hook

Certbot is already scheduled to automatically renew SSL certificates. To ensure the backend uses the latest certificates without manual intervention, create a renewal hook:

Create a post-renewal hook script:
sudo nano /etc/letsencrypt/renewal-hooks/post/renew_backend.sh

Paste this content:
#!/bin/bash

echo "🔄 SSL renewed, restarting quizgame-backend container..."
docker restart quizgame-backend

Make it executable:
sudo chmod +x /etc/letsencrypt/renewal-hooks/post/renew_backend.sh

This ensures that every time the SSL certificate renews, the backend container automatically restarts and regenerates its keystore.

---

# 6. Final Testing

- Visit: https://quiz.antiochorthodox.at
- Verify backend API is responding securely (lock icon 🔒)

Restart container manually (if needed):
sudo docker restart quizgame-backend

View container logs:
sudo docker logs quizgame-backend

---

# Conclusion

Backend service for quiz.antiochorthodox.at is now ready, fully secure with SSL, with automatic SSL renewal and backend restart handled by Certbot hook.

✅ Great work!
