# GitHub Submission Guide

Follow these steps to put your project on GitHub and submit the link for Project 1.

---

## Do this now (checklist)

The assignment asks for: **a shared link to your GitHub repository containing the full source code, commit history, and a descriptive README.md.**

| # | Action | Done |
|---|--------|------|
| 1 | Create a **new empty** repo on GitHub (Public; do **not** add README/.gitignore). | ☐ |
| 2 | In project folder: `git init` | ☐ |
| 3 | `git add .` then `git status` (confirm no `target/` or junk). | ☐ |
| 4 | `git commit -m "Initial commit: Group Chat Application"` | ☐ |
| 5 | Second commit for history: e.g. `git add .` then `git commit -m "Add UML diagrams and docs"` | ☐ |
| 6 | `git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git` | ☐ |
| 7 | `git branch -M main` then `git push -u origin main` | ☐ |
| 8 | On GitHub: verify **source code**, **README.md**, and **commit history** are visible. | ☐ |
| 9 | Copy repo URL and submit it (and optionally add it to the top of README.md). | ☐ |

Use **Git Bash** or a terminal where `git` is installed. If `git` is not found in PowerShell, open **Git Bash** from the Start menu (or install [Git for Windows](https://git-scm.com/download/win)).

---

## Step 1: Create a new repository on GitHub

1. Log in to [GitHub](https://github.com).
2. Click **New repository** (or **+** → **New repository**).
3. **Repository name:** e.g. `group-chat-app` or `paradigms-project1`.
4. **Description (optional):** e.g. `Group Chat Application — TCP server/client with JavaFX (Paradigms Mini Project 1)`.
5. Choose **Public**.
6. Do **not** initialize with a README, .gitignore, or license (this project already has them).
7. Click **Create repository**.

---

## Step 2: Initialize Git in your project (if not already)

Open a terminal in your **project root** (the folder that contains `pom.xml`).

If you have never used Git in this folder:

```bash
git init
```

If you already have a `.git` folder, skip to Step 3.

---

## Step 3: Add and commit your files

```bash
git add .
git status
```

Check that you see your source files, `pom.xml`, README, docs, scripts, etc. (and that you are not adding `target/` or IDE files if they are in `.gitignore`).

```bash
git commit -m "Initial commit: Group Chat Application (TCPServer + TCPClient)"
```

Make one or two more commits so there is visible history, for example:

```bash
# After reviewing or making a small change:
git add .
git commit -m "Add UML diagrams and deliverables documentation"
```

---

## Step 4: Connect to GitHub and push

Replace `YOUR_USERNAME` and `YOUR_REPO` with your GitHub username and repository name.

```bash
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git
git branch -M main
git push -u origin main
```

If GitHub asks for authentication, use a **Personal Access Token** or SSH as you prefer.

---

## Step 5: Verify the repository

On GitHub, check that you see:

- [ ] All source code (`tcp-server/`, `tcp-client/`, `pom.xml`)
- [ ] **README.md** (project description, build, run)
- [ ] **DELIVERABLES.md** (submission checklist)
- [ ] **docs/** (architecture.md, *.puml)
- [ ] Run scripts (build.bat, run-server.bat, run-client.bat)
- [ ] **Commit history** with at least 2–3 commits

---

## Step 6: Submit the link

Copy the repository URL (e.g. `https://github.com/yourusername/group-chat-app`) and submit it wherever your instructor asks for the **GitHub link** (form, document, or LMS).

Optional: add the same link at the top of your **README.md** so graders can open it quickly:

```markdown
**GitHub:** [https://github.com/yourusername/group-chat-app](https://github.com/yourusername/group-chat-app)
```

---

## Troubleshooting

- **"target/" or large files:** Ensure `.gitignore` includes `target/` and that you did not add it. If you already committed it, run `git rm -r --cached tcp-server/target tcp-client/target` and commit again.
- **Authentication failed:** Use a Personal Access Token instead of a password, or set up SSH keys and use the SSH URL for `origin`.
- **Wrong remote:** `git remote -v` shows the current remote. To change: `git remote set-url origin https://github.com/USER/REPO.git`.
