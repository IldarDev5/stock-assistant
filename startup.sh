#!/bin/bash

./rebuild.sh

echo "Starting containers with docker compose"
docker-compose up --build