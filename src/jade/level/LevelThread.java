package jade.level;

import rogue.creature.Player;
import jade.core.World;
import jade.ui.TermPanel;

public class LevelThread extends Thread {
	private TermPanel term;
	private Player player;
	private World world;
	public LevelThread(TermPanel term, Player player, World world) {
		this.term = term;
		this.player = player;	
		this.world = world;
		this.start();
	}
	@Override
	public void run() {
        while(!player.expired())
        {
            term.clearBuffer();
            for(int x = 0; x < world.width(); x++)
                for(int y = 0; y < world.height(); y++)
                    term.bufferChar(x , y, world.look(x, y));
            term.refreshScreen();
            world.tick();
        }
	}
	  public void kill(){
		  this.interrupt();
	  }
}
