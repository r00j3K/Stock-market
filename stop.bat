@echo off

setlocal
set DELETE_DB=%1

if "%DELETE_DB%"=="1" (
    echo Stopping application and deleting db volume...
    docker compose down -v
) else (
    echo Stopping application...
    docker compose down
)
pause
endlocal