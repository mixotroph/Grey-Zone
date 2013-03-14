//imports for "hit"-method
import java.lang.Math;
import java.util.Random;

//test
public class attack{
//-----


/**
 * This function calculates the damage of a single hit in
 * the {@code attack} method.
 * @param expects the experice points of hitting actor as int
 * @return damage in int
 * @author Flo
 */
private static int hit(int xp){

	Random rnd = new Random();

	//dummy-values for weapon and maximal experice points
	double weapPrec = 0.5;	//e [0,1]
	int weapDamage = 5;	//e N\{0}
	int maxXP = 30;		//e N\{0}

	//calculate amount of damage for hit
	return (int)Math.round((rnd.nextDouble()*weapPrec + (1/Math.exp(rnd.nextDouble()*(maxXP +1-xp)))*(1 - weapPrec))*weapDamage);
}


/**
 * This method simulates a fight between the player and a
 * creature
 * @author Flo
 */
//test
public static void attack(){
//------

	// XP and HP of enemy
	//int enXP = enemy.getXP;
	//int enHP = enemy.getHP;

	//XP and HP of player
	//int plXP = this.xp;
	//int plHP = this.hp;

	int enXP = 5;
	int plXP = 5;
	int enHP = 10;
	int plHP = 10;

	//varibles for fight
	boolean fightOver = false;
	boolean win = true;

	//fight; currently the player allways has the first hit
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

	//if player won: ajust attribute of player and enemy and print message
	if (win){

		//enemy.setExpired();
		//this.xp++;
		//this.hp--;
		//print message: "You won"
		System.out.println("You won");
	}

	//if enemy won: ajust attributes and print message
	else{
		//this.expired = true;
		//print message: "You lost"
		System.out.println("You lost");
	}
}


//test->
public static void main(String[] args){
	//test hit-methode
	System.out.println(hit(10));

	//test attack-methode
	attack();
}
}
//-------
