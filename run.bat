@echo off

setlocal
set APP_PORT=%1
if "%APP_PORT%"=="" set APP_PORT=8080

echo -------------------------------------------------------
echo Running...
echo To access service use provided URL: http://localhost:%APP_PORT%
echo -------------------------------------------------------

set MODE=%2

if "%MODE%"=="1" (
    echo Running in an interactive mode...
    docker compose up --build
) else (
    echo Running in a detach mode...
    docker compose up --build -d
)
pause
endlocal