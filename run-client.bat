@echo off
cd /d "%~dp0"
set "CLIENT_JAR=tcp-client\target\tcp-client-1.0-SNAPSHOT.jar"
set "LIB=tcp-client\target\lib"
if not exist "%CLIENT_JAR%" (
    echo JAR not found. Run build.bat or: powershell -ExecutionPolicy Bypass -File scripts\setup-and-build.ps1
    pause
    exit /b 1
)
set HOST=localhost
set PORT=3000
if not "%~1"=="" set HOST=%~1
if not "%~2"=="" set PORT=%~2
echo Starting TCP Client to %HOST%:%PORT%...
java --enable-native-access=ALL-UNNAMED --module-path "%LIB%" --add-modules javafx.controls,javafx.fxml -jar "%CLIENT_JAR%" %HOST% %PORT%
pause
