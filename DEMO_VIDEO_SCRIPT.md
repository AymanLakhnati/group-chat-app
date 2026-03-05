# Demo Video Script — 3 Minutes

Use this script to record your **3-minute** demo: **source code walkthrough** + **live run**. Each section has a time target and suggested lines so you stay on pace.

**For exact lines to say and do in order:** see **[VIDEO_SAY_AND_DO.md](VIDEO_SAY_AND_DO.md)**.

---

## Before You Record

- [ ] **One-click demo env:** Double-click **start-demo-for-recording.bat** to start server + 3 clients (see **[RECORDING_SETUP.md](RECORDING_SETUP.md)**). Or run **build.bat** once, then start server and clients manually during Part 2.
- [ ] Close extra apps; set resolution to 1920×1080 or 1280×720 if required.
- [ ] Test mic; speak clearly. Optionally show face in a corner.
- [ ] Arrange the 4 windows (1 server + 3 clients) so they’re visible for the live demo.

---

## Part 1: Source Code Walkthrough (1 min 15 sec)

### 0:00 – 0:15 — Project structure

**Show:** Project root in IDE or Explorer: `pom.xml`, `tcp-server/`, `tcp-client/`, `docs/`.

**Say:**  
*"This is a Maven multi-module project. We have a TCP server and a TCP client in separate modules, plus a docs folder for UML diagrams."*

---

### 0:15 – 0:45 — Server (30 sec)

**Show (in order):**

1. **TCPServer.java** — *"Entry point: starts the server and the JavaFX UI."*
2. **ServerModel.java** — *"All the logic is here: connections, broadcast, client list, logging. No UI code—that’s the model-view separation."*
3. **ClientHandler.java** — *"One thread per client. It reads the username first, then the message stream, and handles commands like allUsers and bye."*
4. **ServerViewController.java** — *"This only builds the UI and binds to the model’s lists. No network logic."*

---

### 0:45 – 1:15 — Client (30 sec)

**Show (in order):**

1. **TCPClient.java** — *"Main loads config and command-line arguments, then shows the login screen and, after connect, the chat screen."*
2. **ClientModel.java** — *"Socket and messaging: connect, send, disconnect, and the read loop. All network logic is here."*
3. **ClientViewController.java** — *"Login and chat screens using GridPane. It only talks to the model—no sockets."*
4. **config.properties** (in client or server) — *"Host and port come from config so we can change them without recompiling."*

---

## Part 2: Live Demonstration (1 min 45 sec)

### 1:15 – 1:35 — Build and start server (20 sec)

**Do:**

1. Open terminal; run **build.bat** (or `mvn clean package`). If already built, say *"We’ve already built; JARs are ready."*
2. Run **run-server.bat**.
3. Point to the server window: *"Server started on port 3000. We see the timestamped log: Server Started, Waiting for clients."*

---

### 1:35 – 2:00 — First client (25 sec)

**Do:**

1. Run **run-client.bat** (or with `localhost 3000`).
2. Enter a username (e.g. **Alice**), click **Connect**.
3. *"We get the chat screen and the green Online indicator."*
4. Type a short message, press **Enter** or **SEND**.
5. *"The message appears with a timestamp on the server log and in the client. The server distributes to all connected clients."*

---

### 2:00 – 2:30 — Second and third clients (30 sec)

**Do:**

1. Start a **second** client (e.g. **Bob**), send a message. *"Server shows both usernames in the list with different colors."*
2. Start a **third** client; leave username **blank**, click Connect. *"This is read-only mode: the user can see messages but not send."*
3. In Alice or Bob, type **allUsers** and send. *"The server replies with the list of active users."*

---

### 2:30 – 2:45 — Disconnect (15 sec)

**Do:**

1. In one client, type **bye** or click **Disconnect**.
2. *"The user is removed from the server list and we see ‘left’ in the log."*
3. (Optional) Close the server. *"The client shows ‘Disconnected from server’ and the status turns gray."*

---

### 2:45 – 3:00 — Wrap-up (15 sec)

**Do:**

1. Briefly show `tcp-server/target` and `tcp-client/target` (the JARs).
2. **Say:** *"Deliverables: Maven source, executable JARs, UML in docs—class, deployment, and sequence diagrams—plus README and run scripts. The repo is ready for submission. Thank you."*

---

## Video Submission Checklist

Before submitting your video, confirm:

- [ ] **Length:** About 3 minutes (2:45–3:15 is fine).
- [ ] **Content:** Part 1 (code walkthrough) + Part 2 (live run) both included.
- [ ] **Visible:** Code structure, server window, at least two clients, messages and allUsers, disconnect.
- [ ] **Audio:** Clear voice; no heavy background noise.
- [ ] **Format:** As required (e.g. MP4, link to YouTube/Drive, or upload to course platform).

---

## Quick reference

| Time       | Section           | Key point                          |
|-----------|-------------------|------------------------------------|
| 0:00–0:15 | Structure         | Maven, server + client modules     |
| 0:15–0:45 | Server code       | Model vs view, ClientHandler       |
| 0:45–1:15 | Client code       | Model vs view, config              |
| 1:15–1:35 | Build + server     | JARs, run-server, log              |
| 1:35–2:00 | First client       | Connect, send, timestamp            |
| 2:00–2:30 | More clients       | Colors, read-only, allUsers        |
| 2:30–2:45 | Disconnect         | bye, list update, optional close   |
| 2:45–3:00 | Wrap-up            | JARs, deliverables, thank you     |
