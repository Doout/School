import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block {

		private int x, y, width, height;
		private char ch;

		/**
		 * @param x
		 *            The X location of the block
		 * @param y
		 *            The Y location of the block
		 * @param width
		 *            The width of the block
		 * @param height
		 *            The height of the block
		 * @param ch
		 *            THe char for the block
		 */
		public Block(int x, int y, int width, int height, char ch) {
				this.x = x;
				this.y = y;
				this.width = width;
				this.height = height;
				this.ch = ch;
		}

		/**
		 * @param x
		 *            The X location of the block
		 * @param y
		 *            The Y location of the block
		 */
		public void setLoction(int x, int y) {
				this.x = x;
				this.y = y;
		}

		/**
		 * @return the x
		 */
		public int getX() {
				return x;
		}

		/**
		 * @param x
		 *            the x to set
		 */
		public void setX(int x) {
				this.x = x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
				return y;
		}

		/**
		 * @param y
		 *            the y to set
		 */
		public void setY(int y) {
				this.y = y;
		}

		/**
		 * @return the width
		 */
		public int getWidth() {
				return width;
		}

		/**
		 * @param width
		 *            the width to set
		 */
		public void setWidth(int width) {
				this.width = width;
		}

		/**
		 * @return the height
		 */
		public int getHeight() {
				return height;
		}

		/**
		 * @param height
		 *            the height to set
		 */
		public void setHeight(int height) {
				this.height = height;
		}

		/** @return The Area of the block */
		public Rectangle getArea() {
				return new Rectangle(x, y, width, height);
		}

		/**
		 * @param g
		 *            The {@link Graphics2D}*
		 * @param block
		 *            The block in which to add
		 */
		public static void draw(Graphics2D g, Block block) {
				g.draw(block.getArea());
				g.drawString(block.ch + "", (block.x + (block.width / 2)) - 6,
						(block.y + (block.height / 2)) + 6);
		}

}
