package rogue.creature;

import java.util.Collection;

import jade.ui.Camera;
import jade.ui.TermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

public class StoryHandler extends Creature implements Camera
{
    private TermPanel term;

    public StoryHandler(TermPanel term)
    {
        super(ColoredChar.create(' '));
        this.term = term;
		//term.bufferFile("screens/startscreen/title.txt");
    }

    @Override
    public void act()
    {
        try
        {
            char key;
            key = term.getKey();
            switch(key)
            {
                case 's':
                    expire();
                    break;
                case 'q':
                    expire();
                    break;     
        
                default:
                    break;
            }
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

	@Override
	public Collection<Coordinate> getViewField() {
		// TODO Auto-generated method stub
		return null;
	}
}
