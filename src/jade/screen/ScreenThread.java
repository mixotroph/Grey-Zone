package jade.screen;

import java.awt.Color;
import jade.ui.TermPanel;

public class ScreenThread extends Thread
{
	private TermPanel term;
	private String path;
	private int pics;
	private int currentPic = 1;
	
  public ScreenThread(TermPanel term, String path, int pics) {
	  this.term = term;
	  this.pics = pics;
	  this.path = path;
	  this.start();
  }
  @Override
  public void run()
  {
    while (!isInterrupted() )
    {
    	if(currentPic <= pics) {		
    		term.bufferFile("screens/"+path+"/"+currentPic+".txt");
    		term.refreshScreen();
       		try {
    			Thread.sleep(500L);
    		} catch (Exception e){
    		}
    		currentPic++;
    	} else {currentPic=1;}
    }
    System.out.println( "Das Ende" );
    }
  public void kill(){
	  this.interrupt();
  }
}
