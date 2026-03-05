# Optional Technical Post — Outline

Use this outline to write a short blog post (e.g. on Medium or Dev.to) about your Group Chat Application. Then add the **link** in your README or submission.

---

## Suggested structure

### 1. Introduction (2–3 short paragraphs)

- What the project is: a TCP-based group chat with a central server and multiple JavaFX clients.
- Why it was done: Paradigms course mini-project to practice Java Sockets, JavaFX, and separation of concerns.
- What the reader will learn: architecture choices, main challenges, and how the pieces fit together.

---

### 2. Architecture overview

- **Server–client model:** One server, many clients; server receives a message from any client and distributes it to all others.
- **Technology stack:** Java, Java Sockets (TCP), JavaFX (GridPane + CSS), Maven.
- **Model–View separation:** Logic and network code in the model; UI in the view. Easier to maintain and test.
- **Configuration:** `config.properties` for server port and client default host/port so deployment can change without recompiling.

---

### 3. Design decisions

- **Thread-per-connection (server):** One thread per client; simple and clear. Mention that for very large numbers of clients, I/O multiplexing (e.g. NIO Selector) would scale better.
- **Protocol:** Simple text protocol (username first, then lines of text). Commands: `allUsers`, `bye`/`end`. Constants in a `Protocol` class to avoid magic strings.
- **Read-only mode:** Clients that connect without a username can only receive messages; server sends `READ_ONLY` and does not accept messages from them.
- **Connection timeout (client):** Socket connects with a timeout so the UI does not hang if the server is unreachable.

---

### 4. Challenges and how you solved them

Pick 2–3 real issues you faced, for example:

- **Keeping the UI responsive:** All network I/O runs in background threads; updates to JavaFX lists/labels are done with `Platform.runLater()` so the UI stays responsive.
- **Knowing when the connection is lost:** The client’s read loop exits when the stream ends; in a `finally` block we run an `onDisconnect` callback on the FX thread to update the status and disable send.
- **Duplicate usernames:** Server checks existing sessions and appends " (2)", " (3)" so each user has a unique name in the list.
- **Clean server shutdown:** `ServerSocket.setSoTimeout(1000)` so the accept loop can check `running` and exit instead of blocking forever.

---

### 5. How to run it

- Clone the repo, run `mvn clean package`, then run the server and clients (or use the provided scripts). One sentence with the link to the repo is enough.

---

### 6. Conclusion

- What you learned (sockets, threads, JavaFX, model–view, config).
- What you might add next (e.g. private messages, persistence, NIO for scalability).

---

## Where to publish

- **Medium** — https://medium.com  
- **Dev.to** — https://dev.to  
- **LinkedIn** — write as an article and link the repo.

After publishing, add the post URL to your README or submission form so it counts as the optional technical post deliverable.
