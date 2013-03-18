package greyzone.items;

import jade.util.datatype.ColoredChar;

public class Clue extends Item
{
	private String pathToText = "pathToText";
	private String pathToFrame = "pathToFile";
	
	public Clue(ColoredChar face, String name) 
	{
		// TODO Auto-generated constructor stub
		super(face, name);
	}

	public String getPathToText() {
		return pathToText;
	}

	public void setPathToText(String pathToText) {
		this.pathToText = pathToText;
	}

	public String getPathToFrame() {
		return pathToFrame;
	}

	public void setPathToFrame(String pathToFrame) {
		this.pathToFrame = pathToFrame;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
