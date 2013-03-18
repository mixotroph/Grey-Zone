package greyzone.items;

import jade.util.datatype.ColoredChar;

public class Notebook extends Item
{
		private String pathToText = "frame_item_exp.txt";
		private String pathToFrame = "h1_item_exp.txt";
		
		public Notebook(ColoredChar face, String name) 
		{
			super(face, name);
		}


		public String getPathToText() {
			return pathToText;
		}

		public void setPathToText(String pathToText) {
			this.pathToText = pathToText;
		}

		public String getPathToFrame() {
			return pathToFrame;
		}

		public void setPathToFrame(String pathToFrame) {
			this.pathToFrame = pathToFrame;
		}

		@Override
		public void act() {
			// TODO Auto-generated method stub
			
		}

}
