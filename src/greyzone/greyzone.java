package greyzone;

import greyzone.creature.Player;
import greyzone.creature.StoryHandler;
import greyzone.level.Layer;
import greyzone.level.Level;
import jade.core.World;
import jade.ui.TiledTermPanel;
import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JApplet;

public class greyzone extends JApplet implements KeyEventDispatcher 
{

	private static LinkedList<String> levelPaths = new LinkedList<String>();
	
	private static TiledTermPanel term;
	private static Player player;
	private static World layer;
	private static Thread t;
	private static StoryHandler story = new StoryHandler(term);

	private static void getLevel(String decision) {
		
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader(decision));
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
		/*
		HashMap<String, Boolean> switches = new HashMap<String, Boolean>();

		for (String sw : args) {
			switches.put(sw, true);
		}
		System.out.println(switches.toString());
		*/
		
		Iterable<String> messages;
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
		 
		World world;
		/*
		 * Depending on the first decision, the corresponding  
		 * list of level is chosen here.
		 */
		if (term.getMenu("hell")) {
			getLevel("maps/level_hell.txt");
			world = new Level(72, 40, nextLevel(),Color.RED);
		}
		else {
			getLevel("maps/level_lab.txt");
			world = new Level(72, 40, nextLevel(),Color.ORANGE);
		}
		
		/*
		 *  initializing world and player 
		 */
		Player player = new Player(term);
		term.registerCamera(player, 40,20);
		
		world.addActor(player, 2, 2);
		term.clearBuffer();
		
		if (term.getMenu("hell")) {
			term.bufferBoxes(world, "screens/menu/menu-frame.txt","screens/menu/hell1.txt",Color.lightGray);   
			term.refreshScreen();
			while(term.getKey()!='c'){}
		}
		/*
		 *  main game loop
		 */

		String messageBuffer = ""; 
		while(!player.expired()) 
		{
			term.recallBuffer();
			term.bufferStatusBar(player);
			term.bufferString(10,40,world.retrieveMessages().toString());
			//if buffer is cleared only current fov is displayed

			term.clearBuffer();
			term.bufferStatusBar(player);

			term.bufferFov(player); 
			term.saveBuffer();
			
			/*
			 * handles the wish to see all
			 */
			if (term.getMenu("seeAll")) 
			{
				//term.saveBuffer();
				term.bufferWorld(world);
			}
			
			/*
			 * displays the menue 
			 */
			if (term.getMenu("menu")){ 
				term.bufferBoxes(world, "screens/menu/menu-frame.txt","screens/menu/menu.txt");   
			}
			
			/*
			 * if the exit of the current level is passed, the next level is loaded here
			 */
			if (term.getMenu("nextLevel"))
			{ 
				String path = nextLevel();
				if (path != null) {
					term.setMenu("nextLevel", false);
					world.removeActor(player);

					if (term.getMenu("hell"))
						world = new Level(72, 40, path, Color.RED);
					else 
						world = new Level(72, 40, path, Color.ORANGE);
					world.addActor(player,4,4);

					term.clearBuffer();
					term.saveBuffer();
					term.bufferBoxes(world, "screens/betweenLevel/btwL-frame.txt","screens/betweenLevel/btwL.txt");
				}
				else {
					player.expire();
				}	
			}
			
			messages = player.retrieveMessages();
			for (String msg : messages)
				term.bufferString(8,41,msg,Color.CYAN);
			
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
			//term.bufferWorld(layer);
			term.bufferCamera(story);
			term.refreshScreen();
			layer.tick();
		}
		System.exit(0);
	}

	//// applet methods
	@Override
	public void init() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(this);
		
		Iterable<String> messages;
		term = TiledTermPanel.getFramedTerminal("Grey Zone");
		term.loadTextureSet("textures/frames.png","textures/frames2.png");
		term.registerMenu();
		
		story = new StoryHandler(term);
		layer = new Layer(80,200, story);
		story.setPos(40, 19);
		term.registerCamera(story, 40,20);
		
		System.out.println("applet initialized");
	}
	
	@Override
	public void start() {
		
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				Iterable<String> messages;
				
				while(!story.expired())
				{
					//term.bufferWorld(layer);	
					term.bufferCamera(story);
					term.refreshScreen();
					layer.tick();
				}   
				term.clearBuffer();
				 
				World world;
				/*
				 * Depending on the first decision, the corresponding  
				 * list of level is chosen here.
				 */
				if (term.getMenu("hell")) {
					getLevel("maps/level_hell.txt");
					world = new Level(72, 40, nextLevel(),Color.RED);
				}
				else {
					getLevel("maps/level_lab.txt");
					world = new Level(72, 40, nextLevel(),Color.ORANGE);
				}
				
				/*
				 *  initializing world and player 
				 */
				Player player = new Player(term);
				term.registerCamera(player, 40,20);
				
				world.addActor(player, 2, 2);
				term.clearBuffer();
				
				if (term.getMenu("hell")) {
					term.bufferBoxes(world, "screens/menu/menu-frame.txt","screens/menu/hell1.txt",Color.lightGray);   
					term.refreshScreen();
					try {
						while(term.getKey()!='c'){}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				/*
				 *  main game loop
				 */

				String messageBuffer = ""; 
				while(!player.expired()) 
				{
					term.recallBuffer();
					term.bufferStatusBar(player);
					term.bufferString(10,40,world.retrieveMessages().toString());
					//if buffer is cleared only current fov is displayed

					term.clearBuffer();
					term.bufferStatusBar(player);

					term.bufferFov(player); 
					term.saveBuffer();
					
					/*
					 * handles the wish to see all
					 */
					if (term.getMenu("seeAll")) 
					{
						//term.saveBuffer();
						term.bufferWorld(world);
					}
					
					/*
					 * displays the menue 
					 */
					if (term.getMenu("menu")){ 
						term.bufferBoxes(world, "screens/menu/menu-frame.txt","screens/menu/menu.txt");   
					}
					
					/*
					 * if the exit of the current level is passed, the next level is loaded here
					 */
					if (term.getMenu("nextLevel"))
					{ 
						String path = nextLevel();
						if (path != null) {
							term.setMenu("nextLevel", false);
							world.removeActor(player);

							if (term.getMenu("hell"))
								world = new Level(72, 40, path, Color.RED);
							else 
								world = new Level(72, 40, path, Color.ORANGE);
							world.addActor(player,4,4);

							term.clearBuffer();
							term.saveBuffer();
							term.bufferBoxes(world, "screens/betweenLevel/btwL-frame.txt","screens/betweenLevel/btwL.txt");
						}
						else {
							player.expire();
						}	
					}
					
					messages = player.retrieveMessages();
					for (String msg : messages)
						term.bufferString(8,41,msg,Color.CYAN);
					
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
					//term.bufferWorld(layer);
					term.bufferCamera(story);
					term.refreshScreen();
					layer.tick();
				}
				System.exit(0);
				
			}
		});


		t.start();
		System.out.println("applet started");
	}
	
	
	@Override
	public void stop() {
		System.out.println("applet stopped");
	}
	
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_TYPED)
			term.keyPressed(e);
		return true;
	}
}
