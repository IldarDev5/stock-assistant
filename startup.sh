#!/bin/bash

echo "Building stock-assistant lib and placing into local Maven repo"
cd stock-assistant/
gradle build
gradle publishToMavenLocal

echo "Building eureka-server-app"
cd ../eureka-server-app/
gradle build

echo "Building stock-assistant-portfolio-service"
cd ../stock-assistant-portfolio-service/
gradle build

echo "Building stock-assistant-service"
cd ../stock-assistant-service/
gradle build

echo "Starting containers with docker compose"
cd ..
docker-compose up --build