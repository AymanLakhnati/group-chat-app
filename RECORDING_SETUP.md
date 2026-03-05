# Demo Recording Setup

Use this to get the app and windows ready **before** you start the 3-minute demo recording.

---

## 1. One-time: build the project

If you haven’t already, run once:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\setup-and-build.ps1
```

Or double-click **build.bat** (if Maven is available).  
This creates the JARs so you don’t wait for Maven during the actual recording.

---

## 2. Start the demo environment (right before recording Part 2)

**Option A — Double-click (easiest)**  
Double-click **start-demo-for-recording.bat**

**Option B — PowerShell**  
From the project folder:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\demo-for-recording.ps1
```

This script will:

- Free ports 3000–3002 (stops any existing server/client on those ports)
- Start the **TCP Chat Server** (window 1)
- After a few seconds, start **3 clients** (windows 2–4)

You’ll get:

- **1 server window** — “TCP Chat Server — port 3000” (or 3001 if something else uses 3000)
- **3 client windows** — “Group Chat - Connect”

---

## 3. Arrange windows for recording

- Put the **server** window where it’s visible (e.g. left or top).
- Put the **3 client** windows side by side or stacked so you can show:
  - Client 1: username **Alice** → Connect → chat
  - Client 2: username **Bob** → Connect → chat
  - Client 3: **leave username blank** → Connect (read-only)

Optional: set your screen resolution to **1920×1080** or **1280×720** before recording if your assignment requires it.

---

## 4. When to run the script in the video

- **Part 1 (code walkthrough):** Use your IDE; no need to run the script yet.
- **Part 2 (live demo):**  
  - Either run **start-demo-for-recording.bat** (or the PowerShell command) on camera and say you’re starting the server and three clients,  
  - Or run **run-server.bat** then **run-client.bat** three times yourself (as in **DEMO_VIDEO_SCRIPT.md**).

If you use **start-demo-for-recording.bat**, you can say: *“I’m starting the demo environment: server and three clients.”* Then show logging in as Alice, Bob, and read-only, sending messages, and typing **allUsers**.

---

## 5. If the server uses port 3001

If the server window title says **“port 3001”**, the script may have freed 3000 but something else took it. Then:

- Either close everything and run **start-demo-for-recording.bat** again, or  
- Start clients manually with: **run-client.bat localhost 3001** (and same for the other two clients).

---

## Quick checklist before you hit Record

- [ ] Project is built (JARs exist in `tcp-server\target` and `tcp-client\target`)
- [ ] Mic and resolution set
- [ ] Run **start-demo-for-recording.bat** (or run server + 3 clients yourself)
- [ ] Server and 3 client windows are open and arranged
- [ ] **DEMO_VIDEO_SCRIPT.md** open for the talking points

Then start recording and follow the script for Part 1 (code) and Part 2 (live demo).
