package greyzone.items;

import greyzone.creature.Player;

import java.awt.Color;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;


public class Clue extends Item
{
	
	// this should be reset whenever a new level is loaded
	private static int nextClue = 0;	// this can be set according to the number of clues in the level loaded
	private static int totalNumOfClues = 4; // counting from 0
	// {@code firstOrLast} is used, at the moment, to print out the first and last {@code textPaths} and {@code framePaths}
	private static int firstOrLast = 0; 
	// {@code messageTimer} counts the number of tick()s before a message is removed from the screen
	private static int messageTimer = 4;

	private String[] textPaths = 	{	"screens/itemEXP/hell_item_exp.txt",
										"screens/itemEXP/hell_allItemsFound.txt"};
	
	private String[] framePaths	= 	{	"screens/itemEXP/frame_item_exp.txt",
										"screens/itemEXP/frame_allItemsFound.txt"};
	private boolean startTime;
	private int endTime;
	
	

	public Clue() {
		this(ColoredChar.create('I'),"Clue1");
		
	}
	public Clue(ColoredChar face, String name) 
	{
		super(face, name);
		startTime = false;
	}
	
	@Override
	public void act() 
	{
		if(startTime && endTime == messageTimer -1)
			this.expire();
		Terminal term = getWorld().getActor(Player.class).getTerm();
		endTime = (endTime +1) % messageTimer;	
	    term.bufferString(10, 41, "You have found another Clue !!", Color.cyan);
	    term.refreshScreen();
	}
	
	// this can be called at the end of a level
	public void resetClueCounter()
	{
		nextClue = 0;
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
		// TODO: 	the string messages that are printed out to the screen
		// 			should go away by themselves after three or four moves.
		
		// we want to print out a from a specific file for the first and last Notebooks found
		if(nextClue  < 1 || nextClue == totalNumOfClues)
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
		    term.bufferString(10, 41, "You have found another Clue !!", Color.cyan);
		    term.refreshScreen();
			}
		}
		
    	startTime = true;
    	endTime = 0; // starts at 0 and ends at {@code messageTimer}	
    	nextClue = (nextClue +1) % totalNumOfClues;
    	
	}

}
