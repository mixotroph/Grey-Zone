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
        final TermPanel term = TermPanel.getFramedTerminal("Grey Zone");
        
        Player player = new Player(term);
        World world = new Level(48,32, player);
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));
        
        ScreenThread startScreen = new ScreenThread(term,"startscreen",4);
        while(term.getKey()!='s'){}
        startScreen.kill();
        
        LevelThread level1 = new LevelThread(term, player, world);
        while(term.getKey()!='q'){}
       	level1.kill();
       	
        ScreenThread endScreen = new ScreenThread(term,"endscreen",1);
        while(term.getKey()!='q'){}
        endScreen.kill();
    	
        System.exit(0);
    }
}
