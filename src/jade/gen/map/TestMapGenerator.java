package jade.gen.map;

import jade.gen.TestGenerator;

/**
 * Represents an algorithm for generating entire maps. It should be the first step in any generation
 * process and therefore cannot have any chained {@code} Generator attached to it.
 */
public abstract class TestMapGenerator extends TestGenerator
{
    /**
     * Creates a new instance of {@code MapGenerator}, with no chained {@code Generator}.
     */
    public TestMapGenerator()
    {
        super();
    }
}
