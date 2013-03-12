package rogue;

import jade.core.World;
import jade.ui.TermPanel;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import jade.ui.Box;
import jade.ui.TermPanel;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import javax.swing.JPanel;
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

        term.registerTile("dungeon.png", 17, 34, ColoredChar.create('+'));
        term.registerMenu();
        
        
        Player player = new Player(term);
        World world = new TestLevel(75, 40, player);
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));

        term.registerCamera(player, 5, 5);
        

        char key = 0;
        //char qKey = 0;
        while(key!='s')
        {
        	term.bufferFile("screens/startscreen/1.txt");
            term.refreshScreen();
        	key=term.getKey();
        }

        term.registerCamera(player, 40,20);
        //Box.addBox(term);
        //Box box1 = new Box(term);
        //box1.setContent("Ein langer Text der noch länger wird und länger");

  
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
            term.bufferStatusBar();

            if(switches.containsKey("a")) term.bufferWorld(world);
            term.bufferFov(player);
            if (term.getMenu("Inv")) term.bufferBoxes(world);    	
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
