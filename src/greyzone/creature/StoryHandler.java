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
                    
                case 'h':
                	term.setMenu("hell",true);
                	expire();
                    break; 
                    
                case 'l':
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
		if(slidePos.y() <= 50)
			this.move(0, 40);
	}

	@Override
	public Collection<Coordinate> getViewField() {
		return fov.getViewField(world(), pos(), 19);
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
