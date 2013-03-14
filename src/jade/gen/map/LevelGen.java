
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


public class LevelGen extends MapGenerator
{
	// TODO:the different types of tiles have to be defined here.
    private ColoredChar floorTile;
    private ColoredChar wallTile;
    private static final String pathToLevelDesign = "simple_map_lab.txt";

    /**
     * Creates a new instance of {@code Maze} with a default open tile of '.' and a default closed
     * tile of '#'.
     */
    public LevelGen()
    {
        this(ColoredChar.create('.'), ColoredChar.create('#'));
    }

    /**
     * Initializes with default parameters.
     * @param floorTile the face of the open tiles
     * @param wallTile the face of the closed tiles
     */
    public LevelGen(ColoredChar floorTile, ColoredChar wallTile)
    {
        this.floorTile = floorTile;
        this.wallTile = wallTile;
    }
    /*
     * TODO:
     * LevelGen so that we can call the mapLoaders
     * for the appropriate map through this class. All the mapLoaders should
     * inherit the methods from this class. 

    public Laboratory_1(ColoredChar floorTile, ColoredChar wallTile, Class mapLoader)
    {
    	
    }
    
     */
	protected void generateStep(World world) 
	{
		System.out.println("in the LevelGenerator");
		
		init(world);
	}
	
	protected void init(World world)
	{
        BufferedReader bufferedReader = null;
        try 
        {
	        bufferedReader = new BufferedReader(new FileReader(pathToLevelDesign));
	        
	        String line = null;
	        char c;	
	        int i = 0;
	        int j = 0;
	        int maxWidth = world.width();
	        while( ((line = bufferedReader.readLine()) != null)  && (i < world.width())  )
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
	        		//System.out.println("this is world.height():"+ world.height());
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
	}//end generateScreen

	@Override
	protected void generateStep(World world, Dice dice) {
		// TODO Auto-generated method stub
		
	}
	
}
