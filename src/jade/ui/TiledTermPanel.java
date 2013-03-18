package jade.ui;

import jade.core.World;
import jade.util.Guard;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;

public class TiledTermPanel extends TermPanel
{
    public static final int DEFAULT_TILESIZE = 16;
    private static int X_OFFSET = 0;
    private static int Y_OFFSET = 0;

    private Map<Coordinate, List<ColoredChar>> tileBuffer;
    private Map<Coordinate, List<ColoredChar>> savedTile;
    
    

    public TiledTermPanel(int columns, int rows, int tile_size)
    {
        this(new TiledScreen(columns, rows, tile_size));
    }

    public TiledTermPanel()
    {
        this(new TiledScreen(DEFAULT_COLS, DEFAULT_ROWS, DEFAULT_TILESIZE));
    }

    private TiledTermPanel(TiledScreen screen)
    {
        super(screen);
        tileBuffer = new HashMap<Coordinate, List<ColoredChar>>();
        savedTile = new HashMap<Coordinate, List<ColoredChar>>();
    }

    public static TiledTermPanel getFramedTerminal(String title)
    {
        TiledTermPanel term = new TiledTermPanel();
        frameTermPanel(term, title);
        return term;
    }

    @Override
    protected TiledScreen screen()
    {
        return (TiledScreen) super.screen();
    }

    public boolean registerTile(String tileSet, int x, int y, ColoredChar ch)
    {
        try
        {
            int w = screen().tileWidth();
            int h = screen().tileHeight();
            BufferedImage tileset = ImageIO.read(new File(tileSet));
            BufferedImage tile = tileset.getSubimage(x  , y  , w, h);
            System.out.println("x:" + x*w+" y: "+y*h);
            screen().registerTile(ch, tile);
            return true;
        }
        catch(IOException e)
        {
        	System.err.println("Error: textures could not be loaded");
            return false;
        }
    }
 
