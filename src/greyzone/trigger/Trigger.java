package greyzone.trigger;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;

public class Trigger extends Actor {
	
	public Trigger() {
		this (ColoredChar.create('T'));
	}

	public Trigger(ColoredChar face) {
		super(face);
		
	}

	@Override
	public void act() {

		
	}

	
}
