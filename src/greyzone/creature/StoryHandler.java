package greyzone.creature;

import java.util.Collection;
import jade.fov.RayCaster;
import jade.fov.ViewField;
import jade.ui.Camera;
import jade.ui.TermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

public class StoryHandler extends Creature implements Camera
{
    private TermPanel term;
    private ViewField fov;

    public StoryHandler(TermPanel term)
    {
        super(ColoredChar.create(' '));
        this.term = term;
        fov = new RayCaster();
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
            	case 'q':
            		expire();
            		break;
            		
                	case 'c':
                	nextSlide();
                    break;     
                    
                case 'y':
                	term.setMenu("hell",true);
                	expire();
                    break; 
                    
                case 'n':
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

	private void nextSlide() {
		Coordinate slidePos = this.pos();
		if(slidePos.y() <= 200)
			this.move(0, 40);
	}

	@Override
	public Collection<Coordinate> getViewField() {
		return fov.getViewField(world(), pos(), 19);
	}

}
