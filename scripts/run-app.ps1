# Build (if needed), then start server and client
$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectRoot

$ServerJar = "tcp-server\target\tcp-server-1.0-SNAPSHOT.jar"
$ClientJar = "tcp-client\target\tcp-client-1.0-SNAPSHOT.jar"
$ServerLib = "tcp-server\target\lib"
$ClientLib = "tcp-client\target\lib"

if (-not (Test-Path $ServerJar) -or -not (Test-Path $ClientJar)) {
    Write-Host "JARs not found. Running setup and build..."
    & "$PSScriptRoot\setup-and-build.ps1"
    if ($LASTEXITCODE -ne 0) { exit 1 }
}

$javaArgs = @("--module-path", "", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "")

Write-Host "Starting TCP Chat Server..."
$serverArgs = $javaArgs.Clone()
$serverArgs[1] = $ServerLib
$serverArgs[5] = $ServerJar
Start-Process -FilePath "java" -ArgumentList $serverArgs -WorkingDirectory $ProjectRoot

Start-Sleep -Seconds 2

Write-Host "Starting TCP Client..."
Start-Process -FilePath "java" -ArgumentList "--module-path", $ClientLib, "--add-modules", "javafx.controls,javafx.fxml", "-jar", $ClientJar, "localhost", "3000" -WorkingDirectory $ProjectRoot

Write-Host "Server and client started. Use the client window to enter a username and connect."
