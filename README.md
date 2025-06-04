# Interactive Valorant Colorbot
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/JustinAngara/multithreaded-valorant-algorithm)

## Overview

This project is a multithreaded application designed to assist in the game Valorant by automatically detecting and reacting to enemies. It primarily functions as a colorbot, scanning a defined area in the center of the screen for a specific enemy color signature. Upon detection, it triggers a Python script to simulate a key press, intended to fire at the enemy.

The application emphasizes performance by leveraging Java for the computationally intensive pixel scanning and multithreading, while Python is used for game interaction, potentially offering a layer of abstraction that might be less detectable by anti-cheat systems like Vanguard.

## Features

*   **High-Performance Pixel Scanning**: Utilizes Java with JNA (Java Native Access) for direct interaction with Windows GDI32 APIs, enabling fast screen capture and analysis (claimed 3-5ms for a 10x10 pixel area).
*   **Target Color Detection**: Scans a 10x10 pixel boundary at the screen center for a configurable color range. Currently configured to detect a reddish/pink hue (R: ~250±70, G: ~100±70).
*   **Multithreaded Architecture**: Employs a thread pool to manage concurrent tasks: pixel searching, global key listening, and process management.
*   **Automated Action via Python**: When a target is detected, a command is sent to a separate Python script (`main.py`) which uses `pyautogui` to simulate a key press (default: 'k').
*   **Dynamic Trigger Control**:
    *   The bot is active if the player is not moving (W, A, S, D keys are not pressed).
    *   Movement will deactivate the bot.
    *   Manual firing (Left Mouse Button) is detected, but the bot's active state in each cycle is primarily determined by movement status.
*   **Dynamic Shot Delay**: Implements an increasing delay between consecutive shots if multiple targets/frames trigger firing, resetting after a pause to help maintain accuracy.
*   **Process Management**: Periodically restarts the Python script and re-initializes GDI resources. This is intended to help manage memory and potentially as a measure against anti-cheat detection.
*   **Experimental Recoil Control/Key Remapping**: Includes `RecoilCrosshair.java`, a separate utility for mouse control and low-level keyboard hooking (e.g., remapping 'Q' to a mouse click at specific coordinates). This component is not integrated into the main bot's execution flow.

## How It Works

The system operates through a series of coordinated, multithreaded processes:

1.  **Screen Region Analysis (`PixelSearch.java`)**:
    *   Continuously captures a small region (default 10x10 pixels) at the center of the screen.
    *   Uses Windows GDI32 functions (`BitBlt`, `GetDIBits`) via JNA for efficient screen capture directly into memory.
    *   Iterates through the pixel data in the captured region.
    *   Converts pixel data to RGB values and checks if they fall within a predefined color range (targeting enemy outlines).
2.  **Player Input Monitoring (`GlobalKeyListener.java`)**:
    *   Monitors keyboard input for movement keys (W, A, S, D) and the left mouse button.
    *   The bot's trigger is enabled only if no movement keys are pressed. Manual shooting is detected but its sustained deactivation effect depends on movement status.
3.  **Action Triggering (`ManageKeyPress.java` & `main.py`)**:
    *   If `PixelSearch` detects the target color and `GlobalKeyListener` indicates the trigger is active, `ManageKeyPress.java` sends a "click" command to `main.py`.
    *   `main.py` receives this command and simulates a 'k' key press using `pyautogui`.
4.  **Dynamic Shot Pacing (`PixelSearch.java`)**:
    *   A delay is introduced after a shot, which increases with rapid consecutive shots to simulate more realistic firing patterns and aid accuracy. This delay resets if there's a pause in firing.
5.  **Process Resiliency (`RestartProcess.java`)**:
    *   A background thread periodically restarts the Python script and re-initializes critical GDI resources used by `PixelSearch`. This is aimed at maintaining stability, managing memory, and potentially refreshing its signature against anti-cheat.

## Core Components (File Breakdown)

