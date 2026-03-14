# Group Chat Application

**GitHub:** [https://github.com/AymanLakhnati/group-chat-app](https://github.com/AymanLakhnati/group-chat-app)

A multi-user group chat application built with **Java**, **TCP sockets**, and **JavaFX**. A central server distributes messages to all connected clients. Model–View separation; configuration via `config.properties`.

---

## Deliverables

- **Maven projects:** Source for TCPServer and TCPClient (`tcp-server/`, `tcp-client/`).
- **Artifacts:** Executable JARs produced by `mvn clean package` (see Build below).
- **UML:** `docs/` — class diagram, deployment diagram, sequence and use-case diagrams (PlantUML). See `docs/architecture.md`.

---

## Build

**Prerequisites:** Java 17+, Maven 3.6+

```bash
mvn clean package
```

Produces `tcp-server/target/tcp-server-1.0-SNAPSHOT.jar` and `tcp-client/target/tcp-client-1.0-SNAPSHOT.jar`. On Windows you can use `build.bat`.

### No Maven installed? Download latest Maven automatically

Linux/macOS:

```bash
chmod +x scripts/setup-and-build.sh
./scripts/setup-and-build.sh
```

Windows PowerShell:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\setup-and-build.ps1
```

Both scripts fetch the latest Apache Maven release, install it into `.maven/`, and build the project.

---

## Run

**Server** (default port 3000, set in `tcp-server/src/main/resources/config.properties`):

```bash
java --module-path tcp-server/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-server/target/tcp-server-1.0-SNAPSHOT.jar
```

Windows: `run-server.bat` (after building).

**Client** (pass host and port):

```bash
java --module-path tcp-client/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-client/target/tcp-client-1.0-SNAPSHOT.jar localhost 3000
```

Windows: `run-client.bat` or `run-client.bat localhost 3000`.

---

## Project structure

```
├── pom.xml
├── tcp-server/     (Server source + config)
├── tcp-client/     (Client source + config)
├── docs/           (UML: architecture.md, *.puml)
├── build.bat
├── run-server.bat
└── run-client.bat
```

---

Developed for the Paradigms course.
