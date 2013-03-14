package jade.ui;

import jade.ui.TermPanel.Screen;
import jade.util.datatype.ColoredChar;
import jade.util.datatype.Coordinate;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Box {
	
	private BoxPanel boxPanel;
	private Map<Coordinate, ColoredChar> boxBuffer;
	
	
	public Box(TermPanel term) {
		boxBuffer = new HashMap<Coordinate, ColoredChar>();
		boxPanel = new BoxPanel(20,10,16,16);
		term.panel().add(boxPanel);
		//boxBuffer = term.getBuffer();
	}
	
	public static Box addBox(TermPanel term) {
		return new Box(term);
	}

	public BoxPanel getBoxPanel(){
		return boxPanel;
	}
	
	public void setContent(String content) {
		boxPanel.content = content;
	}

	/**
	 * Implements a {@code Box} on a {@code JPanel}, which can then be embedded into any container
	 * able to use a {@code JPanel}.
	 */
	public static class BoxPanel extends JPanel {
		
		private static final long serialVersionUID = 7617306256964716121L;
		private Map<Coordinate, ColoredChar> boxBuffer = new HashMap<Coordinate, ColoredChar>();
		private String content="";
		
		public BoxPanel(int columns, int rows, int tileWidth, int tileHeight)
		{
	            // Sets the preferred size of this component.
	            setPreferredSize(new Dimension(columns * tileWidth, rows * tileHeight));
	            setFont(new Font(Font.MONOSPACED, Font.PLAIN, tileHeight));
	            setBackground(Color.lightGray);
	            setFocusable(true);
	            add(new JLabel("<html><p>This is a long paragraph </p></html>"));
	            getComponentCount();
	     }
		
		public void setContent(String content) {
			this.content = content;
		}
       
	        //inherited from JPanel -> JComponent
	        @Override
	        protected void paintComponent(Graphics page)
	        {
	            super.paintComponent(page);
	            page.setColor(Color.black);
	            //page.drawString(content, 10, 10);
	            /*synchronized(boxBuffer)
	            {
	                for(Coordinate coord : boxBuffer.keySet())
	                {
	                    ColoredChar ch = boxBuffer.get(coord);
	                    int x = 16 * coord.x();
	                    int y = 16 * (coord.y() + 1);

	                    page.setColor(ch.color());
	                    page.drawString(ch.toString(), x, y);
	                }
	            }*/
	        }
	}
}
	
