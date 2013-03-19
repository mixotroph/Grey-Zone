package jade.gen.map;

import jade.core.World;
import jade.util.Dice;
import jade.util.datatype.ColoredChar;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Story extends MapGenerator
{
	static {
		
	}
	// path to the endscreen
	private static final String END = "screens/endscreen/end.txt";
	// path to file which contains all other story-paths 
	private static final String PATH = "screens/story.txt";
	private static LinkedList<String> paths = new LinkedList<String>();
	private String path;

    public Story()
    {
    	this(getPath());
    }
    
    public Story(LinkedList<String> paths)
    {
    	if (!(paths.isEmpty())) {
    		path = paths.poll();
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
			while ((textRow = in.readLine()) != null) {
				paths.add(textRow); 
			}
		} catch (IOException e) {
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
						world.setTile(ColoredChar.create(textRow.charAt(x),Color.lightGray), true, x, currentRow);
					currentRow++;
				}
			} catch (IOException e) {
				System.out.println(System.getProperty("user.dir"));
			}
			finally 
			{
				try {in.close(); } catch ( Exception e ) { }
			}
	}
}
