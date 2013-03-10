package rogue;

import jade.core.World;
import jade.level.LevelThread;
import jade.screen.ScreenThread;
import jade.ui.TermPanel;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

import java.awt.Color;
import rogue.creature.Monster;
import rogue.creature.Player;
import rogue.level.Level;

public class Rogue
{
    public static void main(String[] args) throws InterruptedException
    {
        TiledTermPanel term = TiledTermPanel.getFramedTerminal("Grey Zone");
        term.registerTile("dungeon.png", 1, 1, ColoredChar.create('#'));
        term.registerTile("dungeon.png", 1, 34, ColoredChar.create('.'));
        term.registerTile("dungeon.png", 1, 17, ColoredChar.create('@'));
        term.registerTile("dungeon.png", 17, 17, ColoredChar.create('D', Color.red));
        
        Player player = new Player(term);
        World world = new Level(60, 30, player);
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));
        term.registerCamera(player, 30,15);
        
        ScreenThread startScreen = new ScreenThread(term,"startscreen",4);
        while(term.getKey()!='s'){}
        startScreen.kill();
          	
        while(!player.expired())
        {
            term.clearBuffer();
            /*for(int x = 0; x < world.width(); x++)
                for(int y = 0; y < world.height(); y++)
                    term.bufferChar(x + 11, y, world.look(x, y));*/
            term.bufferWorld(world);
            //term.bufferCameras();
            term.refreshScreen();

            world.tick();
        }
        term.refreshScreen();
        ScreenThread endScreen = new ScreenThread(term,"endscreen",1);
        while(term.getKey()!='q'){}
        endScreen.kill();
        
        System.exit(0);
    }
}
