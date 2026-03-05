# Project 1 — Requirements Checklist

This document maps **every requirement from the initial project prompt** to the implementation. Use it to verify completeness and for grading.

---

## 1. System Architecture

| Requirement | Status | Where |
|-------------|--------|--------|
| Server–Client architecture | ✓ | Server: `tcp-server/`, Client: `tcp-client/` |
| Central server acts as distributor | ✓ | `ServerModel.broadcast()` sends each message to all connected clients |
| Server receives from any client, sends to all others | ✓ | `ClientHandler` reads message → `model.broadcast(fromUsername, message)` |
| Multiple clients connect to server IP and port | ✓ | Each client: `TCPClient` with args or config for host/port |

---

## 2. Functional Requirements

### 2.1 Client Features

| Requirement | Status | Where |
|-------------|--------|--------|
| Users must enter a username before accessing full chat | ✓ | `ClientViewController.buildLogin()` → username field; chat only after Connect |
| Read-only mode if no username (blank) | ✓ | Server sends `READ_ONLY`; `ClientModel.readOnly`; input/send disabled in UI |
| Real-time messaging: text area + SEND button | ✓ | `ClientViewController`: `TextField` + SEND button |
| Send by hitting Enter | ✓ | `inputField.setOnAction(e -> doSend())` |
| Command **allUsers** returns list of active clients to that user | ✓ | `ClientHandler`: `allUsers` → `model.sendUserList(username)`; client shows "Active users: ..." |
| Disconnect: type **end** or **bye**; close connection and notify server | ✓ | `ClientHandler` breaks on bye/end; `ClientModel.send("bye")` on Disconnect button |
| UI: "Online" status label | ✓ | `statusLabel = new Label("Online")` in `buildChat()` |
| UI: Visual indicator (e.g. circle) | ✓ | `statusDot = new Circle(6, Color.GREEN)` |

### 2.2 Server Features

| Requirement | Status | Where |
|-------------|--------|--------|
| Accept multiple simultaneous client connections | ✓ | Thread-per-connection: `new Thread(() -> handleClient(clientSocket)).start()` |
| On message from client: format with sender’s username and time, distribute to all | ✓ | `ServerModel.broadcast()`: `String.format("[%s] %s: %s", time, fromUsername, message)` |
| Server UI: live ListView of all connected usernames | ✓ | `ServerViewController`: `ListView<String> userList = new ListView<>(model.getUsernames())` |
| Visual distinction: random background colors per user in list | ✓ | `userList.setCellFactory(...)` with `userColors.computeIfAbsent(item, ...)` |
| Log: "Server Started", "Waiting for Client", "Welcome [User]" | ✓ | `ServerModel.log()` with timestamp; exact phrases in `start()` and `handleClient()`; activity log has colored rows |

---

## 3. Technical Specifications

### 3.1 Technology Stack

| Requirement | Status | Where |
|-------------|--------|--------|
| Language: Java | ✓ | All source under `tcp-server/`, `tcp-client/` |
| Network: Java Sockets (TCP) | ✓ | `ServerSocket`/`Socket`, `BufferedReader`/`PrintWriter` in server and client |
| GUI: JavaFX | ✓ | `Application`, `Stage`, `Scene`, controls in both modules |
| Layout: GridPane | ✓ | Server: `ServerViewController.build()` uses `GridPane`; Client: login and chat use `GridPane` |
| Styling: CSS | ✓ | `server-style.css`, `client-style.css`; `scene.getStylesheets().add(...)` |
| IDE: IntelliJ IDEA | ✓ | `.idea/runConfigurations/` for TCPServer and TCPClient |

### 3.2 Server-Side Model

| Requirement | Status | Where |
|-------------|--------|--------|
| Thread-per-connection or I/O multiplexing | ✓ | Thread-per-connection: one thread per client in `acceptLoop()` → `handleClient()` |

### 3.3 Network Configuration

| Requirement | Status | Where |
|-------------|--------|--------|
| Server IP and port from config file at runtime | ✓ | Server: `config.properties` → `server.port`; Client: `server.host`, `server.port` |
| Config: .properties or .xml | ✓ | `tcp-server/.../config.properties`, `tcp-client/.../config.properties` |
| No recompilation for environment changes | ✓ | Config loaded in `ServerConfig.load()`, `ClientConfig.load()`; CLI args override for client |

### 3.4 Model–View Decoupling

| Requirement | Status | Where |
|-------------|--------|--------|
| Business logic separated from presentation | ✓ | Model packages: socket/connection logic; View packages: UI only |
| Model works independently of View | ✓ | `ServerModel`, `ClientModel` have no UI imports (except JavaFX for `Platform.runLater` / observables) |
| Changes to presentation do not require changes to logic | ✓ | View only calls model APIs and binds to observable data |

---

## 4. Operational Requirements

### 4.1 Command Line Arguments

| Requirement | Status | Where |
|-------------|--------|--------|
| Client: `java TCPClient <ServerIPAddress> <PortNumber>` | ✓ | `TCPClient.main()`: `args[0]` = host, `args[1]` = port; fallback from config |
| Example: `java TCPClient localhost 3000` | ✓ | Documented in README and run scripts |
| Server: `java TCPServer` | ✓ | `TCPServer.main()`; port from config only |

---

## 5. Deliverables

| Deliverable | Status | Location / How |
|-------------|--------|----------------|
| Fully functional Maven projects (TCPServer, TCPClient) | ✓ | `tcp-server/`, `tcp-client/`, root `pom.xml` |
| Executable JARs | ✓ | `mvn package` or `scripts/setup-and-build.ps1` → `tcp-server/target/*.jar`, `tcp-client/target/*.jar` |
| Demo video (3 min, walkthrough + live demo) | ✓ | Script: `DEMO_VIDEO_SCRIPT.md`; record and submit per instructor |
| UML Class diagram | ✓ | `docs/class-diagram.puml`, `docs/architecture.md` |
| UML Deployment diagram | ✓ | `docs/deployment-diagram.puml`, `docs/architecture.md` |
| Optional: Use case or Sequence diagram | ✓ | `docs/sequence-diagram.puml`, `docs/use-case-diagram.puml` |
| GitHub: full source, commit history, README | ✓ | `README.md`; use `scripts/git-setup.ps1` then push; see `GITHUB_SUBMISSION.md` |
| Optional: Technical post | ✓ | Outline: `TECHNICAL_POST_OUTLINE.md` |

---

## Quick Verification

- **Run server:** IntelliJ: run **TCPServer**; or `run-server.bat` / `scripts/run-app.ps1`.
- **Run client:** IntelliJ: run **TCPClient** (args: `localhost 3000`); or `run-client.bat`.
- **Config:** Edit `tcp-server/.../config.properties` (port) or `tcp-client/.../config.properties` (host/port); no recompile.
- **UML:** Export PNG/PDF from `docs/*.puml` (e.g. PlantUML) for report.
