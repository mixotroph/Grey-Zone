package rogue.level;

import jade.core.World;
import jade.gen.Generator;
import jade.gen.map.Cellular;
import jade.gen.map.TestMaze;
import rogue.creature.Player;

public class TestLevel extends World
{
    private final static Generator gen = getLevelGenerator();

    public TestLevel(int width, int height, Player player)
    {
        super(width, height);
    	System.out.println("this is in testLevel\nin the Constructor");

        gen.generate(this);
        addActor(player);
    }

    private static Generator getLevelGenerator()
    {
    	System.out.println("this is in testLevel\nin the getLevelGenerator()");
        return new TestMaze();
    }
}
