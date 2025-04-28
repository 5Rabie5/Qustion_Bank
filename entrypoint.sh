#!/bin/bash

CERT_PATH="/ssl/fullchain1.pem"
KEY_PATH="/ssl/privkey1.pem"
KEYSTORE_PATH="/app/keystore.p12"

echo "🔐 Checking keystore..."

if [ ! -f "$KEYSTORE_PATH" ]; then
  echo "⚙️ Generating keystore.p12 from Let's Encrypt certs..."
  openssl pkcs12 -export \
    -in "$CERT_PATH" \
    -inkey "$KEY_PATH" \
    -out "$KEYSTORE_PATH" \
    -name tomcat \
    -password pass:"$KEYSTORE_PASSWORD"
else
  echo "✅ Keystore already exists."
fi

echo "🚀 Starting Spring Boot app..."
exec java -jar /app/app.jar
