package greyzone.items;

import jade.util.Dice;
import jade.util.datatype.ColoredChar;

public class Food extends Item
{


	private int maxNutrition = 15;
	private final int nutritionLevel = 10;
	
	public Food(ColoredChar face, String name) {
		super(face, name);

	}
	public Food() {
		this(ColoredChar.create('F'),"Food");
	}
	
	public Food(ColoredChar face, String name, int maxNutrition)
	{
		super(face, name);
		this.maxNutrition = maxNutrition;
		
	}

	public int getValue() {
		return nutritionLevel;
	}
	
	private int setValue()
	{
		Dice dice = new Dice();
		return dice.nextInt(1,maxNutrition);
	}

		private static Dice dice;
		private int addedHitPoints = dice.nextInt(5, 15);

		
		@Override
		public void act() 
		{
			
			
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
		public String deliverTextForGameConsole() 
		{		
			return "Yummy !! Food !!" + " You got " + addedHitPoints + " Hit Points !";
		}
		@Override
		public boolean hasText() 
		{
			return true;
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
		
		
		// method to be called from player to get hitpoints
		public int getHitPointsToPlayer()
		{
			return addedHitPoints;
		}
		
		
}
