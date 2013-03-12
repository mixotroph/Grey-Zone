package jade.gen;

import jade.core.World;
import rogue.level.TestLevel;
import jade.util.Guard;

/**
 * Represents a map generation algorithm. These generators can be chained together in decorator
 * fashion to create a new composite {@code Generator}.
 */
public abstract class TestGenerator
{
    private TestGenerator chained;

    /**
     * Creates a new {@code Generator} with no previous chained {@code Generator}
     */
    public TestGenerator()
    {
        this(null);
    }  

    /**
     * Creates a new {@code Generator} with the given {@code Generator} as the previous chained
     * {@code Generator}.
     * @param chained the chained {@code Generator}
     */
    public TestGenerator(TestGenerator chained)
    {
        this.chained = chained;
    }

    /**
     * Performs the generation step of the {@code Generator}.
     * @param world the {@code World} on which the generation algorithm is being performed on
     */
    protected abstract void generateScreen(World world);

    /**
     * Calls the generation step of the {@code Generator}, after the chained {@code Generator} has
     * been called.
     * @param world the {@code World} on which the generation algorithm is being performed on
     */
    public final void generate(World world)
    {
        Guard.argumentsAreNotNull(world);

        if(chained != null)
            chained.generate(world);
        generateScreen(world);
    }
}
