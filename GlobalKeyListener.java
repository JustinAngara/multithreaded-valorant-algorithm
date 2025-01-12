import com.sun.jna.platform.win32.User32;

public class GlobalKeyListener implements Runnable {
  // implements volatile to ensure threads read most recent thread val
  static volatile boolean isTriggerOn;
  static volatile boolean isMovementKeyPressed = false;
  public GlobalKeyListener() {
      isTriggerOn = true;
  }

  @Override
  public void run() {
    int vkCode = 0x56; // left alt 
    int vkCodeW = 0x57; // W
    int vkCodeA = 0x41; // A
    int vkCodeS = 0x53; // S
    int vkCodeD = 0x44; // D
    int vkLButton = 0x01; // Left Mouse Button
    while (true) { 
      if ((User32.INSTANCE.GetAsyncKeyState(vkLButton) & 0x8000) != 0) {
        // If the left mouse button is held down, set isTriggerOn to false
        isTriggerOn = false;
        System.out.println("NAJAJA");
      }
      isMovementKeyPressed = 
          isKeyDown(vkCodeW) || isKeyDown(vkCodeA) || 
          isKeyDown(vkCodeS) || isKeyDown(vkCodeD);
      
      
      isTriggerOn = !isMovementKeyPressed;
    }
  }
  
  public boolean isKeyDown(int vkCode) {
    return (User32.INSTANCE.GetAsyncKeyState(vkCode) & 0x8000) != 0;
  }
}
