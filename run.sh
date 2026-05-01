#!/bin/bash
export APP_PORT=${1:-8080}

# Check if APP_PORT value is a number within the valid TCP range: 1-65535
if ! [[ "$APP_PORT" =~ ^[0-9]+$ ]] || [[ "$APP_PORT" -lt 1 ]] || [[ "$APP_PORT" -gt 65535 ]]; then
    echo "Invalid port number. Setting to default value 8080..."
    APP_PORT=8080
fi

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