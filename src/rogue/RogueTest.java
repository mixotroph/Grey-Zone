package rogue;

import jade.core.World;
import jade.ui.TiledTermPanel;
import jade.util.datatype.ColoredChar;
import java.awt.Color;
import rogue.creature.Monster;
import rogue.creature.Player;
import rogue.level.Level;

public class RogueTest
{
    public static void main(String[] args) throws InterruptedException
    {
        TiledTermPanel term = TiledTermPanel.getFramedTerminal("Jade Rogue");
        
        Player player = new Player(term);
        World world = new Level(69, 24, player);
        world.addActor(new Monster(ColoredChar.create('D', Color.red)));
        term.registerCamera(player, 5, 5);

        
        while(!player.expired())
        {
        	term.clearBuffer();
            term.fileToTerm("/home/dariush/test.txt");
            term.refreshScreen();

        }

        System.exit(0);
    }
}