package rogue.creature;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;
import java.lang.Math;
import java.util.Random;


public abstract class Creature extends Actor
{


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
		int weapDamage = 5;	//e N\{0}
		int maxXP = 30;		//e N\{0}

		//calculate amount of damage for hit
		return (int)Math.round((rnd.nextDouble()*weapPrec + (1/Math.exp(rnd.nextDouble()*(maxXP +1-xp)))*(1 - weapPrec))*weapDamage);
	}


	/**
	 * This {@code attack} -method simulates a fight between the player and a
	 * creature
	 * @param expects the enemy as {@code Creature} -Object
	 * @author Flo
	 */
	public void attack(Creature enemy){

		// XP and HP of enemy
		int enXP = enemy.getXp;
		int enHP = enemy.getH;

		//XP and HP of player
		int ownXP = this.xp;
		int ownHP = this.hp;

		//varibles for fight
		boolean fightOver = false;
		boolean win = true;

		//fight; currently the player allways has the first hit
		while(!fightOver){

			enHP -= hit(ownXP);

			// if enemy died...
			if (enHP <= 0){fightOver = true;}

			//if enemy didn't die...
			else{

				ownHP -= hit(enXP);

				//if player died...
				if(ownHP <= 0){

					fightOver = true;
					win = false;
				}
			}

		}

		//if player won: ajust attribute of player and enemy and print message
		if (win){

			enemy.expire();
			this.xp++;
			this.hp--;
			//print message: "You won"
			System.out.println("You won");
		}

		//if enemy won: ajust attributes and print message
		else{
			this.expire();
			//print message: "You lost"
			System.out.println("You lost");
		}
	}
	
	
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
    
    
}
