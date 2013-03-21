package greyzone.creature;

import java.awt.Color;
import java.util.Arrays;
import jade.util.Dice;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Direction;


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
	        	//pos(a).getDirectionTo(pos(b)): gets the next coord on the best path from a to b
	        	nextDir = this.pos().directionTo(getWorld().getActor(Player.class).pos());// get player position
	        	if(  isFree(this.pos().x() + nextDir.dx(), this.pos().y() + nextDir.dy()	) )
	        		move(nextDir);
	        	chaseTime++;
	        }
	        else
	        {
	        	nextDir = Dice.global.choose(Arrays.asList(Direction.values()));
	        	if ( isFree(  pos().x() + nextDir.dx(), pos().y() + nextDir.dy())	)
	        		move(nextDir);  
	        }       
    	}//end try
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }// end act
    public void isAttacked()
    {
    	chaseTime =0;
    	attacked = true;
    }
}