    public void loadTextureSet(String path, String path2)
    {
    	char[] symbol={'§','^','$','[','\\',']','%','°','&'};
        registerTile("textures/dungeon.png", 1, 1, ColoredChar.create('#',Color.ORANGE));
        registerTile("textures/dungeon.png", 1, 34, ColoredChar.create('¤'));
        registerTile("textures/player.png", 0, 0, ColoredChar.create('@'));
        //registerTile("textures/player.png", 0, 0, ColoredChar.create('D', Color.red));
        registerTile("textures/dungeon.png", 17, 34, ColoredChar.create('+'));
    	
        registerTile(path2, 81, 0, ColoredChar.create('§'));
        registerTile(path2, 96, 0, ColoredChar.create('^'));
        registerTile(path2, 112, 0, ColoredChar.create('$'));
        registerTile(path2, 81, 16, ColoredChar.create('['));
        registerTile(path2, 96, 16, ColoredChar.create('\''));
        registerTile(path2, 112, 16, ColoredChar.create(']'));
        registerTile(path2, 81, 32, ColoredChar.create('%'));
        registerTile(path2, 96, 32, ColoredChar.create('°'));
        registerTile(path2, 112, 32, ColoredChar.create('&'));
        
        registerTile(path2, 81, 0, ColoredChar.create('§',Color.LIGHT_GRAY));
        registerTile(path2, 96, 0, ColoredChar.create('^',Color.LIGHT_GRAY));
        registerTile(path2, 112, 0, ColoredChar.create('$',Color.LIGHT_GRAY));
        registerTile(path2, 81, 16, ColoredChar.create('[',Color.LIGHT_GRAY));
        registerTile(path2, 96, 16, ColoredChar.create('\'',Color.LIGHT_GRAY));
        registerTile(path2, 112, 16, ColoredChar.create(']',Color.LIGHT_GRAY));
        registerTile(path2, 81, 32, ColoredChar.create('%',Color.LIGHT_GRAY));
        registerTile(path2, 96, 32, ColoredChar.create('°',Color.LIGHT_GRAY));
        registerTile(path2, 112, 32, ColoredChar.create('&',Color.LIGHT_GRAY));
        registerTile(path, 0, 49, ColoredChar.create('Ö'));
        registerTile(path, 66, 0, ColoredChar.create(')'));
        registerTile(path, 50, 1, ColoredChar.create('('));
        registerTile(path, 66, 16, ColoredChar.create('}'));
        registerTile(path, 49, 16, ColoredChar.create('{'));
        registerTile(path, 66, 0, ColoredChar.create(')',Color.BLUE));
        registerTile(path, 50, 1, ColoredChar.create('(',Color.BLUE));
        registerTile(path, 66, 16, ColoredChar.create('}',Color.BLUE));
        registerTile(path, 49, 16, ColoredChar.create('{',Color.BLUE));
       
        registerTile("textures/dungeon.png", 1, 1, ColoredChar.create('#'));
        registerTile("textures/dungeon.png", 1, 34, ColoredChar.create('.'));
        //registerTile("textures/player.png", 1, 17, ColoredChar.create('@'));
        registerTile("textures/enemy.png", 17, 0, ColoredChar.create('D', Color.red));
        registerTile("textures/dungeon.png", 17, 34, ColoredChar.create('+'));
        
        registerTile(path, 0, 0, ColoredChar.create('§',Color.ORANGE));
        registerTile(path, 17, 0, ColoredChar.create('^',Color.ORANGE));
        registerTile(path, 33, 0, ColoredChar.create('$',Color.ORANGE));
        registerTile(path, 0, 16, ColoredChar.create('[',Color.ORANGE));
        registerTile(path, 16, 16, ColoredChar.create('\'',Color.ORANGE));
        registerTile(path, 33, 16, ColoredChar.create(']',Color.ORANGE));
        registerTile(path, 0, 33, ColoredChar.create('%',Color.ORANGE));
        registerTile(path, 16, 33, ColoredChar.create('°',Color.ORANGE));
        registerTile(path, 33, 33, ColoredChar.create('&',Color.ORANGE));
        registerTile(path, 0, 49, ColoredChar.create('Ö',Color.ORANGE));
        registerTile(path, 66, 0, ColoredChar.create(')',Color.ORANGE));
        registerTile(path, 50, 1, ColoredChar.create('(', Color.ORANGE));
        registerTile(path, 66, 16, ColoredChar.create('}',Color.ORANGE));
        registerTile(path, 49, 16, ColoredChar.create('{',Color.ORANGE));
    }


    @Override
    public void clearBuffer()
    {
        super.clearBuffer();
        tileBuffer.clear();
    }

    @Override
    public void saveBuffer()
    {
        super.saveBuffer();
        savedTile.clear();
        savedTile.putAll(tileBuffer);
    }

    @Override
    public void recallBuffer()
    {
        super.recallBuffer();
        tileBuffer.clear();
        tileBuffer.putAll(savedTile);
    }

    @Override
    public void refreshScreen()
    {
        screen().setTileBuffer(tileBuffer);
        super.refreshScreen();
    }
    
    @Override
    public void bufferCamera(Camera camera)
    {
        Guard.argumentIsNotNull(camera);
        Guard.verifyState(cameraRegistered(camera));

        Coordinate screenCenter = cameraCenter(camera);
        int offX = screenCenter.x() - camera.x();
        int offY = screenCenter.y() - camera.y();
        World world = camera.world();
        for(Coordinate coord : camera.getViewField())
            tileBuffer.put(coord.getTranslated(offX, offY),
                    world.lookAll(coord));
    }
    
    /**
     * @author Christoph van Heteren-Frese
     */
    public void bufferFov(Camera camera)
    {
        Guard.argumentIsNotNull(camera);
        Guard.verifyState(cameraRegistered(camera));
        World world = camera.world();
        
        for(Coordinate coord : camera.getViewField()) {
        	Coordinate newCoord = new Coordinate(coord.x() + X_OFFSET,coord.y() + Y_OFFSET+1);
            tileBuffer.put(newCoord,world.lookAll(coord));
        }
    }
    
    /**
     * @author Christoph van Heteren-Frese
     */
    public void bufferStatusBar() {
    	X_OFFSET=8;
    	bufferString(1, 39,"H: Help");
    }
    
