package greyzone.creature;


import greyzone.items.Clue;
import greyzone.items.Food;
import greyzone.items.Notebook;
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
    
	/*
	 * hpDec 
	 * hit points are to decremented every {@ hpDec} number of steps.
	 * the number of steps are counted in {@stepCount} and the modulo operator is
	 * used to decrement the hitpoints number accordingly
	 */
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


	private void addStep()
	{	
		// stepCount will never exceed hpDec. It reaches hpDec and
		// is reset to zero
		setStepCount( (getStepCount()+1) % hpDec); 
	}
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
                    	

// HP reducing takes place here:..........................................................................

                     	Trigger trigger =  getWorld().getActorAt(Trigger.class, pos());
                    	String messages;
                     	
						if (trigger != null) 
						{
                    		messages = trigger.retrieveMessages().toString();
                    		System.out.println(messages);
                    		expire();
						}
// HP reducing takes place here:..................................................
                    	addStep();
                 
                    	if (getStepCount() == 0)
                    		setHp(getHp() - 1);

                    	if (getHp()==0) expire();
 //..............................................................................
                    }
                    	break;	
                    	
            }
            
            contact();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        
        //interaction();
    }
    
    public void contact() {
    	
     	Collection<? extends Actor> actors =  getWorld().getActorsAt(Actor.class, pos());
    	for(Class<? extends Actor> actor : actors)
     	
     	
     	String messages;
     	
		if (actor != null) {
    		messages = actor.retrieveMessages().toString();
    		System.out.println(messages);
    		expire();
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
    	if (ac == "greyzone.creature.Monster")
    	{
    		Monster monster = getWorld().getActorAt(Monster.class, pos());
    		String messages;
    		if (monster != null)
    		{
    			messages = monster.retrieveMessages().toString();
    			System.out.println(messages);
    			attack(monster);
    		}
    	}
    	if (ac == "greyzone.items.Clue"	)
    	{
    		Clue clue = getWorld().getActorAt(Clue.class, pos());
    		String messages;
    		if (clue != null)
    		{
    			messages = clue.retrieveMessages().toString();
    			System.out.println(messages);
    			handleClue(clue);
    		}
    	}
    	if (ac == "greyzone.items.Notebook")
    	{
    		
    		Notebook notebook = (Notebook) getWorld().getActorsAt(Notebook.class, pos());
    		String messages;
    		if (notebook != null)
    		{
    			messages = notebook.retrieveMessages().toString();
    			System.out.println(messages);
    			handleNotebook(notebook);
    		}
    	}
    	if ( ac == "greyzone.item.Food")
    	{
    		Food food = getWorld().getActorAt(Food.class, pos());
    		String messages;
    		if (food != null )
    		{
    			messages = food.retrieveMessages().toString();
    			System.out.println(messages);
    			handleFood(food); 
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
	
	/*
	public void interaction()
	{	
		if (getWorld().getActorsAt(greyzone.creature.Monster.class , pos()) != null){
			Collection<Monster> monsterCol = 
					getWorld().getActorsAt(greyzone.creature.Monster.class , pos());
			for (Monster monster : monsterCol){
				attack(monster);
			}
		}
		else if (getWorld().getActorAt(greyzone.items.Clue.class , pos()) != null) {
			Clue clue=getWorld().getActorAt(greyzone.items.Clue.class , pos());
			handleClue(clue);
		}
		
		else if (getWorld().getActorAt(greyzone.items.Notebook.class , pos()) != null) {
			Notebook notebook=getWorld().getActorAt(greyzone.items.Notebook.class , pos());
			handleNotebook(notebook);
		}
		else if (getWorld().getActorAt(greyzone.items.Food.class , pos()) != null) {
			Food food=getWorld().getActorAt(greyzone.items.Food.class , pos());
			handleFood(food);
		}
		
		
		else if (getWorld().getActorAt(Trigger.class , pos()) != null ) {
			Trigger trigger=getWorld().getActorAt(Trigger.class , pos());
			handleTrigger(trigger);
		}
	}
	*/
	
	private void handleClue(Clue clue)
	{
		this.itemsHeld += 1;
		
		//show text: h1_item_exp.txt
		//quit when q is pressed
		
		clue.expire();
		//if (this.itemsHeld==clue.cluesHidden) 
	}
	
	private void handleNotebook(Notebook notebook)
	{
		this.itemsHeld += 1;
		
		//show text: l1_item_exp.txt
		//quit when q is pressed
		
		notebook.expire();
	}
	
	private void handleFood(Food food)
	{
		this.setHp(getHp() +food.getValue());
		
		//show text: uEat.txt
		//quit when q is pressed
		
		food.expire();
		}
	
	private void handleTrigger(Trigger trigger)
	{
		// is itemsHeld = numOfclues ?
		// if yes react
	}
	

}
