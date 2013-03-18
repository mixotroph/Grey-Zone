
package jade.gen.map;

import greyzone.trigger.Trigger;
import jade.core.Actor;
import jade.core.World;
import jade.gen.Generator;
import jade.gen.feature.Fence;
import jade.util.Dice;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.mockito.Mockito;



/**
 * This is called from the rogue.level.Level.
 * It creates a new instance of {@code MapLoader} 
 * It implements rudimentary methods needed
 * And it calls the proper LevelLoader ( i.e. the Class with the 
 * specifications for level to be loaded). 
 *  
 * TODO: 
 * We have to know which world we are in in order to load the next world.
 * 
 * TODO:
 * Camera should not throw out of bounds exception when the it gets to the
 * end of the world.
 * 
 * TODO:
 * Every Level should extend this class and do the actual loading.
 * 
 * TODO: 
 * The init(world) method 
 * Define rules and implement in a way that allows the actual level laoders
 * to implement it. 
 * 
 * 
 */

public class MapLoaderChris extends MapGenerator
{
    private Map<Character,ColoredChar> passable;
    private Map<Character,String> trigger;
    private String pathToFile;
    private Color color = Color.ORANGE;

    public MapLoaderChris(String path)
    {
    	pathToFile = path;
    	trigger = new HashMap<Character,String>();
    	trigger.put('E', "greyzone.trigger.Trigger");
    	trigger.put('M', "greyzone.creature.Monster");
    	trigger.put('S', "greyzone.trigger.Message");
    	
    	// Dariush: added to test the food, clue, notebook classes
    	trigger.put('F', "greyzone.items.Food");
    	trigger.put('N', "greyzone.items.Notebook");
    	trigger.put('I', "greyzone.items.Clue");    	
    	
    	passable = new HashMap<Character,ColoredChar>();
    	passable.put('¤', ColoredChar.create('¤'));
    	passable.put('A', ColoredChar.create('('));
    	passable.put('B', ColoredChar.create(')'));
    	passable.put('C', ColoredChar.create('{'));
    	passable.put('D', ColoredChar.create('}'));
    	passable.put('E', ColoredChar.create('¤'));
    	passable.put('M', ColoredChar.create('¤'));
    	
    	
    	// Dariush: added to test the food, clue, notebook classes
    	passable.put('F', ColoredChar.create('{'));
    	passable.put('N', ColoredChar.create('}'));
    	passable.put('I', ColoredChar.create('¤'));
    	
    	
    	
    }

    /**
     * Initializes with default parameters.
     * 
     * This should be exported to the specified level laoders
     * 
     * @param passable Map with face of the open tiles
     */
    public MapLoaderChris(ColoredChar floorTile, ColoredChar wallTile)
    {

    }    
    
    // this calls the init(world) method which fills the world accordingly.
	protected void generateStep(World world, Dice dice) 
	{
		init(world);
	}

	// fills the world according to rules defined by specified LevelLoader
	protected void init(World world)
	{
		int currentRow=0;
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader(pathToFile));
			String textRow = "";
			while (currentRow != world.height()){
				textRow = "";
				// if the map has not enough rows to fill the world, add some
				if ((textRow = in.readLine()) == null) 
					textRow = fillLine(world.width());
				// if there are rows shorter than the worlds width, fill them up
				if ((textRow.length() < world.width())) {
					for (int i = textRow.length(); i < world.width(); i++) 
						textRow+='.';			
				}
				for (int x = 0; x < world.width(); x++)
				    if (passable.containsKey(textRow.charAt(x))) {
				    	/*
				    	 * If the current char is some kind of trigger, create a actor class 
				    	 * depending on the char
				    	 */
				    	if (trigger.containsKey(textRow.charAt(x))) {
							try {
					    		Class<?> c = Class.forName(trigger.get(textRow.charAt(x)));
				    			Object o = c.newInstance();
				    			world.addActor((Actor) o, x, currentRow);
							} catch (Exception e) {
								e.printStackTrace();
							}
				    	}
				    	world.setTile(passable.get(textRow.charAt(x)) , true, x, currentRow);
				    }
					else
						world.setTile(ColoredChar.create(textRow.charAt(x),color), false, x, currentRow);
				currentRow++;
			}
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
		}
		finally 
		{
			try {in.close(); } catch ( Exception e ) { }
		}		
	} //end generateStep

	private String fillLine(int width) {
		String line="";
		for (int x = 0; x < width; x++) {
			line += ".";
		}
	return line;
	}	
}