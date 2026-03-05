# Setup and build Group Chat Application
# Downloads Maven if needed, then runs mvn clean package
$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
if (-not (Test-Path "$ProjectRoot\pom.xml")) { $ProjectRoot = (Get-Location).Path }
Set-Location $ProjectRoot

$MavenVersion = "3.9.9"
$MavenDir = "$ProjectRoot\.maven"
$MavenZip = "$MavenDir\apache-maven-$MavenVersion-bin.zip"
$MavenHome = "$MavenDir\apache-maven-$MavenVersion"
$MvnCmd = "$MavenHome\bin\mvn.cmd"

if (-not (Test-Path $MvnCmd)) {
    Write-Host "Downloading Apache Maven $MavenVersion..."
    New-Item -ItemType Directory -Force -Path $MavenDir | Out-Null
    $url = "https://dlcdn.apache.org/maven/maven-3/$MavenVersion/binaries/apache-maven-$MavenVersion-bin.zip"
    try {
        [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
        Invoke-WebRequest -Uri $url -OutFile $MavenZip -UseBasicParsing
    } catch {
        $url = "https://archive.apache.org/dist/maven/maven-3/$MavenVersion/binaries/apache-maven-$MavenVersion-bin.zip"
        Invoke-WebRequest -Uri $url -OutFile $MavenZip -UseBasicParsing
    }
    Write-Host "Extracting Maven..."
    Expand-Archive -Path $MavenZip -DestinationPath $MavenDir -Force
    Remove-Item $MavenZip -Force -ErrorAction SilentlyContinue
}

if (-not (Test-Path $MvnCmd)) { Write-Error "Maven not found at $MvnCmd"; exit 1 }

Write-Host "Building project..."
& $MvnCmd clean package -q
if ($LASTEXITCODE -ne 0) { Write-Error "Build failed"; exit 1 }
Write-Host "Build succeeded."
exit 0
