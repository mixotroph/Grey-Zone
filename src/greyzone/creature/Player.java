package greyzone.creature;


import greyzone.items.Clue;
import greyzone.items.Food;
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
     */
    private boolean bufferedBoxesActive;
    private boolean gameTextConsoleActive;
    private int endGameTextConsoleTimer;
    private int gameTextConsoleTimer;
    private String pathToCurrFrame;
    private String pathToCurrText;
    private String textForGameConsole;
    
	/*
	 * hpDec 
	 * hit points are to decremented every {@ hpDec} number of steps.
	 * the number of steps are counted in {@stepCount} and the modulo operator is
	 * used to decrement the hitpoints number accordingly
	 */
    private int stepCount =0;
    private int hpDec = 10; // hp decremented every hitpointDec steps
    private int bodyCount=0;
    private int numOfCluesNeeded = 5;
	private int numOfCluesFound = 0;

    

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
            
            
            ////////////////////////// end of handling of the items printing to console /////
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
        
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
	}// end react


    @Override
    public Collection<Coordinate> getViewField()
    {
        return fov.getViewField(world(), pos(), 5);
    }
    
    
    
    
    
	
    private void handleMonster(Monster monster)
    {

    	boolean isScientist=false;
    	if(monster.face().toString().equals("Z")||monster.face().toString().equals("S")){ 
	    	isScientist=true;
    	}
    	attack(monster);
    	this.appendMessage("you");
	    if (isScientist) setBodyCount(getBodyCount()+1);	
    }
    
    
    
    
    
    
	/*
	 * The string for the blockbuffer is printed to screen with the appropriate text.txt provided by clue.
	 * The {@code Clue} is at the moment NOT attach()ed, that means NOT held, to the {@code Player}, instead, it is expire()ed.
	 * @param gets a clue
	 */
	private void handleClue(Clue clue) throws InterruptedException
	{	
		if( clue.hasText() )
		{
			textForGameConsole = clue.deliverTextForGameConsole();
			gameTextConsoleActive = true;
			resetGameTextConsoleTimer();
		}
		if( clue.hasFramePath() )
		{
			pathToCurrFrame = clue.deliverFramePath();
			bufferedBoxesActive = true;
			
		}
		if( clue.hasTextPath())
		{
			pathToCurrText = clue.deliverTextPath();
		}
		clue.expire();
	}
	
	
	
	
	
	/*
	 * The appropriate text.txt is loaded to the screen 
	 * The string for the {@code blockBoxes()} is printed to screen 
	 * The {@code Notebook} is at the moment NOT attach()ed to, that means NOT held by, the {@code Player}
	 * @param {@code Notebook}
	 */

	/*
	 * The string for the {@code blockBuffer()} is printed to screen
	 * The the {@code Player} hit points are increased by the amount in {@code Food}
	 * {@code Food} is expire()ed. 
	 * @param {@code Food} 
	 */
	private void handleFood(Food food)
	{
		if( food.hasText() )
		{
			textForGameConsole = food.deliverTextForGameConsole();
			gameTextConsoleActive = true;
			resetGameTextConsoleTimer();
		}
		if( food.hasFramePath() )
		{
			pathToCurrFrame = food.deliverFramePath();
			bufferedBoxesActive = true;
			
		}
		if( food.hasTextPath())
		{
			pathToCurrText = food.deliverTextPath();
		}
		this.setHp(getHp() + food.getHitPointsToPlayer()); 
		food.expire();
	}
	
	
	private void handleNotebook(Notebook notebook)
	{
		if( notebook.hasText() )
		{
			textForGameConsole = notebook.deliverTextForGameConsole();
			gameTextConsoleActive = true;
			resetGameTextConsoleTimer();
		}
		if( notebook.hasFramePath() )
		{
			pathToCurrFrame = notebook.deliverFramePath();
			bufferedBoxesActive = true;
			
		}
		if( notebook.hasTextPath())
		{
			pathToCurrText = notebook.deliverTextPath();
		}
		notebook.expire();
	}
	
	
	
	
	/*
	 * call the length() method on the {@code Player}'s held items
	 * if this number is greater or equal to {@code Clue}s in the level
	 * 			then: remove() {@code Player} from the {@code World} and
	 * 					attach to the next 
	 * param {@code Trigger}
	 */
	private void handleTrigger(Trigger trigger)
	{		
		//if (numOfCluesFound >= numOfCluesNeeded)
		if ( this.getHp() <= 25 )
		{
			term.setMenu("nextLevel",true);
		}
		else
		{
			System.out.println("im not ready yet");
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
	
	
	
	private void resetGameTextConsoleTimer()
	{
		gameTextConsoleTimer = 0;
	}
	
	
	

	@Override
	public String deliverTextForGameConsole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasText() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String deliverFramePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deliverTextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasTextPath() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasFramePath() {
		// TODO Auto-generated method stub
		return false;
	}
	
}// end Player Class







