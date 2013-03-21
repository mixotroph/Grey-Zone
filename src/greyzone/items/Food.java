package greyzone.items;

import jade.util.Dice;
import jade.util.datatype.ColoredChar;

public class Food extends Item
{
		private static Dice dice;
		private int addedHitPoints = dice.nextInt(5, 15);
		

		public Food() {
			this(ColoredChar.create('F'),"Food");
		}
		public Food(ColoredChar face, String name) 
		{
			super(face, name);
		}
		
		@Override
		public void act() 
		{
			
			
		}

}
