@echo off

setlocal
set APP_PORT=%1
if "%APP_PORT%"=="" set APP_PORT=8080

REM Check if APP_PORT value is a number within the valid TCP range: 1-65535
echo %APP_PORT%| findstr /r "^[0-9][0-9]*$" >nul
if errorlevel 1 (
    echo Invalid port number. Setting to 8080...
    set APP_PORT=8080
) else (
    if %APP_PORT% LSS 1 set APP_PORT=8080
    if %APP_PORT% GTR 65535 set APP_PORT=8080
)

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
endlocal