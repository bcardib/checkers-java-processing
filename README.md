# ♟️ Checkers (Processing 3) — Java/Gradle Pinned Build

![Java](https://img.shields.io/badge/Java-8.0.482--zulu-blue)
![Gradle](https://img.shields.io/badge/Gradle-7.6.4%20(wrapper)-green)
![Processing](https://img.shields.io/badge/Processing-3.3.7-orange)
![Platform](https://img.shields.io/badge/Platform-macOS%20%7C%20Windows-lightgrey)

A graphical **Checkers (Draughts)** implementation built with **Processing** and packaged with a **Gradle wrapper** so anyone can run it with the correct versions.

---

## ✨ Features

- Click-to-select pieces
- Highlights valid destinations
- Captures (jumps)
- King promotion
- Animated movement
- Win detection

**Turn order:** White starts  
**Movement:** White moves downward, Black moves upward

---

## ✅ Versions (Important)

This repo is pinned to a known-working combo:

| Component | Version |
|---|---|
| **Java** | **8.0.482-zulu** |
| **Gradle** | **7.6.4** (via `./gradlew`) |
| **Processing** | **3.3.7** (`org.processing:core:3.3.7`) |

### Why Java 8?
Processing 3 on macOS with modern JDKs (11+) can crash due to removed Apple EAWT APIs such as `com.apple.eawt.QuitHandler`.
This project is configured and verified to run reliably on **Java 8**.

> You’ll see an error like: `NoClassDefFoundError: com/apple/eawt/QuitHandler` if you run with Java 11/17/21.

---

## 📁 Project Structure (as in this repo)

This is the layout inside `Game/` (matches the VS Code tree):

```
Game/
├── src/
│   ├── main/java/
│   │   ├── Checkers/
│   │   │   ├── App.java
│   │   │   ├── Cell.java
│   │   │   ├── CheckersPiece.java
│   │   │   └── Move.java
│   │   └── com/apple/eawt/
│   │       ├── AppEvent.java
│   │       ├── QuitHandler.java
│   │       └── QuitResponse.java
│   └── test/java/Checkers/
│       └── SampleTest.java
├── gradle/wrapper/
│   ├── gradle-wrapper.jar
│   └── gradle-wrapper.properties
├── .sdkmanrc
├── .gitignore
├── build.gradle
├── gradlew
├── gradlew.bat
└── README.md
```

The `com/apple/eawt` package contains minimal compatibility stubs required for Processing 3 to run on modern macOS environments.
> Note: `build/` and `.gradle/` are generated locally and **should not be committed**.

---

## 🚀 Quick Start (Mac / Linux)

### 1) Go to the project
From repo root:
```bash
cd Game
```

### 2) Use Java 8 (recommended: SDKMAN)

Install Java 8 (Zulu) once:
```bash
sdk install java 8.0.482-zulu
```

Activate it (this folder already contains `.sdkmanrc`):
```bash
sdk env
java -version
```

Expected:
```
openjdk version "1.8.0_482"
```

### 3) Run the game (use the wrapper)
```bash
./gradlew clean run -x test
```

---

## 🪟 Quick Start (Windows)

```bat
cd Game
gradlew.bat clean run -x test
```

---

## 🎮 How to Play

1. **Click** one of your pieces to select it.
2. Valid destination squares highlight (blue).
3. Click a highlighted square to move.
4. Captures occur via jumps.
5. Reaching the opposite end promotes the piece to a **King**.
6. The game ends when a player has no pieces remaining.

---

## 🏗️ Architecture (Technical)

### `App.java`
- Processing entry point (`PApplet.main(...)`)
- Manages the render loop + drawing
- Handles input and turn switching
- Detects end-game state

### `Cell.java`
- Represents a single board square
- Stores and draws its piece (if any)
- Handles selection / highlight state

### `CheckersPiece.java`
- Holds piece state (colour, king, captured, position)
- Computes available moves and captures
- Handles promotion

### `Move.java`
- Represents a candidate move
- Stores destination cell and optional captured piece
- Executes move logic (update positions, capture)

---

## 🧪 Tests

Tests live here:
```
src/test/java/Checkers/SampleTest.java
```

Run:
```bash
./gradlew test
```

Generate JaCoCo coverage:
```bash
./gradlew test jacocoTestReport
```

---

## 🧯 Troubleshooting (Common Fixes)

### 1) `NoClassDefFoundError: com/apple/eawt/QuitHandler`
You’re not using Java 8.

Fix:
```bash
cd Game
sdk use java 8.0.482-zulu
# or (preferred, reads .sdkmanrc):
sdk env
./gradlew --stop
./gradlew clean run -x test
```

### 2) `gradle: command not found` or Gradle/JDK mismatch
⚠️ Do NOT use system `gradle` (e.g., `gradle run`).
This project must be run using the included wrapper (`./gradlew`) to guarantee Gradle 7.6.4.
```bash
./gradlew run
```

### 3) `Permission denied` running `./gradlew`
```bash
chmod +x gradlew
./gradlew run
```

### 4) Fresh clone takes time
First run downloads dependencies (Processing). Later runs are faster.

---

## 🧩 Permanent Project Setup (No Repeating This Again)

This repo includes `.sdkmanrc` inside `Game/`:

```
java=8.0.482-zulu
```

To auto-switch Java when entering the folder:

1. Edit SDKMAN config:
```bash
nano ~/.sdkman/etc/config
```

2. Ensure:
```
sdkman_auto_env=true
```

Then simply:
```bash
cd Game
sdk env
```

---

## 🧼 Git Hygiene

Recommended `.gitignore` entries (already included):

- `.gradle/`
- `build/`
- `.vscode/`

---

## 💼 Recruiter Notes (What this demonstrates)

- Clean **OOP design** with clear responsibilities per class
- Event-driven GUI programming (Processing)
- Build tooling + dependency management (Gradle wrapper)
- Environment pinning + reproducible runs (`.sdkmanrc` + Java 8)
- Debugging and resolving real cross-version runtime issues on macOS

---

## 👩‍💻 Author

**Bhakthi Salimath**  
University of Sydney — INFO1113 / COMP9003
