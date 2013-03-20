package greyzone.level;

import java.awt.Color;

import greyzone.creature.Player;
import jade.core.World;
import jade.gen.Generator;
import jade.gen.map.MapLoaderChris;
import jade.gen.map.Maze;

public class Level extends World
{
    private final static Generator gen = getLevelGenerator();

    public Level(int width, int height)
    {
        super(width, height);
        gen.generate(this);
    }
    
    public Level(int width, int height, Player player)
    {
        super(width, height);
        gen.generate(this);
        addActor(player);
    }
    
    public Level(int width, int height, String path, Color color)
    {
        super(width, height);
        getLevelLoader(path, color).generate(this);

    }

    
    public Level(int width, int height, String path, Color color, Player player)
    {
        super(width, height);
        getLevelLoader(path, color).generate(this);
        addActor(player);
    }

    private static Generator getLevelGenerator()
    {
        return new Maze();

    }
    private static Generator getLevelLoader(String path, Color color)
    {
        return new MapLoaderChris(path, color);
    }
}
