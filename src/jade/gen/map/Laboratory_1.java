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
     * @param String 
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
		Set<Coordinate> cells = init(world);
	}
	
    
    private Set<Coordinate> init(World world)
    {
        Set<Coordinate> cells = new HashSet<Coordinate>();
        BufferedReader bufferedReader = null;
        try 
        {
        bufferedReader = new BufferedReader(new FileReader(pathToLevelDesign));
        
        String line = null;
        char c;
        Coordinate temp = new Coordinate(0,0);
        for(int x = 0; x < world.width(); x++)
        {
        	line = bufferedReader.readLine();
        	for(int y = 0; y < world.height(); y++)
            {
            	c = line.charAt(y);
            	cells.add(new Coordinate(x,y));
            	if(c != '.')
            	{
            		world.setTile(wallTile, false, x,y);
            	}
            	else
            	{
            		world.setTile(floorTile, true, x,y); 
            	}
            }
        }
        bufferedReader.close();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
		return cells;
    }
        

}
