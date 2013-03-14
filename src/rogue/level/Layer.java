package rogue.level;

import java.util.LinkedList;
import java.util.Queue;

import rogue.creature.StoryHandler;
import jade.core.World;
import jade.gen.Generator;
import jade.gen.map.Story;



public class Layer extends World {
		private static LinkedList<String> paths = new LinkedList<String>();
	    private Generator gen = getLevelGenerator();

	    public Layer(int width, int height, StoryHandler handler)
	    {
	        super(width, height);
	        gen.generate(this);
	        addActor(handler);
	    }
	    
	    public LinkedList<String> getNext() 
	    {
	    	return (LinkedList<String>) paths;
	    }

	    private static Generator getLevelGenerator()
	    {
	        return new Story();
	    }
	}
