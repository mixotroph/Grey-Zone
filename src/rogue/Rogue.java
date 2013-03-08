package rogue;

import jade.core.World;
import jade.ui.TermPanel;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

import java.awt.Color;

import rogue.creature.BadPerson_1;
import rogue.creature.Monster;
import rogue.creature.Player;
import rogue.level.Level;

public class Rogue
{
    public static void main(String[] args) throws InterruptedException
    {

 
        TermPanel term = TermPanel.getFramedTerminal("Grey Zone");
//        term.registerTile("startscreen.png", 5, 59, ColoredChar.create('#'));
  //      term.registerTile("startscreen.png", 3, 60, ColoredChar.create('.'));
    //    term.registerTile("startscreen.png", 5, 20, ColoredChar.create('@'));
      //  term.registerTile("startscreen.png", 14, 30, ColoredChar.create('D', Color.red));

        
        Player player = new Player(term);
        World world = new Level(48,32, player);
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));

        //term.registerCamera(player, 5, 5);
        
        // hallo
        char key = 0;
        char qKey = 0;
        while(key!='s')
        {
        	term.bufferFile("title_1.txt");
            term.refreshScreen();
        	key=term.getKey();
        }

        
        while(!player.expired())
        {
            term.clearBuffer();
            for(int x = 0; x < world.width(); x++)
                for(int y = 0; y < world.height(); y++)
                    term.bufferChar(x , y, world.look(x, y));
            //term.bufferCameras();
            term.bufferChar(new Coordinate(20,20), ColoredChar.create('X'));
            term.refreshScreen();
            world.tick();
        }
        	term.clearBuffer();
        	term.bufferString(new Coordinate(20,15), "THE END");
        	term.refreshScreen();
        	qKey=term.getKey();
    	
        System.exit(0);
    }
}
