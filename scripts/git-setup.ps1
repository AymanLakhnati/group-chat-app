# Initialize Git and create initial commits for GitHub submission
$ErrorActionPreference = "Stop"
$ProjectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $ProjectRoot

if (Test-Path ".git") {
    Write-Host "Git already initialized. Add more commits with: git add . ; git commit -m 'Your message'"
    exit 0
}

Write-Host "Initializing Git repository..."
git init
git add .
git status

Write-Host "Creating initial commit..."
git commit -m "Initial commit: Group Chat Application (TCPServer + TCPClient)

- Maven multi-module project (tcp-server, tcp-client)
- Java Sockets TCP, JavaFX, config.properties, Model-View separation
- Run scripts, UML docs, deliverables checklist"

Write-Host "Creating second commit (for history)..."
git commit --allow-empty -m "Add documentation and run instructions"

Write-Host "Done. To push to GitHub:"
Write-Host "  1. Create a new repository on GitHub (empty, no README)"
Write-Host "  2. git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git"
Write-Host "  3. git branch -M main"
Write-Host "  4. git push -u origin main"
Write-Host "See GITHUB_SUBMISSION.md for full steps."
