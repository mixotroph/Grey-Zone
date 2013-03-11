package rogue;

import jade.core.World;
import jade.ui.TermPanel;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import java.awt.Color;
import java.util.HashMap;
import rogue.creature.Monster;
import rogue.creature.Player;
import rogue.level.Level;
import rogue.level.TestLevel;

public class TestRogue
{
    private static final String TeiledTermPanel = null;

	public static void main(String[] args) throws InterruptedException
    {


    	HashMap<String, Boolean> switches = new HashMap<String, Boolean>();
    	
    	for (String sw : args) {
    		switches.put(sw, true);
    	}
    	System.out.println(switches.toString());
    	
        TiledTermPanel term = TiledTermPanel.getFramedTerminal("Grey Zone");
        term.registerTile("dungeon.png", 1, 1, ColoredChar.create('#'));
        term.registerTile("dungeon.png", 1, 34, ColoredChar.create('.'));
        term.registerTile("dungeon.png", 1, 17, ColoredChar.create('@'));
        term.registerTile("dungeon.png", 17, 17, ColoredChar.create('D', Color.red));
  
        Player player = new Player(term);
        World world = new Level(80, 40, player);
        Monster dragon = new Monster(ColoredChar.create('D', Color.red));
        world.addActor(dragon);

        term.registerCamera(player, 40,20);
        
        term.bufferFile("screens/startscreen/title.txt");
        term.refreshScreen();
        while(term.getKey()!='s'){}
        
;		int x = 0;
        while(!player.expired())
        {
        	World.getActors(Monster);
        	x = x+1;
        	term.clearBuffer();
            if(switches.containsKey("a")) term.bufferWorld(world);
            term.bufferFov(player);
            term.refreshScreen();
            world.tick();
        }

        
        
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));
        term.bufferWorld(world);
        term.registerCamera(player, 40,20);
        
        while(!player.expired())
        {
            term.clearBuffer();
            if(switches.containsKey("a")) term.bufferWorld(world);
            term.bufferFov(player);
            term.refreshScreen();
            world.tick();
        }
 
        term.clearBuffer();
        term.bufferFile("screens/endscreen/end.txt");
        term.refreshScreen();
        while(term.getKey()!='q'){}
        /*
         * buggy animated endscreen
         * 
        ScreenThread endScreen = new ScreenThread(term,"endscreen",1);
        while(term.getKey()!='q'){}
        endScreen.kill();
        */
        System.exit(0);

        

        
        
    }
}
