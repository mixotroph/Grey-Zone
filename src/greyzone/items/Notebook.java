package greyzone.items;
import jade.util.datatype.ColoredChar;

public class Notebook extends Item
{

		// this should be reset whenever a new level is loaded
		private static int nextNotebook;
		// this can be set according to the number of clues in the level loaded
		private static int totalNumOfNotebooks = 4; // counting from 0
		// {@code firstOrLast} is used, at the moment, to print out the first and last {@code textPaths} and {@code framePaths}
		private static int firstOrLast;; 
		// {@code messageTimer} counts the number of tick()s before a message is removed from the screen

		private String[] textPaths 	= {	"screens/itemEXP/lab_item_exp.txt",
										"screens/itemEXP/lab_allItemsFound.txt"};
		
		private String[] framePaths = {	"screens/itemEXP/frame_item_exp.txt",
										"screens/itemEXP/frame_allItemsFound.txt"};
		
		
		
		public Notebook() {
			this(ColoredChar.create('N'),"Notebook1");
		}
		public Notebook(ColoredChar face, String name) 
		{
			super(face, name);
			nextNotebook = 0;
			firstOrLast = 1;
		}
		
		
		
		/*
		 * (non-Javadoc)
		 * @see jade.core.Actor#hasText()
		 * 
		 * If this is the first notebook found on this level
		 * 					OR
		 * If this is the last notebook to find on this level
		 * 					THEN
		 * we don't print a text to the game console because 
		 * there is a bufferedboxes that will be loaded
		 * 
		 * We are also tracking the value of nextNotebook in this method
		 * because this method should be called first by player 
		 */
		@Override
		public boolean hasText() 
		{
			System.out.println("this is in hasText() in Notebook. value of nextNotebook: " + nextNotebook);
			nextNotebook = (nextNotebook + 1) % (totalNumOfNotebooks + 1);

			if ( nextNotebook < 1 || nextNotebook == totalNumOfNotebooks)
			{
				return false;
			}
			return true;
		}
		
		
		
		
		/*
		 * (non-Javadoc)
		 * @see jade.core.Actor#hasTextPath()
		 * 
		 * There should never be a path for a text when there is no path for a frame
		 * Therefore we can call hasFramePath() for a return value.
		 */
		@Override
		public boolean hasTextPath() 
		{
			return hasFramePath();
		}
		
		
		
		/*
		 * @see jade.core.Actor#hasFramePath()
		 * 
		 * if this isn't the first or last notebook on the level then we don't have a path to return 
		 */
		@Override
		public boolean hasFramePath() 
		{
			if(   (nextNotebook > 0)  &&  (nextNotebook < totalNumOfNotebooks)    )
			{
				return false;
			}
			return true;
		}	
		
		
		/*
		 * (non-Javadoc)
		 * @see jade.core.Actor#deliverTextForGameConsole()
		 * 
		 * 
		 * 
		 */
		@Override
		public String deliverTextForGameConsole() 
		{
			return "You have found another Notebook !!";
		}
		
		/*
		 * (non-Javadoc)
		 * @see jade.core.Actor#deliverFramePath()
		 * 
		 * we are also tracking the variable firstOrLast (see top for info) in this method.
		 * 
		 * 
		 */
		
		@Override
		public String deliverFramePath() 
		{
			firstOrLast = (firstOrLast + 1) % framePaths.length; 
			return framePaths[firstOrLast];
		}
		
		
		
		
		/*
		 * (non-Javadoc)
		 * @see jade.core.Actor#deliverTextPath()
		 * 
		 * 
		 * Use the string array textPaths[] and the firstOrLast index to return the correct 
		 * path.
		 * 
		 */
		@Override
		public String deliverTextPath() 
		{
			return textPaths[firstOrLast];
		}
		
		
		
		

		
		@Override
		public void act()
		{

		}
		public static int getTotalNumOfNotebooks() {
			return totalNumOfNotebooks;
		}
		public static void setTotalNumOfNotebooks(int totalNumOfNotebooks) {
			Notebook.totalNumOfNotebooks = totalNumOfNotebooks;
		}

}
