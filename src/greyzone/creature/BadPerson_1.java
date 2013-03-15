package greyzone.creature;

import java.util.Arrays;

import jade.util.Dice;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Direction;

public class BadPerson_1 extends Creature {
	
    private int strength;
    private int experience;

	public BadPerson_1(ColoredChar face) {
		super(face);
		
	}
    public void setStrength(int s)
    {
    	strength = s;
    }
    public void setExperience(int e)
    {
    	experience = e;	
    }
    public int getStrength()
    {
    	return strength;
    }
    public int getExperience()
    {
    	return experience;
    }

	@Override
	public void act() 
	{
        move(Dice.global.choose(Arrays.asList(Direction.values())));
	}

}
