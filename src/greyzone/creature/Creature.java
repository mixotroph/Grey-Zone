package greyzone.creature;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;
import java.lang.Math;
import java.util.Random;

public abstract class Creature extends Actor
{
	 
	private int xp; // experience
	private int hp; // hit-points
	
    public Creature(ColoredChar face)
    {
        super(face);
    }
    
    @Override
    public void setPos(int x, int y)
    {
        if(world().passableAt(x, y))
            super.setPos(x, y);
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
	

	/**
	 * This function calculates the damage of a single hit in
	 * the {@code attack} method.
	 * @param expects the experience points of hitting actor as int
	 * @return damage in int
	 * @author Flo
	 */
	private static int hit(int xp){

	  Random rnd = new Random();

	  //dummy-values for weapon and maximal experience points
	  double weapPrec = 0.5;	//e [0,1]
	  int weapDamage = 5;		//e N\{0}
	  int maxXP = 30;			//e N\{0}

	  //calculate amount of damage for hit
	  return (int)Math.round((rnd.nextDouble()*weapPrec + (1/Math.exp(rnd.nextDouble()*(maxXP +1-xp)))*(1 - weapPrec))*weapDamage);
	} 



	/**
	 * This method simulates a fight between the player and a
	 * creature
	 * @author Flo
	 */
	public void attack(Monster enemy){

	  // XP and HP of enemy
	  int enXP = enemy.getXp();
	  int enHP = enemy.getHp();

	  //XP and HP of player
	  int plXP = this.xp;
	  int plHP = this.hp;

	  //variables for fight
	  boolean fightOver = false;
	  boolean win = true;

	  //fight; currently the player always has the first hit
	  while(!fightOver){

	    enHP -= hit(plXP);

	    // if enemy died...
	    if (enHP <= 0){fightOver = true;}

	    //if enemy didn't die...
	    else{

	      plHP -= hit(enXP);

	      //if player died...
	      if(plHP <= 0){

	        fightOver = true;
	        win = false;
	      }
	    }

	  }

	  //if player won: adjust attribute of player and enemy and print message
	  if (win){

	    enemy.expire();
	    this.xp++;
	    this.hp--;
	    //print message: "You won"
	    System.out.println("You won");
	  }

	  //if enemy won: adjust attributes and print message
	  else{
	    this.expire();
	    //print message: "You lost"
	    System.out.println("You lost");
	  }
	}
	
}