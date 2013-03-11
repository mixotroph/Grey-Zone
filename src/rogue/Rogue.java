package rogue;

import jade.core.World;
import jade.ui.TermPanel;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;
import java.awt.Color;
import java.util.HashMap;
import rogue.creature.Monster;
import rogue.creature.Player;
import rogue.level.Level;

public class Rogue
{
    public static void main(String[] args) throws InterruptedException
    {


 
 //       TermPanel term = TermPanel.getFramedTerminal("Grey Zone");
//        term.registerTile("startscreen.png", 5, 59, ColoredChar.create('#'));
  //      term.registerTile("startscreen.png", 3, 60, ColoredChar.create('.'));
    //    term.registerTile("startscreen.png", 5, 20, ColoredChar.create('@'));
      //  term.registerTile("startscreen.png", 14, 30, ColoredChar.create('D', Color.red));


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
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));

        //term.registerCamera(player, 5, 5);
        
        // hallo
        char key = 0;
        char qKey = 0;
        while(key!='s')
        {
        	term.bufferFile("title_1.txt");
            term.refreshScreen();
        	key=term.getKey();
        }

        term.registerCamera(player, 40,20);
        
        /*
         * buggy animated startscreen
         * 
        ScreenThread startScreen = new ScreenThread(term,"startscreen",4);
        while(term.getKey()!='s'){}
        startScreen.kill();
        */
        
        term.bufferFile("screens/startscreen/title.txt");
        term.refreshScreen();
        while(term.getKey()!='s'){}
        
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
