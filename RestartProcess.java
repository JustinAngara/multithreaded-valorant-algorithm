
public class RestartProcess implements Runnable{

  @Override
  public void run() {
    Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    
    // TODO Auto-generated method stub
    while(true) {
      
      
      PixelSearch.releaseResources();
      PixelSearch.initResources();
      ManageKeyPress.stopPythonProcess();
      ManageKeyPress.startPythonProcess();
      System.out.println("restart");
      try {
        Thread.sleep(35000);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

}
