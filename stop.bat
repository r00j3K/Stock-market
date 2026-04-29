@echo off

setlocal
set DELETE_VOLUME=%1

if "%DELETE_VOLUME%"=="-v" (
    echo Stopping application and deleting db volume...
    docker compose down -v
) else (
    echo Stopping application...
    docker compose down
)
pause
endlocal