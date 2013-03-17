package greyzone;

import greyzone.creature.Monster;
import greyzone.creature.Player;
import greyzone.creature.StoryHandler;
import greyzone.level.Layer;
import greyzone.level.Level;
import greyzone.trigger.Trigger;
import jade.core.World;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import java.awt.Color;
import java.util.HashMap;


public class greyzone
{
    public static void main(String[] args) throws InterruptedException
    {
    	HashMap<String, Boolean> switches = new HashMap<String, Boolean>();
    	
    	for (String sw : args) {
    		switches.put(sw, true);
    	}
    	//System.out.println(switches.toString());
    	
        TiledTermPanel term = TiledTermPanel.getFramedTerminal("Grey Zone");
        term.loadTextureSet("textures/frames.png","textures/frames2.png");
        term.registerMenu();
        
        World world;
        Player player = new Player(term);
        
        if (switches.containsKey("l")) {
            world = new Level(72, 40, "test.txt",player);
        }
        else {
        	world = new Level(72, 40,player);
        }
        //cworld.addActor(new Monster(ColoredChar.create('D', Color.red)));

        term.registerCamera(player, 40,20);
        
        //term.bufferFile("screens/startscreen/title.txt");
        //term.refreshScreen(); 
        //while(term.getKey()!='s'){}
        
        StoryHandler story = new StoryHandler(term);
        World layer = new Layer(80,200, story);
        story.setPos(40, 19);
        term.registerCamera(story, 40,20);
        
        while(!story.expired())
        {
            //term.bufferWorld(layer);	
        	term.bufferCamera(story);
        	
        	
            term.refreshScreen();
            layer.tick();
        }   
        /*       
        term.clearBuffer();
    	term.bufferBoxes(world, "text/fullscreentext/full_frame.txt","text/fullscreentext/descMade.txt"); 
    	term.refreshScreen();;
    	while(term.getKey()!='s'){}
        */
        term.clearBuffer();
        
        while(!player.expired())
        {  
        	term.recallBuffer();
        	term.bufferStatusBar();
        	term.bufferString(1, 5, "steps:");
        	term.bufferString(1, 6, ""+player.getStepCount());
        	term.bufferString(1, 8, "hp:");
        	term.bufferString(1, 9, ""+(int)player.getHp());
        	term.bufferString(1 , 11, "xp:");
        	term.bufferString(1,  12, ""+ (int)player.getXp());
        	//if buffer is cleared only current fov is displayed
        	//term.clearBuffer();
           
            term.bufferFov(player); 
        	term.saveBuffer();
        	
            if (term.getMenu("seeAll")) {
            	//term.saveBuffer();
            	term.bufferWorld(world);
            }
            
        	if (term.getMenu("Inv")){ 
        		term.bufferBoxes(world, "screens/menu/menu-frame.txt","screens/menu/menu.txt");    
        	}
            // last but not least
            term.refreshScreen();
            world.tick();
        }
        
        term.clearBuffer();
        term.refreshScreen();

        layer = new Layer(80,110, story);
        story = new StoryHandler(term);
        layer.removeExpired();
        layer.addActor(story, 40, 19);
        term.registerCamera(story, 40,20);
        
        while(!story.expired())
        {
            ///term.bufferWorld(layer);
            term.bufferCamera(story);
            term.refreshScreen();
            
            //..............................................................................................................
            //for now helpfull:
            //term.bufferString(50, 28, "steps : "+player.getSteps());
            //term.bufferString(50, 30, "hp : "+player.getHp());
            //..............................................................................................................
            
            layer.tick();
           
        }
        System.exit(0);
    }
}
