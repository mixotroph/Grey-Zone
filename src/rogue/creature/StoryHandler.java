package rogue.creature;

import jade.ui.TermPanel;
import jade.util.datatype.ColoredChar;

public class StoryHandler extends Creature 
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
}
