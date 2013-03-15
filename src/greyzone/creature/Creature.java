package greyzone.creature;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;

public abstract class Creature extends Actor
{
	
	private int steps; 
	private int xp; // experience
	private int hp; // hit-points
	
    public Creature(ColoredChar face)
    {
        super(face);
        setSteps(0); // there are zero steps done at beginning
    }

    
    public void addStep()
    {
    	setSteps(getSteps() + 1);
    }
    
    
    
    @Override
    public void setPos(int x, int y)
    {
        if(world().passableAt(x, y))
            super.setPos(x, y);
    }


	public int getSteps() {
		return steps;
	}


	public void setSteps(int steps) {
		this.steps = steps;
	}


	public int getXp() {
		return xp;
	}


	public void setXp(int xp) {
		this.xp = xp;
	}


	public int getHp() {
		return hp;
	}


	public void setHp(int hp) {
		this.hp = hp;
	} 
    
}
