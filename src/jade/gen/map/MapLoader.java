
package jade.gen.map;

import jade.core.World;
import jade.gen.Generator;
import jade.util.Dice;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

public class MapLoader extends MapGenerator
{

    private ColoredChar floorTile;
    private ColoredChar wallTile;
    private static final String pathToFile = "test.txt";

	
	
    public MapLoader()
    {
        this(ColoredChar.create('¤'), ColoredChar.create('#'));
    }

    /**
     * Initializes with default parameters.
     * 
     * This should be exported to the specified levellaoders
     * 
     * @param floorTile the face of the open tiles
     * @param wallTile the face of the closed tiles
     */
    public MapLoader(ColoredChar floorTile, ColoredChar wallTile)
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
        BufferedReader bufferedReader = null;
        try 
        {
	        bufferedReader = new BufferedReader(new FileReader(pathToFile));
	        
	        String line = null;
	        char c;		// holds the char that is read out of the file.txt
	        int i = 0;	// 
	        int j = 0;
	        int maxWidth = world.width();
	        while( ((line = bufferedReader.readLine()) != null)  && (i < world.height())  )
	        {

	        	// don't want to read past the end of line
	        	if( line.length() < world.width()) maxWidth = line.length();
	        	
	        	//System.out.println("this is i: " +i);
	        	System.out.println("this is world.width():" + world.width());
	        	//System.out.println("this is line.length: "+ line.length());
	        	System.out.println(line);
	        	
	        	for(j = 0; j < maxWidth; j++)
	            {
	        		System.out.println("this is j: "+j);
	        		System.out.println("this is maxWidth: "+ maxWidth);
	        		System.out.println("this is line.length():"+ line.length());
	        		c = line.charAt(j);

		            if(c != '.')
		            		world.setTile(wallTile, false, j, i);
		            else
		            		world.setTile(floorTile, true, j,i); 
	            }// end for
	        	
	        	maxWidth = world.width();
	        	i++;
	        	
	        }// end while
        bufferedReader.close();
        
        }// end try
        catch (IOException ex) {
            ex.printStackTrace();
        }
	}//end generateStep
	@Override
	protected void generateStep(World world, Dice dice) {
		// TODO Auto-generated method stub
		generateStep(world);
		
	}
	
}
