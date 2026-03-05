# How to Run the Group Chat Application

**Fastest (no Maven/IntelliJ needed):** Run the setup script once, then start the app. See **Option 0** below.

---

## Option 0: One-time setup, then run (recommended)

### First time only — download Maven and build

In **PowerShell** (or Windows Terminal), from the **project folder** (the one that contains `pom.xml`):

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\setup-and-build.ps1
```

This downloads Apache Maven into `.maven\` and builds the project. You only need to do this once (or after you delete the `.maven` folder).

### Run the server and client

From the same project folder:

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\run-app.ps1
```

The **TCP Chat Server** window and the **TCP Client** (Group Chat - Connect) window will open. In the client, enter a username and click **Connect**, then chat.

To run **more clients**, run the same command again (or use **run-client.bat**).

---

## Option A: Run from IntelliJ IDEA

### 1. Open the project

- Open **IntelliJ IDEA**.
- **File → Open** and select the **Paradigms Project** folder (the one that contains `pom.xml`).
- Click **Open**. If asked "Load Maven project?", click **Load** or **Trust Project**.
- Wait until Maven finishes importing (progress bar at the bottom).

### 2. Build the project

- Open the **Maven** tool window (right side, or **View → Tool Windows → Maven**).
- Expand **group-chat-app** → **Lifecycle**.
- Double-click **package** (or right-click **package** → Run Maven Build).
- Wait until you see "BUILD SUCCESS" in the Run window.

### 3. Start the server

- In the Project view, go to **tcp-server** → **src/main/java** → **com.groupchat.server**.
- Open **TCPServer.java**.
- Click the green **Run** button next to `public static void main`, or right-click the file → **Run 'TCPServer.main()'**.
- A window titled **"TCP Chat Server"** should open. You should see "Server Started" and "Waiting for clients..." in the log.

**If you see "Error: JavaFX runtime components are missing":**

- **Run → Edit Configurations**.
- Select **TCPServer** (Application).
- In **VM options** add (adjust the path if your JavaFX is elsewhere):
  ```
  --module-path "C:\path\to\javafx-sdk-21\lib" --add-modules javafx.controls,javafx.fxml
  ```
- Click **Apply** and run again.

### 4. Start one or more clients

- Go to **tcp-client** → **src/main/java** → **com.groupchat.client**.
- Open **TCPClient.java**.
- Click **Run** (green button) or right-click → **Run 'TCPClient.main()'**.
- A **"Group Chat - Connect"** window opens. Enter a **username** (e.g. Alice) and click **Connect**.
- The chat window opens. Type a message and press **Enter** or click **SEND**.

To run a **second client**: click **Run** again (or **Run → Run 'TCPClient.main()'**). Enter another username (e.g. Bob) and connect. Both clients will see each other's messages.

**If the client needs JavaFX VM options:** Edit the **TCPClient** run configuration and add the same VM options as for TCPServer (see above).

### 5. Optional: Program arguments for the client

By default the client connects to **localhost** and port **3000**. To use a different host/port:

- **Run → Edit Configurations** → select **TCPClient**.
- In **Program arguments** type: `localhost 3000` (or another host and port).
- Click **Apply**.

---

## Option B: Run from command line

### 1. Build

- **With local Maven (after Option 0):** Double-click **build.bat** or run `build.bat` in cmd — it will use `.maven\` if present.
- **With Maven in PATH:** Run `mvn clean package` from the project root.

### 2. Start the server

- **Windows:** Double-click **run-server.bat**, or in a terminal run:
  ```bash
  run-server.bat
  ```
- **Mac/Linux:** From the project root:
  ```bash
  java --module-path tcp-server/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-server/target/tcp-server-1.0-SNAPSHOT.jar
  ```

### 3. Start the client

- **Windows:** Double-click **run-client.bat**, or run:
  ```bash
  run-client.bat
  ```
  Or with host and port: `run-client.bat localhost 3000`
- **Mac/Linux:**
  ```bash
  java --module-path tcp-client/target/lib --add-modules javafx.controls,javafx.fxml -jar tcp-client/target/tcp-client-1.0-SNAPSHOT.jar localhost 3000
  ```

---

## Quick checklist

1. **Server** must be running **before** you start any client.
2. **Port:** Server uses port **3000** by default (set in `tcp-server/src/main/resources/config.properties`).
3. **Multiple clients:** Run the client multiple times (different usernames) to simulate several users.
4. **Read-only:** Leave the username **blank** and click Connect to join in read-only mode (see messages only).
5. **Commands:** In the chat, type **allUsers** to see active users; type **bye** or **end** to disconnect.

---

## Troubleshooting

| Problem | What to do |
|--------|------------|
| "Connection refused" or "Server not reachable" | Start the **server** first, then the client. |
| "JavaFX runtime components are missing" | Add VM options (see Option A, step 3). Or use a JDK that includes JavaFX (e.g. Azul Zulu FX). |
| Maven not found | Use **Option A** (IntelliJ); IntelliJ has built-in Maven support. |
| Port already in use | The server **auto-tries the next port** (3001, 3002, …) if 3000 is busy. Check the server window title or log for the actual port, then run the client with it: `run-client.bat localhost 3001`. |
