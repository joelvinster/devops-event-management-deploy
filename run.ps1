$ErrorActionPreference = "Stop"

$mavenUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
$mavenZip = "maven.zip"
$mavenDir = ".maven"

Write-Host "Checking for Maven..."
if (-not (Test-Path "$mavenDir/apache-maven-3.9.6/bin/mvn.cmd")) {
    Write-Host "Downloading Maven..."
    Invoke-WebRequest -Uri $mavenUrl -OutFile $mavenZip
    Write-Host "Extracting Maven..."
    Expand-Archive -Path $mavenZip -DestinationPath $mavenDir -Force
    Remove-Item -Path $mavenZip
}

Write-Host "Building project with Maven..."
& .\.maven\apache-maven-3.9.6\bin\mvn.cmd clean package

