package greyzone.items;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;

public abstract class Item extends Actor
{
	private final String name;
	
	
	public Item()
	{
		this(ColoredChar.create('I'), "Item");
	}
	public Item(ColoredChar face, String name) 
	{
		super(face);
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
