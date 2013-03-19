package greyzone.creature;

import java.awt.Color;
import java.util.Arrays;
import jade.path.AStar;
import jade.ui.Terminal;
import jade.util.Dice;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;
import jade.util.datatype.Direction;

/*
 * 
 * {@code Monster} calls the {@code Player} strength and experience points
 * to create appropriately strong monsters. The {@code Monster} notices if it has
 * been attacked and will then follow the {@code Player} for up to number of movements, which
 * is defined by {@code aggressiveness}
 * @author dariush
 */


public class Monster extends Creature
{
	private int origStrength;			// used to buffer the current hitpoint state to monitor for a drop
	private int chaseTime; 				// how long the {@code Monster} has left to chase {@code Player} since last fight	
	private int aggressiveness = 10; 	// number of {@code tick()}s the monster will chase after a fight
	
	int biasMax = 20;
	int biasMin = 10;
	
	private static AStar pathfinder;
	
	public Monster() {
		this(ColoredChar.create('D', Color.red));
	}
    public Monster(ColoredChar face)
    {
        super(face);
        this.setHp(20);
        this.setXp(20);
        origStrength = this.getHp();
        chaseTime = 0;
    }
    

	
	// print a message to the term.terminal
    public void printMessage() throws InterruptedException
    {
	    Terminal term = getWorld().getActor(Player.class).getTerm();

    	while(term.getKey() != 'c')
    	{
	    	term.bufferString(10, 41, "you have met a monster !!", Color.cyan);
	    	term.refreshScreen();
    	}
    }
    
    
    @Override
    public void act()
    {
        
        if( isAttacked() || chaseTime > 0)
        {
        	Coordinate coord = getWorld().getActor(Player.class).pos();	
        	this.move(this.pos().directionTo(coord));
        	chaseTime = (chaseTime + 1) % getAggressiveness();
        }
        else
        {
        	move(Dice.global.choose(Arrays.asList(Direction.values())));
        }
        
    }
    /*
     * if there is a drop in Hit Points then {@code isAttacked} returns true
     * and sets the {@code chaseTime} to the defined level of {@code aggressiveness}
     * Otherwise, it returns a false. 
     * @author dariush
     * @return boolean
     */
    private boolean isAttacked()
    {
		if(!(this.getHp() == origStrength))
		{
	    	System.out.println("this is in the isAttacked()");

			chaseTime = getAggressiveness();
			origStrength = this.getHp();
			return true;
		}
		return false;
    }
    
    
	public int getAggressiveness() {
		return aggressiveness;
	}
	public void setAggressiveness(int aggressiveness) {
		this.aggressiveness = aggressiveness;
	}
        
}
