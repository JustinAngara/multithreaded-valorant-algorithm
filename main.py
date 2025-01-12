import sys
import pyautogui
import time

def press_and_hold_key(key):
    pyautogui.keyDown(key)
    pyautogui.keyUp(key)

# Listen for commands from Java
while True:
    command = sys.stdin.readline().strip()
    if command == "click":
        press_and_hold_key('k')  # Perform the click action
