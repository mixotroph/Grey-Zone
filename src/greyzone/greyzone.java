package greyzone;

import greyzone.creature.Player;
import greyzone.creature.StoryHandler;
import greyzone.level.Layer;
import greyzone.level.Level;
import jade.core.World;
import jade.ui.TiledTermPanel;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class greyzone
{

	private static LinkedList<String> levelPaths = new LinkedList<String>();

	private static void getLevel() {
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader("level.txt"));
			String textRow = null;
			while ((textRow = in.readLine()) != null) {
				levelPaths.add(textRow); 
			}
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
		}
		finally 
		{
			try {in.close(); } catch ( Exception e ) { }
		}
	}

	private static String nextLevel() {
		if (levelPaths.isEmpty())
			return null;
		else
			return levelPaths.poll();
	}

	public static void main(String[] args) throws InterruptedException
	{
		getLevel();
		HashMap<String, Boolean> switches = new HashMap<String, Boolean>();

		for (String sw : args) {
			switches.put(sw, true);
		}
		//System.out.println(switches.toString());
		TiledTermPanel term = TiledTermPanel.getFramedTerminal("Grey Zone");
		term.loadTextureSet("textures/frames.png","textures/frames2.png");
		term.registerMenu();
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
		term.clearBuffer();
		
		Player player = new Player(term);
		term.registerCamera(player, 40,20);
		 
		World world = new Level(72, 40, "test.txt");
		world.addActor(player, 1, 1);

		while(!player.expired()) 
		{

			term.recallBuffer();
			term.bufferStatusBar(player);
			//if buffer is cleared only current fov is displayed
			//term.clearBuffer();
			term.bufferFov(player); 
			term.saveBuffer();

			if (term.getMenu("seeAll")) 
			{
				//term.saveBuffer();
				term.bufferWorld(world);
			}
			
			if (term.getMenu("Inv")){ 
				term.bufferBoxes(world, "screens/menu/menu-frame.txt","screens/menu/menu.txt");   
			}
			
			if (term.getMenu("nextLevel"))
			{ 
				if (nextLevel()!=null) {
					term.setMenu("nextLevel", false);
					world.removeActor(player);
					world = new Level(72, 40, nextLevel());
					world.addActor(player,2,2);
					term.clearBuffer();
					term.saveBuffer();
					term.bufferBoxes(world, "screens/betweenLevel/btwL-frame.txt","screens/betweenLevel/btwL.txt");
				}
				else {
					player.expire();
				}	
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
