# Technical Architecture (UML)

This document describes the **Technical Architecture** of the Group Chat Application for Project 1. It provides both **text descriptions** and **diagram sources** suitable for inclusion in your report.

---

## Diagrams Required (Assignment Rubric)

| Requirement | Description | File |
|-------------|-------------|------|
| **Class Diagram** (required) | Detailing the software structure and relationships between classes. | `class-diagram.puml` |
| **Deployment Diagram** (required) | Illustrating the physical network nodes (Server vs. Clients) and the TCP/IP communication links. | `deployment-diagram.puml` |
| **Use Case or Sequence** (optional) | To further clarify user interactions and message flow. | `use-case-diagram.puml`, `sequence-diagram.puml` |

All four diagrams are provided; export to PNG/PDF as needed for your report.

---

## How to Generate Diagram Images for Submission

1. **PlantUML** (recommended): Open each `.puml` file in this folder in [PlantUML Online](https://www.plantuml.com/plantuml/uml/), or use the PlantUML extension in VS Code / IntelliJ, then export as **PNG** or **PDF**.
2. **Files included:**
   - `class-diagram.puml` — Class Diagram (required)
   - `deployment-diagram.puml` — Deployment Diagram (required)
   - `sequence-diagram.puml` — Sequence Diagram (optional, message flow)
   - `use-case-diagram.puml` — Use Case Diagram (optional)

Include the exported images in your report or in a `docs/images/` folder in the repository.

---

## 1. Class Diagram

**Purpose:** Shows the software structure and relationships between classes (attributes, methods, dependencies).

### Server module

| Class | Role |
|-------|------|
| **TCPServer** | JavaFX Application entry point; creates ServerConfig, ServerModel, ServerViewController; starts the server and shows the UI. |
| **ServerModel** | Core logic: ServerSocket, accept loop, client sessions, broadcast, logging. No UI code. Uses Protocol constants. |
| **ServerConfig** | Loads `config.properties`; validates and exposes server port. |
| **ClientSession** | Holds username, Socket, and PrintWriter for one connected client. |
| **ClientHandler** | One per client thread: reads username, then message loop; handles commands (allUsers, bye/end); uses Protocol. |
| **ServerViewController** | Builds the JavaFX UI (GridPane); binds ListViews to model’s usernames and logMessages. |
| **Protocol** | Static constants: READ_ONLY, OK, USERS_PREFIX, command names, MAX_USERNAME_LENGTH, port range. |

**Relationships:** TCPServer *uses* ServerModel, ServerConfig, ServerViewController. ServerModel *holds* ClientSession, *creates* ClientHandler. ServerViewController *binds to* ServerModel. ServerModel and ClientHandler *use* Protocol.

### Client module

| Class | Role |
|-------|------|
| **TCPClient** | JavaFX Application entry point; parses CLI args; loads ClientConfig; creates ClientViewController; shows login then chat. |
| **ClientModel** | Socket connection, send/receive, disconnect; observable messages list; onDisconnect callback. No UI. |
| **ClientConfig** | Loads `config.properties`; default host and port; validated on load. |
| **ClientViewController** | Builds login (GridPane) and chat (GridPane); binds to ClientModel; no network code. |
| **Protocol** | Static constants: READ_ONLY, USERS_PREFIX, CONNECT_TIMEOUT_MS, port range. |

**Relationships:** TCPClient *uses* ClientViewController and ClientConfig. ClientViewController *uses* ClientModel. ClientModel *uses* Protocol.

### ASCII sketch (for quick reference)

```
[TCPServer] --> [ServerModel], [ServerConfig], [ServerViewController]
[ServerModel] --> [ClientSession], [ClientHandler], [ServerConfig], [Protocol]
[ServerViewController] --> [ServerModel]

[TCPClient] --> [ClientViewController], [ClientConfig]
[ClientViewController] --> [ClientModel]
[ClientModel] --> [Protocol]
```

---

## 2. Deployment Diagram

**Purpose:** Illustrates the physical deployment of the system: **Server** and **Clients** as nodes, and the **TCP/IP communication** between them.

- **Server Host:** One node running the **TCPServer** (JVM, JavaFX UI). Listens on a configurable port (e.g. 3000). One thread per client connection.
- **Client Hosts:** Multiple nodes (e.g. Client A, B, C), each running **TCPClient** (JVM, JavaFX UI). Each connects to the server via `java TCPClient <host> <port>`.
- **Communication:** TCP/IP (Java Sockets) between the server and each client. The server accepts connections and distributes messages to all connected clients.

See `deployment-diagram.puml` for the full diagram (nodes, components, TCP links, notes).

---

## 3. Sequence Diagram (Message Flow)

**Purpose:** Clarifies the message flow between clients and the server (optional but recommended).

**Typical flow:**

1. Client A connects; sends username (e.g. "alice"); server responds "OK" and logs "Welcome alice"; server broadcasts "[System] alice joined" to other clients.
2. Client B connects; sends "bob"; server responds "OK"; server notifies all (including A) "[System] bob joined".
3. Client A sends "hello everyone"; server formats with timestamp and broadcasts to all clients.
4. Client B sends "allUsers"; server responds "USERS:alice,bob,...".
5. Client A sends "bye"; server removes the client and broadcasts "[System] alice left" to others.

See `sequence-diagram.puml` for the full sequence.

---

## 4. Use Case Diagram (Optional)

**Purpose:** Describes user interactions with the system (client and server use cases).

**Client use cases:** Enter username, Connect to server, Send message, View messages, Request active users (allUsers), Disconnect (end/bye), Connect in read-only mode (no username).  
**Server use cases:** Accept client connections, Distribute messages, Maintain user list, Log activity, Respond to allUsers.

See `use-case-diagram.puml` for the full diagram.

---

## Design Notes (for report)

- **Model–View separation:** All network and business logic live in the model packages; the view only builds UI and binds to model data. This keeps the application maintainable and testable.
- **Configuration:** Server port and client default host/port are loaded from `config.properties` at runtime so the application can be redeployed without recompilation.
- **Thread model:** Server uses a thread-per-connection approach; each client has a dedicated handler thread. For very large numbers of clients, I/O multiplexing (e.g. NIO Selector) would scale better.
- **Protocol:** Shared constants (Protocol classes) ensure consistent behavior between server and client and make the protocol easy to extend.
