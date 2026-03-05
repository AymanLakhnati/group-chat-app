# Video Demo — What to Say and Do (3 Minutes)

Use this as your exact script. **Say** the lines in quotes; **Do** the actions in brackets.

---

## PART 1: Code Walkthrough (~1 min 15 sec)

### 0:00 – 0:15  Project structure

**DO:** Show project root in IDE or Explorer: `pom.xml`, `tcp-server`, `tcp-client`, `docs`.

**SAY:** *"This is our Maven multi-module project. We have the TCP server and TCP client in separate modules, and a docs folder for the UML diagrams."*

---

### 0:15 – 0:45  Server code

**DO:** Open in order: `tcp-server` → `src/main/java` → `com.groupchat.server`.

**SAY (at TCPServer.java):** *"This is the entry point. It starts the server and the JavaFX window."*

**DO:** Open `ServerModel.java`.

**SAY:** *"All the logic is here: accepting connections, broadcasting messages, keeping the client list. No UI code—that’s our model-view separation."*

**DO:** Open `ClientHandler.java`.

**SAY:** *"One thread per client. It reads the username first, then the message stream, and handles commands like allUsers and bye."*

**DO:** Open `ServerViewController.java`.

**SAY:** *"This only builds the UI and binds to the model’s lists. No network code."*

---

### 0:45 – 1:15  Client code

**DO:** Open `tcp-client` → `com.groupchat.client`.

**SAY (at TCPClient.java):** *"Main loads config and command-line arguments, then shows the login screen and, after connect, the chat screen."*

**DO:** Open `ClientModel.java`.

**SAY:** *"Socket and messaging logic: connect, send, disconnect, and the read loop. All network code is here."*

**DO:** Open `ClientViewController.java`.

**SAY:** *"Login and chat screens use GridPane. It only talks to the model—no sockets."*

**DO:** Open `config.properties` (server or client).

**SAY:** *"Host and port come from config so we can change them without recompiling."*

---

## PART 2: Live Demo (~1 min 45 sec)

### 1:15 – 1:35  Start server

**DO:** Open terminal or Explorer. Run **start-demo-for-recording.bat** (or run **run-server.bat**).

**SAY:** *"I’m starting the server. We see the timestamped log: Server Started, Waiting for clients."*

**DO:** Point to the server window.

---

### 1:35 – 2:00  First client (Alice)

**DO:** In the first client window: type **Alice**, click **Connect**.

**SAY:** *"We get the chat screen and the green Online indicator."*

**DO:** Type *Hello everyone* and press **Enter**.

**SAY:** *"The message appears with a timestamp on the server log and in the client. The server distributes to all connected clients."*

---

### 2:00 – 2:25  Second and third clients

**DO:** In the second client: type **Bob**, click **Connect**. Send *Hi Alice*.

**SAY:** *"The server shows both usernames in the list with different colors."*

**DO:** In the third client: leave username **blank**, click **Connect**.

**SAY:** *"This is read-only mode. This user can see messages but not send."*

**DO:** In Alice or Bob’s client, type **allUsers** and press Enter.

**SAY:** *"The server replies with the list of active users."*

---

### 2:25 – 2:45  Disconnect

**DO:** In one client type **bye** and press Enter (or click **Disconnect**).

**SAY:** *"The user is removed from the server list and we see they left in the log."*

---

### 2:45 – 3:00  Wrap-up

**DO:** Briefly show the server window and one client.

**SAY:** *"So we have a Maven project, executable JARs, UML in docs, and the app supports multiple clients, read-only mode, and all the required commands. Thank you."*

---

## Quick reference

| Time     | Say (key phrase) | Do |
|----------|------------------|-----|
| 0:00     | Maven, server + client modules | Show `pom.xml`, folders |
| 0:15     | Entry point, JavaFX | TCPServer.java |
| 0:20     | Logic here, model-view | ServerModel.java |
| 0:30     | One thread per client, allUsers, bye | ClientHandler.java |
| 0:38     | UI only, binds to model | ServerViewController.java |
| 0:45     | Config, login, chat | TCPClient.java |
| 0:55     | Socket, connect, send | ClientModel.java |
| 1:02     | GridPane, no sockets | ClientViewController.java |
| 1:08     | Config, no recompile | config.properties |
| 1:15     | Starting server, timestamped log | Run server / start-demo batch |
| 1:35     | Chat screen, Online, message | Alice, Connect, send message |
| 2:00     | Both users, different colors | Bob, connect, send |
| 2:10     | Read-only, see but not send | Blank username, Connect |
| 2:18     | List of active users | Type allUsers |
| 2:25     | User removed, left in log | Type bye |
| 2:45     | Maven, JARs, UML, thank you | Show windows, end |
