# Project 1 — Submission Summary

**Mini Project 1: Group Chat Application**  
**Deliverables:** All requirements from the initial project prompt are implemented and documented here.

---

## What to Submit

| # | Deliverable | What to hand in |
|---|-------------|------------------|
| 1 | **Maven projects** | GitHub repository link (contains full source: `tcp-server/`, `tcp-client/`, root `pom.xml`). |
| 2 | **JARs** | Build with `mvn package` or `scripts/setup-and-build.ps1`; JARs in `tcp-server/target/`, `tcp-client/target/`. Submit the link to the repo; optionally attach JARs or document build in README. |
| 3 | **Demo video** | 3-minute video: code walkthrough + live run. Follow **DEMO_VIDEO_SCRIPT.md**. Submit link or file as required by instructor. |
| 4 | **UML** | Class + Deployment diagrams (required). Optional: Sequence, Use case. Sources: **docs/class-diagram.puml**, **docs/deployment-diagram.puml**, **docs/sequence-diagram.puml**, **docs/use-case-diagram.puml**. Export to PNG/PDF with PlantUML; include in report or **docs/** and mention in submission. |
| 5 | **GitHub** | Repository URL. Repo must have: source, README.md, commit history (use **scripts/git-setup.ps1** then push; see **GITHUB_SUBMISSION.md**). |
| 6 | **Optional** | Technical post link (outline in **TECHNICAL_POST_OUTLINE.md**). |

---

## Quick Start for Graders

1. **Open project:** Open root **pom.xml** in IntelliJ → Maven imports. Run **TCPServer**, then **TCPClient** (program args: `localhost 3000`) from the run menu.
2. **Or run from scripts:**  
   - First time: `powershell -ExecutionPolicy Bypass -File scripts/setup-and-build.ps1`  
   - Then: `powershell -ExecutionPolicy Bypass -File scripts/run-app.ps1`  
   Or use **run-server.bat** and **run-client.bat** after building.
3. **Requirements:** See **REQUIREMENTS_CHECKLIST.md** for every requirement from the prompt mapped to the code.
4. **Deliverables index:** See **DELIVERABLES.md**.

---

## Project Layout

```
├── pom.xml
├── README.md
├── DELIVERABLES.md
├── REQUIREMENTS_CHECKLIST.md
├── SUBMISSION.md (this file)
├── RUN_INSTRUCTIONS.md
├── DEMO_VIDEO_SCRIPT.md
├── GITHUB_SUBMISSION.md
├── TECHNICAL_POST_OUTLINE.md
├── build.bat, run-server.bat, run-client.bat
├── scripts/
│   ├── setup-and-build.ps1   (download Maven, build)
│   ├── run-app.ps1           (start server + client)
│   └── git-setup.ps1         (init Git, first commits)
├── docs/
│   ├── architecture.md
│   ├── class-diagram.puml
│   ├── deployment-diagram.puml
│   ├── sequence-diagram.puml
│   └── use-case-diagram.puml
├── tcp-server/   (server source + config + CSS)
└── tcp-client/   (client source + config + CSS)
```

---

## Config (no recompile)

- **Server port:** `tcp-server/src/main/resources/config.properties` → `server.port=3000`
- **Client default:** `tcp-client/src/main/resources/config.properties` → `server.host`, `server.port`  
- **CLI overrides client:** `java TCPClient <host> <port>`
