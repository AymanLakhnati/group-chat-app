# Demo recording setup: free port, build if needed, start server then 3 clients
$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectRoot

$ServerJar = "tcp-server\target\tcp-server-1.0-SNAPSHOT.jar"
$ClientJar = "tcp-client\target\tcp-client-1.0-SNAPSHOT.jar"
$ServerLib = "tcp-server\target\lib"
$ClientLib = "tcp-client\target\lib"

Write-Host "=== Demo recording setup ===" -ForegroundColor Cyan

# Free ports 3000-3002 so server can bind to 3000
foreach ($port in 3000, 3001, 3002) {
    $raw = & netstat -ano 2>$null
    $lines = $raw | Select-String ":$port\s"
    foreach ($l in $lines) {
        $parts = ($l.ToString() -split '\s+') | Where-Object { $_ -ne '' }
        if ($parts.Count -gt 0 -and $parts[-1] -match '^\d+$') {
            $procId = $parts[-1]
            Write-Host "Freeing port $port (PID $procId)..."
            & taskkill /PID $procId /F 2>$null
            Start-Sleep -Seconds 1
            break
        }
    }
}

if (-not (Test-Path $ServerJar) -or -not (Test-Path $ClientJar)) {
    Write-Host "Building project..."
    & "$PSScriptRoot\setup-and-build.ps1"
    if ($LASTEXITCODE -ne 0) { exit 1 }
}

$javaBase = @("--enable-native-access=ALL-UNNAMED", "--module-path", "", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "")

Write-Host "Starting server..."
$serverArgs = $javaBase.Clone()
$serverArgs[2] = $ServerLib
$serverArgs[6] = $ServerJar
Start-Process -FilePath "java" -ArgumentList $serverArgs -WorkingDirectory $ProjectRoot

Start-Sleep -Seconds 3

Write-Host "Starting client 1 (Alice)..."
Start-Process -FilePath "java" -ArgumentList "--enable-native-access=ALL-UNNAMED", "--module-path", $ClientLib, "--add-modules", "javafx.controls,javafx.fxml", "-jar", $ClientJar, "localhost", "3000" -WorkingDirectory $ProjectRoot
Start-Sleep -Seconds 2

Write-Host "Starting client 2 (Bob)..."
Start-Process -FilePath "java" -ArgumentList "--enable-native-access=ALL-UNNAMED", "--module-path", $ClientLib, "--add-modules", "javafx.controls,javafx.fxml", "-jar", $ClientJar, "localhost", "3000" -WorkingDirectory $ProjectRoot
Start-Sleep -Seconds 2

Write-Host "Starting client 3 (read-only)..."
Start-Process -FilePath "java" -ArgumentList "--enable-native-access=ALL-UNNAMED", "--module-path", $ClientLib, "--add-modules", "javafx.controls,javafx.fxml", "-jar", $ClientJar, "localhost", "3000" -WorkingDirectory $ProjectRoot

Write-Host ""
Write-Host "Ready for recording." -ForegroundColor Green
Write-Host "  - 1 server window (TCP Chat Server)"
Write-Host "  - 3 client windows: use Alice, Bob, and leave one username blank for read-only"
Write-Host "  - If server shows port 3001, close clients and run: run-client.bat localhost 3001"
Write-Host ""
