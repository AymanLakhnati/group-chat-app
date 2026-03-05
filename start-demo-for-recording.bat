@echo off
cd /d "%~dp0"
echo Starting demo environment for recording...
powershell -ExecutionPolicy Bypass -File "scripts\demo-for-recording.ps1"
echo.
pause
