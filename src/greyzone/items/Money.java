package greyzone.items;

import jade.core.Actor;
import jade.util.datatype.ColoredChar;

public class Money extends Actor{

	public Money() {
		this(ColoredChar.create('F'));
	}
	public Money(ColoredChar face) {
		super(face);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
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
