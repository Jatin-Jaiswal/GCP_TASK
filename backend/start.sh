#!/bin/bash

# Start the application
echo "Starting Spring Boot application on port ${PORT}..."
exec java -jar /app/app.jar --server.port=${PORT}