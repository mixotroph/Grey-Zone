package rogue.creature;

import java.util.Collection;
import jade.fov.RayCaster;
import jade.fov.ViewField;
import jade.ui.Camera;
import jade.ui.TermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;
import jade.util.datatype.Direction;

public class Player extends Creature implements Camera
{
    private TermPanel term;
    private ViewField fov;
    private int stepamount;

    public Player(TermPanel term)
    {
        super(ColoredChar.create('@'));
        this.term = term;
        fov = new RayCaster();
        stepamount=10; // after stepamount many steps hp gets reduced by 1
        setXp(0);
        setHp(30); // hp at beginning of game
    }

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
                case 'H':
                {
                	if(term.getMenu("Inv")==false)
                		term.setMenu("Inv",true);
                	else
                		term.setMenu("Inv",false);
                }          
                case '1':
                {
                	if(term.getMenu("seeAll")==false)
                		term.setMenu("seeAll",true);
                	else
                		term.setMenu("seeAll",false);
                }    
                default:
                    Direction dir = Direction.keyToDir(key);
                    if(dir != null)
                    {
                    	move(dir);
                    // HP reducing takes place here:..........................................................................
                    	this.addStep();
                 
                    	if (this.getSteps() % this.stepamount == 0)
                    	{
                    		this.setHp(getHp() - 1);
              
                    	}
                    	if (this.getHp()==0) this.expire();
                    //........................................................................................................
                    }
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
        return fov.getViewField(world(), pos(), 5);
    }

	public int getStepamount() {
		return stepamount;
	}

	public void setStepamount(int stepamount) {
		this.stepamount = stepamount;
	}
}
