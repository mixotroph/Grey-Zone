package rogue;

import jade.core.World;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import java.awt.Color;
import java.util.HashMap;
import rogue.creature.Monster;
import rogue.creature.Player;
import rogue.creature.StoryHandler;
import rogue.level.Layer;
import rogue.level.Level;

public class Rogue
{
    public static void main(String[] args) throws InterruptedException
    {
    	HashMap<String, Boolean> switches = new HashMap<String, Boolean>();
    	
    	for (String sw : args) {
    		switches.put(sw, true);
    	}
    	System.out.println(switches.toString());
    	
        TiledTermPanel term = TiledTermPanel.getFramedTerminal("Grey Zone");
        term.registerTile("dungeon.png", 1, 1, ColoredChar.create('#'));
        term.registerTile("dungeon.png", 1, 34, ColoredChar.create('¤'));
        term.registerTile("dungeon.png", 1, 17, ColoredChar.create('@'));
        term.registerTile("dungeon.png", 17, 17, ColoredChar.create('D', Color.red));
        term.registerTile("dungeon.png", 17, 34, ColoredChar.create('+'));
        term.registerTile("textfield.png", 1, 1, ColoredChar.create('§'));
        term.registerTile("textfield.png", 17, 1, ColoredChar.create('^'));
        term.registerTile("textfield.png", 34, 1, ColoredChar.create('$'));
        term.registerTile("textfield.png", 1, 17, ColoredChar.create('['));
        term.registerTile("textfield.png", 17, 17, ColoredChar.create('\''));
        term.registerTile("textfield.png", 34, 17, ColoredChar.create(']'));
        term.registerTile("textfield.png", 1, 34, ColoredChar.create('%'));
        term.registerTile("textfield.png", 17, 34, ColoredChar.create('°'));
        term.registerTile("textfield.png", 34, 34, ColoredChar.create('&'));
        term.registerTile("textfield.png", 1, 50, ColoredChar.create('Ö'));
        term.registerMenu();
         

        
        //term.bufferFile("screens/startscreen/title.txt");
        //term.refreshScreen(); 
        //while(term.getKey()!='s'){}
        
        StoryHandler start = new StoryHandler(term);
        World layer = new Layer(80,40, start);
        while(!start.expired())
        {
            term.bufferWorld(layer);	
            term.refreshScreen();
            layer.tick();
        }   
        
        Player player = new Player(term);
        World world = new Level(80, 40, player);
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));
        term.registerCamera(player, 40,20);
    	//term.clearBuffer();
    	//term.bufferBoxes(world, "text/fullscreentext/full_frame.txt","text/fullscreentext/descMade.txt"); 
    	//term.refreshScreen();;
    	//while(term.getKey()!='s'){}
    	term.clearBuffer();
    	
        while(!player.expired())
        {
            //term.bufferStatusBar();
            if(switches.containsKey("a")) term.bufferWorld(world);
            term.bufferFov(player); 
            //if (term.getMenu("Inv")) term.bufferBoxes(world, "text/itemEXP/frame_item_exp.txt","text/itemEXP/h1_item_exp.txt");    	
            term.refreshScreen();
            world.tick();
        }
        
        term.clearBuffer();
        term.refreshScreen();
       
        start = new StoryHandler(term);
        layer = new Layer(80,40, start);
        //layer.addActor(start);
        
        while(!start.expired())
        {
            term.bufferWorld(layer);	
            term.refreshScreen();
            layer.tick();
        }
        
        /*
        term.clearBuffer();
        term.bufferFile("screens/endscreen/end.txt");
        term.refreshScreen();
        while(term.getKey()!='q'){}
        */
        
        System.exit(0);
    }
}
