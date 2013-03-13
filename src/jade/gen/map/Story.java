package jade.gen.map;

import jade.core.World;
import jade.gen.Generator;
import jade.util.Dice;
import jade.util.datatype.ColoredChar;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Story extends MapGenerator
{
	static {
		
	}
	private static final String END = "screens/endscreen/end.txt";
	private static final String PATH = "screens/story.txt";
	private static LinkedList<String> paths = new LinkedList<String>();

	private String path;
	public static int count = 0;
   

    public Story()
    {
    	this(getPath());
    	System.out.println(count);
    	System.out.println(path);
    }
    
    public Story(LinkedList<String> paths)
    {
    	count++;
    	if (!(paths.isEmpty())) {
    		path = paths.poll();
    		System.out.println("queue is emptys");
    	}

    	else {
    		path = END;
    	}
    }

    public static LinkedList<String> getPath() {
    	BufferedReader in = null;
		try
		{
			in = new BufferedReader(new FileReader(PATH));
			String textRow = null;
			int currentRow=0;
			while ((textRow = in.readLine()) != null) {
				paths.add(textRow); 
				currentRow++;
			}
		} catch (IOException e) {
			System.err.println("error (slide " + count+")");
			System.out.println(System.getProperty("user.dir"));
		}
		finally 
		{
			try {in.close(); } catch ( Exception e ) { }
		}

		return paths;
	}
   
    @Override
	public void generateStep(World world, Dice dice) {
		BufferedReader in = null;
			try
			{
				in = new BufferedReader(new FileReader(path));
				String textRow = null;
				int currentRow=0;
				while ((textRow = in.readLine()) != null) {
					for (int x = 0; x < world.width(); x++)
						world.setTile(ColoredChar.create(textRow.charAt(x)), true, x, currentRow);
					currentRow++;
				}
			} catch (IOException e) {
				System.err.println("error (slide " + count+")");
				System.out.println(System.getProperty("user.dir"));
			}
			finally 
			{
				try {in.close(); } catch ( Exception e ) { }
			}
	}
/*
	@Override
    protected void generateStep(World world, Dice dice)
    {
		
        for(int x = 0; x < world.width(); x++)
            for(int y = 0; y < world.height(); y++)
                    world.setTile(ColoredChar.create(' '), true, x, y);
    }*/
}
