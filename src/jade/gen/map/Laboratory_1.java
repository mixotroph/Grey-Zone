package jade.gen.map;

import jade.core.World;
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

import rogue.level.TestLevel;


public class Laboratory_1 extends TestMapGenerator
{
	// TODO:the different types of tiles have to be defined here.
    private ColoredChar floorTile;
    private ColoredChar wallTile;
    private static final String pathToLevelDesign = "test.txt";

    /**
     * Creates a new instance of {@code Maze} with a default open tile of '.' and a default closed
     * tile of '#'.
     */
    public Laboratory_1()
    {
        this(ColoredChar.create('.'), ColoredChar.create('#'));
    }

    /**
     * Initializ es Maze with default parameters.
     * @param floorTile the face of the open tiles
     * @param wallTile the face oof the closed tiles
     */
    public Laboratory_1(ColoredChar floorTile, ColoredChar wallTile)
    {
        this.floorTile = floorTile;
        this.wallTile = wallTile;
    }


	protected void generateScreen(World world) 
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
	
}
