@echo off
cd /d "%~dp0"
where mvn >nul 2>nul
if %ERRORLEVEL% equ 0 (
    echo Building project with system Maven...
    call mvn clean package -q
) else (
    if exist "scripts\setup-and-build.ps1" (
        echo Maven not found. Downloading latest Maven and building...
        powershell -ExecutionPolicy Bypass -File "scripts\setup-and-build.ps1"
    ) else (
        echo Build failed. Maven not found and scripts\setup-and-build.ps1 is missing.
        pause
        exit /b 1
    )
)
if %ERRORLEVEL% neq 0 (
    echo Build failed. Run scripts\setup-and-build.ps1 first to download Maven, or install Maven.
    pause
    exit /b 1
)
echo Build complete. JARs are in tcp-server\target and tcp-client\target
pause
