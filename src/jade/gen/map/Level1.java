package jade.gen.map;

import jade.util.datatype.ColoredChar;

public class Level1 extends MapLoader 
{

    private ColoredChar floorTile;
    private ColoredChar wallTile;

	private static final String  PATH_TO_LEVEL_DESIGN = "pathToFile";

	/*
	 * TODO:
	 * Define chars and etc. needed to generate level. 
	 * 
	 * 
	 * 
	 * 
	 */
	
    public Level1()
    {
        this(ColoredChar.create('.'), ColoredChar.create('#'));
        
    }
	

	
	
	
	public Level1() 
	{
		System.out.println("this is the Level1");
		// TODO Auto-generated constructor stub
	}
	
	protected void init()
	{
		
	}

}
