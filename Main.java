import java.awt.AWTException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Main {
  public static void main(String[] args) throws AWTException {

    // loads multiple threads
    int coreCount = Runtime.getRuntime().availableProcessors(); 
    System.out.println(coreCount);
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(coreCount);

    // submit tasks to the thread pool
    executor.execute(new RestartProcess());
    executor.execute(new GlobalKeyListener());
    executor.execute(new PixelSearch());
    
    // no longer point
    executor.shutdown();
    
    
    
    
    
  }
}
