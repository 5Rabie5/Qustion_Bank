FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file
COPY target/*.jar app.jar

# Copy the startup script
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

# Install OpenSSL for keystore generation
RUN apt-get update && apt-get install -y openssl && rm -rf /var/lib/apt/lists/*

# Environment variable for SSL keystore password
ENV KEYSTORE_PASSWORD=1234567

# Start the application
ENTRYPOINT ["/app/entrypoint.sh"]
