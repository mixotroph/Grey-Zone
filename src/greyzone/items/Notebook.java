package greyzone.items;

import java.awt.Color;

import greyzone.creature.Player;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;

public class Notebook extends Item
{

		// this should be reset whenever a new level is loaded
		private static int nextClue = 0;
		// this can be set according to the number of clues in the level loaded
		private static int totalNumOfNotebooks = 4; // counting from 0
		// {@code firstOrLast} is used, at the moment, to print out the first and last {@code textPaths} and {@code framePaths}
		private static int firstOrLast = 0; 


		private String[] textPaths = {"one string",	"sencond string"};
		private String[] framePaths = { "",""};
		private String pathToText;
		private String pathToFrame;
		
		

		public Notebook() {
			this(ColoredChar.create('N'),"Notebook1");
		}

		
		public Notebook(ColoredChar face, String name) 
		{
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
		

		/*
		 * this handles the output to screen for Notebook
		 */
		public void printMessage() throws InterruptedException
		{
			
			Terminal term = getWorld().getActor(Player.class).getTerm();
			
			// we want to print out a from a specific file for the first and last Notebooks found
			if(nextClue < 1 || nextClue == totalNumOfNotebooks)
			{
				while(term.getKey() != 'c'	)
				{
					term.bufferBoxes(getWorld(), framePaths[firstOrLast], textPaths[firstOrLast]);			
					term.refreshScreen();

				}
				firstOrLast = (firstOrLast + 1) % 2;
			}
			else
			{
			}
			
		}
		
}
