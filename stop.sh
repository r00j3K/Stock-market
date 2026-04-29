#!/bin/bash

DELETE_VOLUME=$1

if [ "$DELETE_VOLUME" == "-v" ]; then
    echo "Stopping application and deleting db volume..."
    docker compose down -v
else 
    echo "Stopping application..."
    docker compose down
fi