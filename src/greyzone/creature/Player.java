package greyzone.creature;


import greyzone.items.Clue;
import greyzone.items.Food;
import greyzone.items.Money;
import greyzone.items.Notebook;
import greyzone.trigger.Trigger;

import java.awt.Color;
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
/*
 * TODO: {@code handleTrigger()} 
 * 		the player has to be attach()ed to the world again after a new level has 
 * 		been created. 
 */

public class Player extends Creature implements Camera
{
    private TermPanel term;
    private ViewField fov;
    
    
    /*
     *  these are need for printing to the bufferedboxes and for printing to gameConsole for 
     *  {@code gameTextConsoleTimer} number of {@code tick}s.
     *  @author dariush

    private boolean bufferedBoxesActive;
    private boolean gameTextConsoleActive;
    private int endGameTextConsoleTimer;
    private int gameTextConsoleTimer;
    private String pathToCurrFrame;
    private String pathToCurrText;
    private String textForGameConsole;
     */
    
    
	/*
	 * hpDec 
	 * hit points are to decremented every {@ hpDec} number of steps.
	 * the number of steps are counted in {@stepCount} and the modulo operator is
	 * used to decrement the hitpoints number accordingly
	 */
    private int stepCount =0;
    private int hpDec = 10; // hp decremented every hitpointDec steps
    private int bodyCount=0;
    private int cluesNeeded = 5;
	private int cluesFound = 0;

    

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
	public void clearNumOfCluesNeeded()
	{
		int x = 0;
		setNumOfCluesNeeded(x);
	}
	public void clearNumOfCluesFound()
	{
		int x = 0;
		setNumOfCluesFound(x);
	}
	private void setNumOfCluesFound(int x) {
		// TODO Auto-generated method stub		
	}
	private void setNumOfCluesNeeded(int i) {
		// TODO Auto-generated method stub		
	}
	////////////////////////////////////// end get set ////////////////

	private void addStep()
	{	
		// stepCount will never exceed hpDec. It reaches hpDec and
		// is reset to zero
		setStepCount( (getStepCount()+1) % hpDec); 
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
                case ((char)27): // ESC-key
                {
                	if(term.getMenu("menu")==false)
                		term.setMenu("menu",true);
                	else
                		term.setMenu("menu",false);
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


						// HP reducing takes place here
                    	addStep();
                 
                    	if (getStepCount() == 0)
                    		setHp(getHp() - 1);

                    	if (getHp()==0) expire();
                    	}
                    	break;	  	
            }
            contact();
            
            ///////////////////////////  handling of the items printing to console ///////
            /*
            
            if(bufferedBoxesActive)
            {
            	while(this.getTerm().getKey() != 'c')
            	{
            		printToBufferBoxes(pathToCurrFrame, pathToCurrText);
            		printToGameConsole("Press 'c' to continue");
            	}
            	bufferedBoxesActive = false;
            }
            if(gameTextConsoleActive)
            {
            	if(endGameTextConsoleTimer == gameTextConsoleTimer) gameTextConsoleActive = false;
            	printToGameConsole(textForGameConsole);
            	endGameTextConsoleTimer++;
            }
            
            */
            ////////////////////////// end of handling of the items printing to console /////
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
    
    public void contact() throws InterruptedException 
    {
     	Collection<? extends Actor> actors =  getWorld().getActorsAt(Actor.class, pos());
    	for(Actor actor : actors)
    	{
    		String actorClass = actor.getClass().getName();
    		react(actorClass);
    	}
    }
    
    private void react(String ac) throws InterruptedException 
    {
    	if (ac == "greyzone.creature.Monster")
    	{
    		Monster monster = getWorld().getActorAt(Monster.class, pos());
    		if (monster != null) handleMonster(monster);
    	}
    	if (ac == "greyzone.items.Clue"	)
    	{
    		Clue clue = getWorld().getActorAt(Clue.class, pos());
    		if ( clue != null) handleClue(clue);
    		
    	}
    	if (ac == "greyzone.items.Notebook")
    	{
    		Notebook notebook = (Notebook)getWorld().getActorAt(Notebook.class, this.pos());
    		if (notebook != null) handleNotebook(notebook);

    	}
    	if ( ac == "greyzone.item.Food")
    	{
    		Food food = getWorld().getActorAt(Food.class, pos());
    		if (food != null ) handleFood(food); 
    	}
    	
    	if (ac=="greyzone.trigger.Trigger")
    	{
          	Trigger trigger =  getWorld().getActorAt(Trigger.class, pos());
    		if (trigger != null) handleTrigger(trigger);
    	}
    	
    	if (ac=="greyzone.items.Money")
    	{
          	Money money =  getWorld().getActorAt(Money.class, pos());
    		if (money != null) handleMoney(money);
    	}
	}// end react

    
    ///////////////////////////// handle methods ////////////////////////////

    private void handleMoney(Money money) 
    {
		setHp(getHp()+10);
		this.appendMessage("Yummy. You found money! You like that!");
    	money.expire();	
	}

    private void handleMonster(Monster monster)
    {

    	boolean isScientist=false;
    	if(monster.face().toString().equals("Z")||monster.face().toString().equals("S")){ 
	    	isScientist=true;
    	}
    	attack(monster);
    
	    if (isScientist) setBodyCount(getBodyCount()+1);	
    }
    

	private void handleClue(Clue clue) throws InterruptedException
	{	
		setHp(getHp()+10);
		this.appendMessage("Yummy. You found money! You like that!");
		clue.expire();	

	}
	
	private void handleNotebook(Notebook notebook)
	{

		this.appendMessage("You found notebook! You have to find "+ (cluesNeeded - cluesFound)
							+ " more.");
		cluesFound++;
		notebook.expire();	

	}	

    private void handleFood(Food food) 
    {
		setHp(getHp()+10);
		this.appendMessage("Yummy. You found food! You like that!");
    	food.expire();		
	}

	private void handleTrigger(Trigger trigger)
	{		
		//if (cluesFound >= cluesNeeded)
		if ( this.getHp() <= 25 )
		{
			term.setMenu("nextLevel",true);
		}
		else
		{
			System.out.println("You haven\'t found everything yet");
		}
	}// end handleTrigger()

	private void printToBufferBoxes(String framePath, String textPath)
	{
		{
			term.bufferBoxes(getWorld(), framePath, textPath);			
			term.refreshScreen();
		}
	}
	
	
	
	
	/*
	 * @author dariush
	 * @param text is printed to the bottom of the game console for {@code gameTextConsoleTimer} number
	 * of {@code tick}s.
	 */
	private void printToGameConsole(String text)
	{
	    term.bufferString(10, 42, text, Color.cyan);
	    term.refreshScreen();
	}
	
	
}// end Player Class







