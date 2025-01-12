import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ManageKeyPress {
    static String pythonPath = "C:/msys64/ucrt64/bin/python.exe";  // Path to the Python executable
    static String scriptPath = "c:/Users/justi/JavaScript Programs/python shit not rly javascript/main.py";  // Path to the Python script
    static Process pythonProcess;  // Holds the running Python process
    static PrintWriter writer;  // Writer to send commands to the Python process

    // Start the Python process once
    public static synchronized void startPythonProcess() {
      System.out.println("Started Python process");
      try {
        ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath);
        processBuilder.redirectErrorStream(true);
        pythonProcess = processBuilder.start();
        writer = new PrintWriter(new OutputStreamWriter(pythonProcess.getOutputStream()), true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    // Send a trigger to Python to simulate pressing 'K'
    public static synchronized void click() {
      if (writer != null) {
          writer.println("click");
      }
    }

    // Stop the Python process when done
    public static synchronized void stopPythonProcess() {
      if (pythonProcess != null) {
          pythonProcess.destroy();
      }
    }
}
