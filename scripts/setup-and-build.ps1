param(
    [switch]$SkipBuild,
    [string[]]$MavenArgs = @()
)

Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$rootDir = (Resolve-Path (Join-Path $scriptDir "..")).Path
$mavenHomeDir = Join-Path $rootDir ".maven"

function Get-LatestMavenVersion {
    $metadataUri = "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/maven-metadata.xml"
    [xml]$metadata = (Invoke-WebRequest -Uri $metadataUri -UseBasicParsing).Content
    $releaseVersion = $metadata.metadata.versioning.release
    if ([string]::IsNullOrWhiteSpace($releaseVersion)) {
        throw "Could not determine latest Maven release from metadata."
    }
    return $releaseVersion
}

function Ensure-MavenInstalled {
    param(
        [Parameter(Mandatory = $true)]
        [string]$Version
    )

    $targetDir = Join-Path $mavenHomeDir "apache-maven-$Version"
    $mvnCmd = Join-Path $targetDir "bin\mvn.cmd"
    if (Test-Path $mvnCmd) {
        Write-Host "Using existing local Maven $Version."
        return $mvnCmd
    }

    New-Item -ItemType Directory -Path $mavenHomeDir -Force | Out-Null

    $archiveName = "apache-maven-$Version-bin.zip"
    $majorVersion = $Version.Split(".")[0]
    $primaryUrl = "https://dlcdn.apache.org/maven/maven-$majorVersion/$Version/binaries/$archiveName"
    $fallbackUrl = "https://archive.apache.org/dist/maven/maven-$majorVersion/$Version/binaries/$archiveName"
    $zipPath = Join-Path $env:TEMP $archiveName

    Write-Host "Downloading Apache Maven $Version..."
    try {
        Invoke-WebRequest -Uri $primaryUrl -OutFile $zipPath -UseBasicParsing
    }
    catch {
        Write-Host "Primary download URL unavailable; trying archive mirror..."
        Invoke-WebRequest -Uri $fallbackUrl -OutFile $zipPath -UseBasicParsing
    }

    Expand-Archive -Path $zipPath -DestinationPath $mavenHomeDir -Force
    Remove-Item -Path $zipPath -Force

    if (-not (Test-Path $mvnCmd)) {
        throw "Maven executable not found after extraction: $mvnCmd"
    }

    return $mvnCmd
}

$latestVersion = Get-LatestMavenVersion
$mavenCommand = Ensure-MavenInstalled -Version $latestVersion

if (-not $SkipBuild) {
    Write-Host "Building project with: $mavenCommand"
    & $mavenCommand clean package @MavenArgs
}
