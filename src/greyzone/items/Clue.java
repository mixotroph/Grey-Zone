package greyzone.items;

import jade.util.datatype.ColoredChar;


public class Clue extends Item
{
	private String pathToText;
	private String pathToFrame;
	private static int NEXTCLUE = 0;
	
	private String[] textPaths 			= { 
											"", 
											" "};
	private String[] framePaths 		= { "",
											" "};
	
	
	
	public Clue(ColoredChar face, String name) 
	{
		super(face, name);
	}

	public String getPathToText() {
		return getNextTextPath();
	}

	private void setPathToText(String pathToText) 
	{
		this.pathToText = pathToText;
	}

	public String getPathToFrame() 
	{
		return getNextFramePath();
	}

	private void setPathToFrame(String pathToFrame) {
		this.pathToFrame = pathToFrame;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}
	/*
	 * {@code getNextTextPath()} 
	 * @return the next string from the string array of texts. But if all the strings in the array have
	 * already been used, that is, if we are on the last array, then the method will continue to return
	 * the last string of the array. 
	 */
	private String getNextTextPath()
	{
		if (NEXTCLUE <= textPaths.length) 
			setPathToText(textPaths[NEXTCLUE]);
		NEXTCLUE++;
		return pathToText;
	}
	/*
	 * {@code getNextFramePath()}
	 * @return the next string from the string array of frames. But if all the strings in the array have
	 * already been used, that is, if we are on the last array, then the method will continue to return
	 * the last string of the array.
	 */
	private String getNextFramePath()
	{
		if (NEXTCLUE < framePaths.length) 
			setPathToText(framePaths[NEXTCLUE]);
		NEXTCLUE++;
		return pathToFrame;
	}
	
	
	

}
