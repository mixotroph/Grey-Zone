package rogue.creature;

import java.util.Collection;
import jade.fov.RayCaster;
import jade.fov.ViewField;
import jade.ui.Camera;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;
import jade.util.datatype.Direction;

public class Player extends Creature implements Camera
{
    private Terminal term;
    private ViewField fov;
    private int strength;
    private int experience;
    //private Coordinate; // this is where the player should be 
    					// placed when he enters a new level.

    public Player(Terminal term)
    {
        super(ColoredChar.create('@'));
        this.term = term;
        fov = new RayCaster();
    }
    
    ////////////////////////////////////////////////////////////////
    //////////// get set methods  
    ////////////////////////////////////////////////////////////////
    public Terminal getTerm()
    {
    	return term;	
    }
    public void setTerm(Terminal term)
    {
    	this.term = term;
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
    
    ////////////////////////////////////////////////////////////////
    //////////// Methods that were already implemented
    ////////////////////////////////////////////////////////////////
    @Override
    public void act()
    {
        try
        {
            char key;
            key = term.getKey();
            switch(key)
            {
                case 'q':
                    expire();
                    break;
                    
                case '1':
                	term.bufferCamera(this);
                default:
                    Direction dir = Direction.keyToDir(key);
                    if(dir != null)
                        move(dir);
                    break;
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Coordinate> getViewField()
    {
        return fov.getViewField(world(), pos(), 25);
    }
}
