@echo off

setlocal
set APP_PORT=%1
if "%APP_PORT%"=="" set APP_PORT=8080

set INVALID=0

@REM Check if APP_PORT value is a number 
echo %APP_PORT%| findstr /r "^[0-9][0-9]*$" >nul || set INVALID=1

@REM Check if APP_PORT is within the valid TCP range: 1-65535
if %INVALID%==0 (
    if %APP_PORT% LSS 1 set INVALID=1
    if %APP_PORT% GTR 65535 set INVALID=1
)

if %INVALID%==1 (
    echo Invalid port number. Setting to 8080...
    set APP_PORT=8080
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