#!/bin/bash
export APP_PORT=${1:-8080}

echo "-------------------------------------------------------"
echo "Running..."
echo "To access service use provided URL: http://localhost:$APP_PORT"
echo "-------------------------------------------------------"

MODE=${2:-0}

if [ "$MODE" == "1" ]; then
    echo "Running in an interactive mode..."
    docker compose up --build
else
    echo "Running in a detach mode..."
    docker compose up --build -d
fi