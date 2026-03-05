@echo off
cd /d "%~dp0"
set MavenDir=.maven\apache-maven-3.9.9\bin
if exist "%MavenDir%\mvn.cmd" (
    echo Building with local Maven...
    call "%MavenDir%\mvn.cmd" clean package -q
) else (
    echo Building project...
    call mvn clean package -q
)
if %ERRORLEVEL% neq 0 (
    echo Build failed. Run scripts\setup-and-build.ps1 first to download Maven, or install Maven.
    pause
    exit /b 1
)
echo Build complete. JARs are in tcp-server\target and tcp-client\target
pause
