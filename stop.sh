#!/bin/bash

DELETE=$1

if [ "$DELETE" == "1" ]; then
    echo "Stopping application and deleting db volume..."
    docker compose down -v
else 
    echo "Stopping application..."
    docker compose down
fi