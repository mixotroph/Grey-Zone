package greyzone.creature;

import jade.core.Actor;
import jade.ui.Terminal;
import jade.util.datatype.ColoredChar;

import java.awt.Color;
import java.lang.Math;
import java.util.Random;

public abstract class Creature extends Actor
{
	private int xp; // experience
	private int hp; // hit-points
	
	//number of contacts to player
	//evaluates if player has done an "fast kill" 
	private int numCont = 0;


	
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
	//get current number of ecounters with player
	public int getNumCont(){
		return numCont;
	}

	//increments "numCont" by one
	public void incremNumCont(){
		numCont++;
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

		//dummy-values for weapon ("Testinator") and maximal experience points
		double weapPrec = 0.5;	//e [0,1]
		int weapDamage = 10;		//e N\{0}
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
		
		enemy.isAttacked();

		// XP and HP of enemy
		int enXP = enemy.getXp();
		int enHP = enemy.getHp();

		//XP and HP of player
		int plXP = this.xp;
		int plHP = this.hp;


		//fight; currently the player always has the first hit

		//damage dealt by player
		int hitPlayer = hit(plXP);

		enHP -= hitPlayer;

		// if enemy died...
		if (enHP <= 0){
		
			//if you performed a "fast kill" on the enemy
			if(enemy.getNumCont() < 4){

				appendMessage("You have gone BERSERK!! +2XP");
				xp += 2;
				enemy.expire();
			}

			//if you killed the enemy with more than 3 hits
			else{
				appendMessage("You killed that one for sure! +1XP");
				xp++;
				enemy.expire();			
			}
		}
		else{
			int hitEnem = hit(enXP);
			
			plHP -= hitEnem;
			
			//if the player died
			if(plHP < 0){
				appendMessage("You get ripped!");
				this.expire();
			}

			//if neigther you nor the enemy died increment the 
			//contact counter of the enemy
			else{
				appendMessage("Damage dealt: "+hitPlayer+"; Damage taken: "+hitEnem);
				setHp(plHP);
				enemy.incremNumCont();
			}
		}
	}



	
}