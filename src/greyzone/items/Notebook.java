package greyzone.items;

import java.awt.Color;

import greyzone.creature.Player;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;

public class Notebook extends Item
{

		// this should be reset whenever a new level is loaded
		private static int nextNotebook = 0;
		// this can be set according to the number of clues in the level loaded
		private static int totalNumOfNotebooks = 4; // counting from 0
		// {@code firstOrLast} is used, at the moment, to print out the first and last {@code textPaths} and {@code framePaths}
		private static int firstOrLast = 0; 
		// {@code messageTimer} counts the number of tick()s before a message is removed from the screen



		private String[] textPaths 	= {	"screens/itemEXP/lab_item_exp.txt",
										"screens/itemEXP/lab_allItemsFound.txt"};
		
		private String[] framePaths = {	"screens/itemEXP/frame_item_exp.txt",
										"screens/itemEXP/frame_allItemsFound.txt"};
		private int messageTimer = 4;
		private boolean startTime;
		private int endTime;
		

		public Notebook() {
			this(ColoredChar.create('N'),"Notebook1");
		}
		public Notebook(ColoredChar face, String name) 
		{
			super(face, name);
			startTime = false;
		}
		
		@Override
		public void act() 
		{
			if(startTime && endTime > 4)
				this.expire();
			endTime = (endTime +1) % messageTimer;
			
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
			if(nextNotebook  < 1 || nextNotebook == totalNumOfNotebooks)
			{
				while(term.getKey() != 'c')
				{
				term.bufferBoxes(getWorld(), framePaths[firstOrLast], textPaths[firstOrLast]);			
				term.refreshScreen();
				firstOrLast = (firstOrLast + 1) % 2;
				}
			}
			else
			{
				while(term.getKey() != 'c')
				{
			    term.bufferString(10, 41, "You have found another Notebook !!", Color.cyan);
			    term.refreshScreen();
				}
			}
	    	startTime = true;
	    	endTime = 0; // starts at 0 and ends at {@code messageTimer}
	    	nextNotebook = (nextNotebook + 1) % totalNumOfNotebooks; // auto reset if always the same number of notebooks
		}		
}
