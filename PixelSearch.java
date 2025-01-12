import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.platform.win32.WinGDI;
import com.sun.jna.platform.win32.WinNT;

public class PixelSearch implements Runnable {


  // Reusable GDI resources
  private static WinDef.HDC hdcWindow;
  private static WinDef.HDC hdcMemDC;
  private static WinDef.HBITMAP hBitmap;
  private static WinNT.HANDLE hOld;

  public PixelSearch() throws AWTException {
    initResources();
  }


  // Initialize reusable GDI resources
  public static void initResources() {
    hdcWindow = User32.INSTANCE.GetDC(null);
    hdcMemDC = GDI32.INSTANCE.CreateCompatibleDC(hdcWindow);
    hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, 1, 1);
    hOld = GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap);
  }


  public static void releaseResources() {
    GDI32.INSTANCE.SelectObject(hdcMemDC, hOld);
    GDI32.INSTANCE.DeleteObject(hBitmap);
    GDI32.INSTANCE.DeleteDC(hdcMemDC);
    User32.INSTANCE.ReleaseDC(null, hdcWindow);
  }

  public interface User32 extends StdCallLibrary {
    User32 INSTANCE = Native.load("user32", User32.class);
    WinDef.HDC GetDC(WinDef.HWND hWnd);
    int ReleaseDC(WinDef.HWND hWnd, WinDef.HDC hDC);
  }

  public interface GDI32 extends StdCallLibrary {
    GDI32 INSTANCE = Native.load("gdi32", GDI32.class);
    int SRCCOPY = 0x00CC0020;
  
    WinDef.HDC CreateCompatibleDC(WinDef.HDC hdc);
    WinDef.HBITMAP CreateCompatibleBitmap(WinDef.HDC hdc, int nWidth, int nHeight);
    WinNT.HANDLE SelectObject(WinDef.HDC hdc, WinNT.HANDLE h);
    boolean BitBlt(WinDef.HDC hdcDest, int nXDest, int nYDest, int nWidth, int nHeight,
                   WinDef.HDC hdcSrc, int nXSrc, int nYSrc, int dwRop);
    int GetDIBits(WinDef.HDC hdc, WinDef.HBITMAP hBitmap, int uStartScan, int cScanLines,
                  int[] lpvBits, WinGDI.BITMAPINFO lpbi, int uUsage);
    boolean DeleteObject(WinNT.HANDLE hObject);
    boolean DeleteDC(WinDef.HDC hdc);
  }

  private int findPixelColorInRegion(int x1, int y1, int x2, int y2) {

    int width = x2 - x1 + 1;
    int height = y2 - y1 + 1;
    
    GDI32.INSTANCE.DeleteObject(hBitmap); 
    hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcWindow, width, height); 
    GDI32.INSTANCE.SelectObject(hdcMemDC, hBitmap); 
    
    
    GDI32.INSTANCE.BitBlt(hdcMemDC, 0, 0, width, height, hdcWindow, x1, y1, GDI32.SRCCOPY);
    
    // array to hold pixel data
    int[] pixelData = new int[width * height];
    
    // use bitmap in order to process pixels
    WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
    bmi.bmiHeader.biSize = new WinDef.DWORD(bmi.bmiHeader.size()).intValue();
    bmi.bmiHeader.biWidth = width;
    bmi.bmiHeader.biHeight = -height; 
    bmi.bmiHeader.biPlanes = 1;
    bmi.bmiHeader.biBitCount = 32;
    bmi.bmiHeader.biCompression = WinGDI.BI_RGB;
    
    // fetch pixel data from region
    GDI32.INSTANCE.GetDIBits(hdcMemDC, hBitmap, 0, height, pixelData, bmi, WinGDI.DIB_RGB_COLORS);
    
    for (int i = 0; i < pixelData.length; i++) {
      int pixelColor = pixelData[i] & 0xFFFFFF;  // strip alpha
      int r = (pixelColor) & 0xFF;               
      int g = (pixelColor >> 8) & 0xFF;          

      // Color comparison logic can go here
      if (Math.abs(r - 250) <= 70 && Math.abs(g - 100) <= 70) {
        return pixelColor;
      }
    }
    
    return -1; // return -1 if no matching color is found

      
  }

  // rgb to hsv
  public static float[] rgbToHsv(int r, int g, int b) {
    float[] hsv = new float[3];
    float min, max, delta;
    
    min = Math.min(r, Math.min(g, b));
    max = Math.max(r, Math.max(g, b));
    delta = max - min;
    
    hsv[1] = (max == 0) ? 0 : delta / max;
    hsv[2] = max / 255.0f;
    
    if (max == min) {
        hsv[0] = 0;
    } else {
      if (max == r) {
        hsv[0] = (g - b) / delta;
      } else if (max == g) {
        hsv[0] = (b - r) / delta + 2;
      } else {
        hsv[0] = (r - g) / delta + 4;
      }
      hsv[0] *= 60;
      if (hsv[0] < 0) {
          hsv[0] += 360;
      }
    }
    return hsv;
  }
  
  // used strictly for debuggin
  public static void captureRegion(int x1, int y1, int x2, int y2, int valid) throws IOException, AWTException {
    
    Robot robot = new Robot();
    Rectangle captureRect = new Rectangle(x1, y1, x2 - x1, y2 - y1); 
    BufferedImage image = robot.createScreenCapture(captureRect);
  
    Path downloadsPath = Paths.get(System.getProperty("user.home"), "Downloads");
    String fileName = "\\screenshots\\screenshot_" + System.currentTimeMillis() + " v"+valid+".png"; 
    File outputFile = new File(downloadsPath.toFile(), fileName);
  
    ImageIO.write(image, "png", outputFile);
    System.out.println("Screenshot saved to: " + outputFile.getAbsolutePath());
  

  }

  @Override
  public void run() {
    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    // INSERT RESOLUTION HERE
    int x = (2560 / 2);
    int y = (1440 / 2);
    int size = 5;
    
    
    
    long currentDelay = 150;
    long lastClickTime = System.currentTimeMillis(); // track last delay
    
    while (true) {
      long startTime = System.currentTimeMillis(); // start time bf loop
      int valid = findPixelColorInRegion(x - size, y - size, x + size, y + size);
      
      
      if (GlobalKeyListener.isTriggerOn && valid != -1) {
        ManageKeyPress.click(); 
        
        try {
          Thread.sleep(currentDelay); 
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        
        // after each click increase the delay by x ms
        currentDelay += 23;
        
        // reset the delay if X ms have passed since the last click
        if (System.currentTimeMillis() - lastClickTime >= 355) {
          currentDelay = 220; // Reset delay to 180ms after 250ms
        }
        
        // update last click time
        lastClickTime = System.currentTimeMillis();
        
        System.out.println("crazy");
      }
      
      long endTime = System.currentTimeMillis();
      long elapsedTime = endTime - startTime;
      System.out.println("Total time taken: " + elapsedTime + " ms");
    }
  }
}
