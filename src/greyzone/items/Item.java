package greyzone.items;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;

public abstract class Item extends Actor
{
	
	private final String name;
	
	public Item(ColoredChar face, String name) 
	{
		// TODO Auto-generated constructor stub
		super(face);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