    /**
     * @author Christoph van Heteren-Frese
     */
    //@Override
    public void bufferBoxes(World world, String pathToframe, String pathTotext) 
    {
    	
    	//Player player = world.getActor(Player.class);
    	
    	// first, buffer frame and background
    	this.recallBuffer();
    	this.bufferFile(pathToframe);	
    	
    	//this.bufferWorld(world);

    	Map<Coordinate,ColoredChar> buffer;
    	buffer = this.getBuffer();
    	for (Coordinate coord : buffer.keySet())
    	{
    		if (tileBuffer.get(coord) == null) {
    			List<ColoredChar> tileList = tileBuffer.get(coord);
    		}
    		List<ColoredChar> tileList = new ArrayList<ColoredChar>();
    		tileList.add(0, buffer.get(coord));
    		tileBuffer.put(coord,tileList);
    	}
    	
    	// second, buffer text
    	this.bufferFile(pathTotext);
    	buffer = this.getBuffer();
    	for (Coordinate coord : buffer.keySet())
    	{
    		List<ColoredChar> tileList;
    		tileList = tileBuffer.get(coord);
    		char ch = buffer.get(coord).ch();
    		tileList.add(0,ColoredChar.create(ch));
    		tileBuffer.put(coord,tileList);
    	}  	
    	   	buffer.clear();
    }

    public void bufferWorld(World world)
    {
    	for(int x = 0; x < world.width(); x++)
    		for(int y = 0; y < world.height(); y++)
    			tileBuffer.put(new Coordinate((x+X_OFFSET ), (1+y+Y_OFFSET)), world.lookAll(x, y));
    } 
    
    @Override
    public void bufferRelative(Camera camera, ColoredChar ch, int x, int y)
    {
        Guard.argumentIsNotNull(camera);
        Guard.verifyState(cameraRegistered(camera));

        Coordinate screenCenter = cameraCenter(camera);
        int offX = screenCenter.x() - camera.x();
        int offY = screenCenter.y() - camera.y();
        Coordinate pos = new Coordinate(x + offX, y + offY);
        List<ColoredChar> look = tileBuffer.containsKey(pos) ? tileBuffer
                .get(pos) : new ArrayList<ColoredChar>();
        look.add(0, ch);
        tileBuffer.put(new Coordinate(x + offX, y + offY), look);
    }

    /*
     * begin inner class TiledScreen
     */
    private static class TiledScreen extends Screen
    {
        private static final long serialVersionUID = 6739172935885377439L;

        private Map<Coordinate, List<ColoredChar>> tileBuffer;
        private Map<ColoredChar, Image> tileRegister;

        public TiledScreen(int columns, int rows, int tileSize)
        {
            this(columns, rows, tileSize, tileSize);
        }

        public TiledScreen(int columns, int rows, int tileWidth, int tileHeight)
        {
            super(columns, rows, tileWidth, tileHeight);
            tileBuffer = new HashMap<Coordinate, List<ColoredChar>>();
            tileRegister = new HashMap<ColoredChar, Image>();
        }

        public void setTileBuffer(Map<Coordinate, List<ColoredChar>> buffer)
        {
            synchronized(tileBuffer)
            {
                tileBuffer.clear();
                tileBuffer.putAll(buffer);
            }
        }

        public void registerTile(ColoredChar ch, Image tile)
        {
            synchronized(tileRegister)
            {
                tileRegister.put(ch, tile);
            }
        }

        @Override
        protected void paintComponent(Graphics page)
        {
            super.paintComponent(page);
            for(Coordinate coord : tileBuffer.keySet())
            {
                int x = tileWidth() * coord.x();
                int y = tileHeight() * coord.y();
                
                List<ColoredChar> tiles = tileBuffer.get(coord);
                Collections.reverse(tiles);
                for(ColoredChar ch : tiles)
                {
                    if(tileRegister.containsKey(ch))
                    {
                        page.drawImage(tileRegister.get(ch), x, y - tileHeight(),
                                tileWidth(), tileHeight(), null);
                    }
                    else
                    {
                        page.setColor(ch.color());
                        page.drawString(ch.toString(), x, y);
                    }
                }
            }
        }
    }
}
    
    
