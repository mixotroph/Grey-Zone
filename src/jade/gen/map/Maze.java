package jade.gen.map;import jade.core.World;import jade.util.Dice;import jade.util.datatype.ColoredChar;import jade.util.datatype.Coordinate;import java.util.ArrayList;import java.util.HashSet;import java.util.List;import java.util.Set;import java.util.Stack;/** * Uses a randomized version of Prim's algorithm to generate perfect mazes. */public class Maze extends MapGenerator{    private ColoredChar floorTile;    private ColoredChar wallTile;    /**     * Creates a new instance of {@code Maze} with a default open tile of '.' and a default closed     * tile of '#'.     */    public Maze()    {        this(ColoredChar.create('.'), ColoredChar.create('#'));    }    /**     * Initializes Maze with default parameters.     * @param floorTile the face of the open tiles     * @param wallTile the face oof the closed tiles     */    public Maze(ColoredChar floorTile, ColoredChar wallTile)    {        this.floorTile = floorTile;        this.wallTile = wallTile;    }    @Override    protected void generateStep(World world, Dice dice)    {        Set<Coordinate> cells = init(world);        Stack<Coordinate> stack = new Stack<Coordinate>();        stack.push(world.getOpenTile(dice));        cells.remove(stack.peek());        while(!stack.isEmpty())        {            Coordinate curr = stack.peek();            Coordinate next = getNext(curr, cells, dice);// get random uncovered direction            if(next == null)// no where to go, just back trace                stack.pop();            else            {                // dig in random uncovered direction                Coordinate dig = curr.getTranslated(curr.directionTo(next));                world.setTile(floorTile, true, dig);                stack.push(next);// continue from where we dug            }        }    }        private Coordinate getNext(Coordinate curr, Set<Coordinate> cells, Dice dice)    {        List<Coordinate> possible = new ArrayList<Coordinate>(4);        tryAddNext(possible, curr.getTranslated(0, 2), cells);        tryAddNext(possible, curr.getTranslated(0, -2), cells);        tryAddNext(possible, curr.getTranslated(2, 0), cells);        tryAddNext(possible, curr.getTranslated(-2, 0), cells);        if(possible.isEmpty())            return null;        Coordinate next = dice.choose(possible);        cells.remove(next);        return next;    }    private void tryAddNext(List<Coordinate> possible, Coordinate pos, Set<Coordinate> cells)    {        if(cells.contains(pos))            possible.add(pos);    }    private Set<Coordinate> init(World world)    {        Set<Coordinate> cells = new HashSet<Coordinate>();        for(int x = 0; x < world.width(); x++)            for(int y = 0; y < world.height(); y++)            {                // every other square is an open to start                if(x % 2 == 1 && y % 2 == 1 && x < world.width() - 1 && y < world.height() - 1)                {                    cells.add(new Coordinate(x, y));                    world.setTile(floorTile, true, x, y);                }                else                    world.setTile(wallTile, false, x, y);            }        return cells;    }}