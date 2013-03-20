package greyzone.items;

import java.awt.Color;

import greyzone.creature.Player;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;

public class Food extends Item
{

		// this should be reset whenever a new level is loaded
		private static int nextNotebook = 0;
		// this can be set according to the number of clues in the level loaded
		private static int totalNumOfNotebooks = 4; // counting from 0
		// {@code firstOrLast} is used, at the moment, to print out the first and last {@code textPaths} and {@code framePaths}
		private static int firstOrLast = 0; 
		// {@code messageTimer} counts the number of tick()s before a message is removed from the screen



		private int messageTimer = 4;
		private boolean startTime;
		private int endTime;
		

		public Food() {
			this(ColoredChar.create('N'),"Notebook1");
		}
		public Food(ColoredChar face, String name) 
		{
			super(face, name);
			startTime = false;
		}
		
		@Override
		public void act() 
		{
			if(startTime && endTime > messageTimer)
				this.expire();
			endTime = (endTime +1) % messageTimer;
			
		}
		
		public void handleFood() throws InterruptedException
		{
			printMessage();
	    	startTime = true;
	    	endTime = 0; // starts at 0 and ends at {@code messageTimer}
	    	nextNotebook = (nextNotebook + 1) % totalNumOfNotebooks; // auto reset if always the same number of notebooks
	    	getWorld().removeActor(this);
			
		}
		
		/*
		 * @author dariush
		 * {@code printMessage} uses the int variable {@code nextNotebook} to keep track of
		 * the number of {@code Notebook}s found by {@code Player}. 
		 * Only the first and last {@code Notebook}s found have file.txt to read from. These are
		 * tracked with the int variable {@code firstOrLast}. 
		 * this handles the output to screen for Notebook
		 */
		public void printMessage() throws InterruptedException
		{
			Terminal term = getWorld().getActor(Player.class).getTerm();
			
			// we want to print out a from a specific file for the first and last Notebooks found

				while(term.getKey() != 'c')
				{
			    term.bufferString(10, 41, "Yummy !! FOOD !!", Color.cyan);
			    term.refreshScreen();
				}

		}		
}
