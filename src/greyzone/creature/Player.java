package greyzone.creature;


import greyzone.trigger.Trigger;
import java.util.Collection;
import jade.core.Actor;
import jade.fov.RayCaster;
import jade.fov.ViewField;
import jade.ui.Camera;
import jade.ui.TermPanel;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;
import jade.util.datatype.Direction;


public class Player extends Creature implements Camera
{
    private TermPanel term;
    private ViewField fov;
    
    private int stepCount =0;
    private int hpDec = 10; // hp decremented every hitpointDec steps
    private int itemsHeld=0;
    private int bodyCount=0;
    

    public Player(TermPanel term)
    {
        super(ColoredChar.create('@'));
        this.term = term;
        fov = new RayCaster();
        setXp(0);
        setHp(30); // hp at beginning of game
    }
    
	// this increments @stepcount at every step
	private void addStep()
	{
		setStepCount( (getStepCount() +1) % hpDec); // stepCount won't ever be larger than hpDec
	}

    
    ////////////////////////////////////////////////////////////////
    //////////// get set methods  
    ////////////////////////////////////////////////////////////////
    public Terminal getTerm()
    {
    	return term;	
    }
    public void setTerm(TermPanel term)
    {
    	this.term = term;
    }
    public void setHpDec(int newDecNum )
    {
    	hpDec = newDecNum;
    }
    public int getHpDec()
    {
    	return hpDec;
    }

	public int getBodyCount() {
		return bodyCount;
	}

	public void setBodyCount(int bodyCount) {
		this.bodyCount = bodyCount;
	}

	public int getStepCount() {
		return stepCount;
	}

	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}

	public int getItemsHeld() {
		return itemsHeld;
	}

	public void setItemsHeld(int itemsHeld) {
		this.itemsHeld = itemsHeld;
	}

    ////////////////////////////////////////////////////////////////
    //////////// Methods that were already implemented
    ////////////////////////////////////////////////////////////////
    @Override
    public void act()
    
    {	
    	Actor actor;
   
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
                     	Trigger trigger =  getWorld().getActorAt(Trigger.class, pos());
                    	String messages;
                     	
						if (trigger != null) {
                    		messages = trigger.retrieveMessages().toString();
                    		System.out.println(messages);
                    		expire();
						}
 // HP reducing takes place here:..................................................
                    	addStep();
                 
                    	if (getStepCount() == 0)
                    	{
                    		setHp(getHp() - 1);
              
                    	}
                    	if (getHp()==0) expire();
 //..............................................................................
                    }
                    	break;
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        
    }

    //@Override
    public void contact() {
    	
    	Iterable<Actor> actor = getWorld().getActorsAt(Actor.class, pos());
    	
    	for (Actor ac : actor) {
    		
    		react(ac.getClass().getName());
    		
    	}
    }
    private void react(String ac) {
    	
    	if (ac=="greyzone.trigger.Trigger")
    	{
          	Trigger trigger =  getWorld().getActorAt(Trigger.class, pos());
        	String messages;
         	
    		if (trigger != null) {
        		messages = trigger.retrieveMessages().toString();
        		System.out.println(messages);
        		expire();
    		}
    	}
	}

	@Override
    public Collection<Coordinate> getViewField()
    {
        return fov.getViewField(world(), pos(), 5);
    }
	
	/*
	 * contact made:
	 * contactMade():
	 * uses the trigger and finds out if the player is at the same place with
	 * any other actors. If yes, which actor?
	 * Use a switch to determine and act accordingly.
	 * 
	 * 
	 */

}
