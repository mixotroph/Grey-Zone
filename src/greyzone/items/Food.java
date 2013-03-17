package greyzone.items;

import jade.util.Dice;
import jade.util.datatype.ColoredChar;

public class Food extends Item
{

	private final int maxNutrition;
	private final int nutritionLevel = setValue();
	
	public Food(ColoredChar face, String name) {
		super(face, name);
		maxNutrition = 5;
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

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
