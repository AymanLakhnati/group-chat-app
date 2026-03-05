# Group Chat Application

**GitHub:** *(paste your repository link here after pushing, e.g. https://github.com/yourusername/group-chat-app)*

A real-time multi-user group chat application built with **Java**, **Java Sockets (TCP)**, and **JavaFX**. The system uses a central server that distributes messages to all connected clients. This project fulfills the requirements for the Paradigms course mini-project (Server–Client architecture, Model–View separation, config-driven deployment).

**Submission & requirements:** [SUBMISSION.md](SUBMISSION.md) (what to submit) · [REQUIREMENTS_CHECKLIST.md](REQUIREMENTS_CHECKLIST.md) (all prompt requirements mapped to code) · [DELIVERABLES.md](DELIVERABLES.md) (full index) · [DEMO_VIDEO_SCRIPT.md](DEMO_VIDEO_SCRIPT.md) (3-min demo).

---

## Overview

- **Server (TCPServer):** Listens for client connections, assigns usernames (or read-only mode), broadcasts messages with timestamp and sender name, maintains a live list of connected users with distinct colors, and logs all activity with timestamps.
- **Client (TCPClient):** Connects to the server with an optional username. Users with a username can send messages and use commands; users without a username are restricted to read-only mode. The UI shows online status, message area, and supports `allUsers`, `end`, and `bye`.

Configuration (server port, client host/port) is loaded from **config.properties** at runtime so the application can be redeployed without recompilation.

---

## Features

### Client

- **Authentication:** Username required to send messages; blank username results in read-only mode.
- **Real-time messaging:** Type in the text field and send via the SEND button or Enter key.
- **Commands:** `allUsers` — server returns the list of active users; `end` or `bye` — disconnect and exit.
- **UI:** Online status label with a green indicator; Disconnected state with gray indicator when the connection is lost. System and “Active users” lines styled for clarity.
- **Connection:** Configurable connection timeout; clear error messages for connection refused or timeout.

### Server

- **Multiple clients:** Thread-per-connection model; each client is handled in a dedicated thread.
- **Message distribution:** Every message is formatted with `[HH:mm:ss] username: message` and sent to all connected clients.
- **Client list:** ListView of connected usernames with random background colors for readability.
- **Activity log:** Timestamped log entries (e.g. `[10:30:45] Server Started on port 3000`, `Welcome [User]`, client join/leave).
- **Graceful shutdown:** Accept loop uses a timeout so the server can stop cleanly; all client sockets are closed on stop.

---

## Architecture

- **Model–View separation:** Business logic and network I/O live in the **model** packages; the **view** packages only build the JavaFX UI and bind to model data. Changes to the UI do not require changes to the core logic.
- **Server:** `ServerModel` manages the `ServerSocket`, client sessions, broadcast, and logging. `ClientHandler` (one per client) reads username then message stream; protocol constants are in `Protocol`.
- **Client:** `ClientModel` handles connection, send/receive, and disconnect; the view uses **GridPane** for layout (login and chat screens) and **CSS** for styling.
- **Configuration:** Each module has `config.properties` in `src/main/resources`. Server validates port range (1–65535); client validates port from config and command-line arguments.

---

## Project Structure

```
├── pom.xml                 (parent Maven project)
├── tcp-server/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/groupchat/server/
│       │   ├── TCPServer.java           (entry point)
│       │   ├── config/ServerConfig.java
│       │   ├── model/ServerModel.java, ClientHandler.java, ClientSession.java, Protocol.java
│       │   └── view/ServerViewController.java
│       └── resources/
│           ├── config.properties
│           └── server-style.css
├── tcp-client/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/groupchat/client/
│       │   ├── TCPClient.java           (entry point)
│       │   ├── config/ClientConfig.java
│       │   ├── model/ClientModel.java, Protocol.java
│       │   └── view/ClientViewController.java
│       └── resources/
│           ├── config.properties
│           └── client-style.css
├── docs/
│   ├── architecture.md     (UML descriptions)
│   ├── class-diagram.puml
│   ├── deployment-diagram.puml
│   └── sequence-diagram.puml
├── DELIVERABLES.md
├── DEMO_VIDEO_SCRIPT.md
├── build.bat
├── run-server.bat
└── run-client.bat
```

---

## Build and Run

### Prerequisites

- **Java 17+** (JDK with or without bundled JavaFX)
- **Maven 3.6+** (or use the provided batch scripts on Windows)

### Build

From the project root:

```bash
mvn clean package
```

This produces:

- `tcp-server/target/tcp-server-1.0-SNAPSHOT.jar`
- `tcp-client/target/tcp-client-1.0-SNAPSHOT.jar`

Dependencies (including JavaFX) are copied to `tcp-server/target/lib` and `tcp-client/target/lib`.

On **Windows**, you can run `build.bat` to build.

### Run Server

```bash
java TCPServer
```

Or, if JavaFX is not in the JDK:

```bash
java --module-path tcp-server/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-server/target/tcp-server-1.0-SNAPSHOT.jar
```

On Windows: `run-server.bat` (after building).

The server reads **server.port** from `tcp-server/src/main/resources/config.properties` (default: 3000). Invalid port values (outside 1–65535) are rejected at startup.

### Run Client

```bash
java TCPClient <ServerIPAddress> <PortNumber>
```

Example:

```bash
java TCPClient localhost 3000
```

With JavaFX on the module path:

```bash
java --module-path tcp-client/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-client/target/tcp-client-1.0-SNAPSHOT.jar localhost 3000
```

On Windows: `run-client.bat` or `run-client.bat localhost 3000`.

If no arguments are provided, the client uses **server.host** and **server.port** from `tcp-client/src/main/resources/config.properties`. Command-line arguments override the config. Invalid port values are ignored and the config default is used.

---

## Configuration

| File | Keys | Description |
|------|------|-------------|
| `tcp-server/.../config.properties` | `server.port` | Port the server listens on (1–65535). |
| `tcp-client/.../config.properties` | `server.host`, `server.port` | Default server address and port when not passed as CLI arguments. |

---

## Deliverables (Project 1)

| Item | Description |
|------|-------------|
| **Maven projects** | Source code for TCPServer and TCPClient in this repository. |
| **JARs** | Built with `mvn clean package`; run with `run-server.bat` / `run-client.bat` or the `java` commands above. |
| **Demo video** | [DEMO_VIDEO_SCRIPT.md](DEMO_VIDEO_SCRIPT.md) — timed 3-minute script (code walkthrough + live run) and video submission checklist. |
| **UML** | [docs/architecture.md](docs/architecture.md) (text + design notes). Diagrams: `docs/class-diagram.puml`, `docs/deployment-diagram.puml`, `docs/sequence-diagram.puml`, `docs/use-case-diagram.puml` — export to PNG/PDF with PlantUML. |
| **Submission checklist** | [DELIVERABLES.md](DELIVERABLES.md) — full list of deliverables and where to find each. |
| **GitHub** | [GITHUB_SUBMISSION.md](GITHUB_SUBMISSION.md) — step-by-step guide to create the repo and submit the link. |
| **Optional technical post** | [TECHNICAL_POST_OUTLINE.md](TECHNICAL_POST_OUTLINE.md) — outline for a blog post (Medium, Dev.to, etc.). |

---

## License

This project was developed for educational purposes as part of the Paradigms course.
