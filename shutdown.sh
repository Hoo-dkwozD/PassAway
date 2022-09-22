#!/bin/sh

# Shutdown Docker Compose
docker compose down
docker compose rm

# Shutdown MySQL
docker stop mysql
docker rm mysql
