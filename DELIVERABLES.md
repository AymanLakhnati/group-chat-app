# Project 1 — Deliverables Summary

**Course:** Paradigms  
**Project:** Mini Project 1 — Group Chat Application  
**Deliverables:** Maven projects, JARs, demo video, UML, GitHub link (optional: technical post)

This document is a **submission checklist** and **index** for all required deliverables. Everything listed here is included in or produced from this repository.

---

## 1. Fully Functional Maven Projects

| Item | Location | Description |
|------|----------|-------------|
| **TCPServer** | `tcp-server/` | Central server: Java Sockets (TCP), JavaFX UI, config, model/view separation. |
| **TCPClient** | `tcp-client/` | Client: Java Sockets (TCP), JavaFX UI (GridPane + CSS), config, model/view separation. |

**How to open:** Open the **root** `pom.xml` in IntelliJ (or another Maven-aware IDE). The project is a multi-module Maven build with modules `tcp-server` and `tcp-client`. Predefined run configurations are in `.idea/runConfigurations/` (TCPServer, TCPClient with JavaFX VM options and client args `localhost 3000`).

**Requirements covered:** See **[REQUIREMENTS_CHECKLIST.md](REQUIREMENTS_CHECKLIST.md)** for a full mapping of the initial project prompt to the codebase.

---

## 2. Artifacts (Executable JARs)

| Artifact | Path (after build) | How to run |
|----------|--------------------|------------|
| **Server JAR** | `tcp-server/target/tcp-server-1.0-SNAPSHOT.jar` | See below |
| **Client JAR** | `tcp-client/target/tcp-client-1.0-SNAPSHOT.jar` | See below |

**Build:** From project root run:

```bash
mvn clean package
```

On Windows you can use **build.bat**.

**Run server:**

```bash
java --module-path tcp-server/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-server/target/tcp-server-1.0-SNAPSHOT.jar
```

Or on Windows: **run-server.bat** (after building).

**Run client:**

```bash
java --module-path tcp-client/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-client/target/tcp-client-1.0-SNAPSHOT.jar localhost 3000
```

Or on Windows: **run-client.bat** or **run-client.bat localhost 3000**.

Dependencies (including JavaFX) are in `tcp-server/target/lib` and `tcp-client/target/lib`. If your JDK already includes JavaFX, you may be able to run with `java -jar ...` only; otherwise use the `--module-path` and `--add-modules` options above.

---

## 3. Demo Video (3 minutes)

| Requirement | How it is met |
|-------------|----------------|
| **Length** | ~3 minutes |
| **Content** | (1) Walkthrough of source code (structure, server, client, model/view, config). (2) Live demonstration: build, run server, run 2–3 clients, send messages, read-only mode, `allUsers`, disconnect. |
| **Submission** | Upload or link as specified by the instructor. |

**Script and checklist:** See **[DEMO_VIDEO_SCRIPT.md](DEMO_VIDEO_SCRIPT.md)** for a timed script, suggested lines to say, and a video submission checklist.

---

## 4. Technical Architecture (UML)

| Diagram | Required | Source | Description |
|---------|----------|--------|-------------|
| **Class Diagram** | Yes | `docs/class-diagram.puml` | Software structure and relationships between classes (server and client modules). |
| **Deployment Diagram** | Yes | `docs/deployment-diagram.puml` | Physical nodes (Server vs Clients) and TCP/IP communication. |
| **Sequence Diagram** | Optional | `docs/sequence-diagram.puml` | Message flow between clients and server. |
| **Use Case Diagram** | Optional | `docs/use-case-diagram.puml` | User interactions (client and server use cases). |

**Text version and design notes:** **[docs/architecture.md](docs/architecture.md)** — includes class roles, relationships, deployment description, sequence summary, use cases, and design notes for the report.

**How to produce images for submission:** Open each `.puml` file in [PlantUML Online](https://www.plantuml.com/plantuml/uml/) (or use the PlantUML plugin in VS Code/IntelliJ) and export as **PNG** or **PDF**. Add these images to your report or to a folder (e.g. `docs/images/`) in the repo.

---

## 5. GitHub Link

| Requirement | How it is met |
|-------------|----------------|
| **Repository** | A GitHub repository containing this project. |
| **Contents** | Full source code, README.md, commit history. |
| **README** | Describes the project, build, run, and architecture (see root **README.md**). |

**Steps:** Run **scripts/git-setup.ps1** once to initialize Git and create initial commits. Then create a new repository on GitHub and push (see below). Submit the **repository URL** as required.

**Detailed steps:** See **[GITHUB_SUBMISSION.md](GITHUB_SUBMISSION.md)** for a step-by-step guide.

---

## 6. Optional — Technical Post

| Requirement | How it is met |
|-------------|----------------|
| **Content** | Development process, technical challenges, final implementation. |
| **Platform** | e.g. Medium, Dev.to, or personal blog. |
| **Submission** | Add the post **link** in the README or in your submission. |

**Outline:** See **[TECHNICAL_POST_OUTLINE.md](TECHNICAL_POST_OUTLINE.md)** for a suggested structure and bullet points.

---

## Quick Reference for Graders

| Deliverable | Where to find it |
|-------------|------------------|
| Source code | Repository root: `tcp-server/`, `tcp-client/`, root `pom.xml`. |
| Build & run | README.md; run scripts: `build.bat`, `run-server.bat`, `run-client.bat`. |
| JARs | After `mvn clean package`: `tcp-server/target/*.jar`, `tcp-client/target/*.jar`. |
| Demo video | Submitted separately (link or file as per instructions); script in DEMO_VIDEO_SCRIPT.md. |
| UML (text) | docs/architecture.md. |
| UML (diagrams) | docs/*.puml → export to PNG/PDF. |
| This checklist | DELIVERABLES.md (this file). |
