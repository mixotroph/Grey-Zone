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
	        
	        for(int x = 0; x < world.height(); x++)
	        {
	        	line = bufferedReader.readLine();
	        	System.out.println("this is x");
	        	System.out.println("this is line.length: "+ line.length());
	        	System.out.println(line);
	        	for(int y = 0; y < world.width(); y++)
	            {
	        		System.out.println("this is y");
	        		
	        		Coordinate temp = new Coordinate(x,y);
	            	c = line.charAt(y);

	            	if(c != '.')
	            	{
	            		world.setTile(wallTile, false, x, y);
	            	}
	            	else
	            	{
	            		world.setTile(floorTile, true, temp); 
	            	}
	            }// end for
	        }// end for
        bufferedReader.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
	}//end generateScreen
	
    
    private Set<Coordinate> init(World world)
    {
        Set<Coordinate> cells = new HashSet<Coordinate>();

        BufferedReader bufferedReader = null;
        try 
        {
        bufferedReader = new BufferedReader(new FileReader(pathToLevelDesign));
        
        String line = null;
        
        char c;
        int x = 10;
        int y = 10;
        
        Coordinate temp = new Coordinate(x,y);
        world.setTile(wallTile, false, temp);
/*
        for(int x = 0; x < world.width(); x++)
        {
        	line = bufferedReader.readLine();
        	for(int y = 0; y < world.height(); y++)
            {
        		Coordinate temp = new Coordinate(x,y);
            	if(  (c = line.charAt(y)) == '\n')
            	{
            		break;
            	}
            	cells.add(temp);
            	if(c != '.')
            	{
            		world.setTile(wallTile, false, temp);
            	}
            	else
            	{
            		world.setTile(floorTile, true, temp); 
            	}
            }
        }
        */
        bufferedReader.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
		return cells;
    }
        

}
