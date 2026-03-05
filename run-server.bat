@echo off
cd /d "%~dp0"
set "SERVER_JAR=tcp-server\target\tcp-server-1.0-SNAPSHOT.jar"
set "LIB=tcp-server\target\lib"
if not exist "%SERVER_JAR%" (
    echo JAR not found. Run build.bat or: powershell -ExecutionPolicy Bypass -File scripts\setup-and-build.ps1
    pause
    exit /b 1
)
echo Starting TCP Chat Server...
java --enable-native-access=ALL-UNNAMED --module-path "%LIB%" --add-modules javafx.controls,javafx.fxml -jar "%SERVER_JAR%"
pause
