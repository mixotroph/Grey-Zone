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

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
	}

}
