
package jade.gen.map;

import jade.core.World;
import jade.gen.Generator;
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
    private ColoredChar floorTile;
    private ColoredChar wallTile;
  //  private Map<Character,ColoredChar> pas;
    private Map<Character,ColoredChar> passable;
    private static final String pathToFile = "test.txt";

    public MapLoaderChris()
    {
     //this(ColoredChar.create('¤'), ColoredChar.create('#'));
     passable = new HashMap<Character,ColoredChar>();
    // pas = new HashMap<Character, ColoredChar>();
     passable.put('¤', ColoredChar.create('¤'));
     //passable.put('(', ColoredChar.create('¤'));
     //passable.put(')', ColoredChar.create('¤'));
     //passable.put('{', ColoredChar.create('¤'));
     //passable.put('}', ColoredChar.create('¤'));

     // Read more: http://javarevisited.blogspot.com/2011/06/converting-array-to-arraylist-in-java.html#ixzz2NXUAN1KA
     
    }

    /**
     * Initializes with default parameters.
     * 
     * This should be exported to the specified levellaoders
     * 
     * @param floorTile the face of the open tiles
     * @param wallTile the face of the closed tiles
     */
    public MapLoaderChris(ColoredChar floorTile, ColoredChar wallTile)
    {
        this.floorTile = floorTile;
        this.wallTile = wallTile;
    }    
    
    // this calls the init(world) method which fills the world accordingly.
	protected void generateStep(World world) 
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
			
			//while (((textRow = in.readLine()) != null)  ){
			while ((currentRow != world.height())){
				System.out.println(currentRow);
				textRow = "";
				if ((textRow = in.readLine()) == null) {textRow = "################################################################################ 	";}
				if ((textRow.length() < 40)) {
					//if (((textRow = in.readLine()) != null) && !("".equals(textRow))) {
				  textRow = "################################################################################";
				}
				for (int x = 0; x < world.width(); x++)
					//if (textRow.charAt(x) == '¤')
				    if (passable.containsKey(textRow.charAt(x))) {
						world.setTile(ColoredChar.create(textRow.charAt(x)) , true, x, currentRow);
				    }
					else
						world.setTile(ColoredChar.create(textRow.charAt(x)), false, x, currentRow);
				currentRow++;
			}
		} catch (IOException e) {
			System.out.println(System.getProperty("user.dir"));
		}
		finally 
		{
			try {in.close(); } catch ( Exception e ) { }
		}		
        
	}//end generateStep

	private String fillLine(World world) {
		String line="";
		for (int x = 0; x < world.width(); x++) {
			line += "#";
		}
	return null;
	}

	@Override
	protected void generateStep(World world, Dice dice) {
		// TODO Auto-generated method stub
		generateStep(world);
		
	}
	
}