*   **`Main.java`**: The entry point of the application. Initializes and manages a thread pool to run the core components concurrently.
*   **`PixelSearch.java`**: Responsible for capturing screen pixels and searching for the target color using JNA for high performance. Contains the main color detection loop and dynamic shot delay logic.
*   **`GlobalKeyListener.java`**: Listens for global keyboard (W, A, S, D) and mouse (LButton) events using JNA to determine if the bot should be active.
*   **`ManageKeyPress.java`**: Manages the Python script (`main.py`) subprocess, including starting, stopping, and sending commands (like "click") to it.
*   **`RestartProcess.java`**: A thread that periodically restarts the Python script and GDI resources to ensure stability and potentially evade detection.
*   **`main.py`**: A Python script that listens for commands from the Java application (via stdin) and uses `pyautogui` to simulate key presses.
*   **`RecoilCrosshair.java`**: A standalone utility for advanced mouse and keyboard manipulation using JNA, including low-level keyboard hooks and mouse event simulation. It's not directly integrated into the main colorbot's execution flow in `Main.java`.

## Technical Highlights

*   **Low-Level API Interaction**: Utilizes JNA (Java Native Access) to interface directly with Windows APIs like User32 (for input hooks) and GDI32 (for screen capture), bypassing slower Java AWT Robot methods for critical performance sections.
*   **Efficient Pixel Analysis**: Claims to scan 100 pixels (10x10 area) within 3-5ms. The pixel search is designed with O(N) time complexity for the 1D pixel array.
*   **Multithreading**: Leverages a `ThreadPoolExecutor` to distribute tasks. Thread priorities are set (e.g., `MAX_PRIORITY` for `PixelSearch`, `MIN_PRIORITY` for `RestartProcess`) as hints to the OS scheduler.

## Prerequisites

*   **Java Development Kit (JDK)**: Version 8 or higher.
*   **Python 3**: For running `main.py`.
*   **`pyautogui` Python library**: Install using pip: `pip install pyautogui`.
*   **JNA**: The project uses JNA libraries. Ensure `jna.jar` and `jna-platform.jar` are available in the classpath if not managed by a build system like Maven/Gradle.
*   **Windows Operating System**: Due to the use of User32 and GDI32.

## Configuration

Before running, you may need to adjust hardcoded paths and values:

1.  **Python Executable and Script Path in `ManageKeyPress.java`**:
    *   Update `pythonPath` to your Python executable location.
    *   Update `scriptPath` to the absolute path of `main.py`.
    ```java
    static String pythonPath = "C:/msys64/ucrt64/bin/python.exe"; // Change this
    static String scriptPath = ""; // Change this
    ```

2.  **Screen Resolution in `PixelSearch.java`**:
    *   The scanning area is centered based on hardcoded screen dimensions. Adjust `x` and `y` if your primary monitor resolution is not 2560x1440.
    ```java
    // INSERT RESOLUTION HERE
    int x = (2560 / 2); // Change 2560 to your screen width
    int y = (1440 / 2); // Change 1440 to your screen height
    ```

3.  **Target Color Range in `PixelSearch.java`**:
    *   Modify the `if` condition in `findPixelColorInRegion` to change the target color. Current: Reddish/Pink (R: 250±70, G: 100±70).
    ```java
    if (Math.abs(r - 250) <= 70 && Math.abs(g - 100) <= 70) {
        return pixelColor;
    }
    ```

4.  **Scan Area Size in `PixelSearch.java`**:
    *   The `size`_variable defines half the width/height of the square scan area (e.g.,_`size = 5`_creates a 10x10 pixel box).
    ```java
    int size = 5;
    ```

## Running the Application

1.  **Compile the Java code**:
    Navigate to the source directory and compile the Java files. If JNA jars are not automatically handled by an IDE or build tool, you'll need to include them in your classpath during compilation and runtime.
    Example (assuming JNA jars are in a `lib` folder):
    ```bash
    javac -cp ".;lib/jna-5.X.X.jar;lib/jna-platform-5.X.X.jar" *.java
    ```
2.  **Run the `Main` class**:
    ```bash
    java -cp ".;lib/jna-5.X.X.jar;lib/jna-platform-5.X.X.jar" Main
    ```
    Adjust classpath (`-cp`) as per your JNA library setup.

The application will start, and the console will show output including thread activity, process restarts, detection times, and any debug messages.

## Important Notes / Disclaimer

*   This software is provided as-is. The use of such tools in online games may violate terms of service and could lead to penalties, including account suspension.
*   The effectiveness of bypassing anti-cheat software like Vanguard is not guaranteed and can change rapidly.
*   Users are responsible for understanding the risks and configuring the software appropriately.
*   The hardcoded paths and values require careful user configuration for the program to function correctly.
