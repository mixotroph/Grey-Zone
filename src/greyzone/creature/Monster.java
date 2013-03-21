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
 * {@code Monster} calls the {@code Player} strength and experience points
 * to create appropriately strong monsters. The {@code Monster} notices if it has
 * been attacked and will then follow the {@code Player} for up to number of movements, which
 * is defined by {@code aggressiveness}
 * @author dariush
 */


public class Monster extends Creature
{
	private int chaseTime; 				// how long the {@code Monster} has left to chase {@code Player} since last fight	
	private int aggressiveness = 1; 	// number of {@code tick()}s the monster will chase after a fight

	private boolean attacked;
	private Direction nextDir;

	
	
	
    public Monster(ColoredChar face)
    {
        super(face);
        this.setHp(20);
        this.setXp(20);
        chaseTime = 0;
        attacked = false;
        this.setPassable(false);
    }
	public Monster() 
	{
		this(ColoredChar.create('D', Color.red));
	}
	
	public int getAggressiveness() 
	{
		return aggressiveness;
	}
	public void setAggressiveness(int aggressiveness) 
	{
		this.aggressiveness = aggressiveness;
	}

    @Override
    public void act()
    {

    	try
    	{
	        if( attacked && (chaseTime < getAggressiveness()))
	        {
	        	nextDir = this.pos().directionTo(getWorld().getActor(Player.class).pos());// get player position
	        	//if(  isFree(this.pos().x() + nextDir.dx(), this.pos().y() + nextDir.dy()	) )
	        		move(nextDir);
	        	chaseTime++;
	        }
	        else
	        {
	        	nextDir = Dice.global.choose(Arrays.asList(Direction.values()));
	        	if ( isFree(  pos().x() + nextDir.dx(), pos().y() + nextDir.dy())	)
	        		move(nextDir);  
	        }
        
    	}
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        

    }
    	
    	
    public void isAttacked()
    {
    	chaseTime =0;
    	attacked = true;
    }
    

}
