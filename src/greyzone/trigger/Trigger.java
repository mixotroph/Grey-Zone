package greyzone.trigger;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;

public class Trigger extends Actor 
{
	
	public Trigger() 
	{
		this (ColoredChar.create('T'));
	}
	public Trigger(boolean b)
	{
		this (ColoredChar.create('T'));
		setActive(b);
	}

	public Trigger(ColoredChar face) {
		super(face);
		
	}

	@Override
	public void act() {
		
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
	public String deliverTextForGameConsole() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasText() {
		// TODO Auto-generated method stub
		return false;
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
	
}
