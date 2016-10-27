import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * This class describes a vehicle that looks like a flatbed railcar. The railcar
 * should be assigned a unique number displayed on its body. The railcar should
 * have variable and methods to allow it to be linked to another vehicle
 * (consider whether this variable and associated methods should be inherited).
 * This railcar should also have variables and methods so that a storage
 * container can be loaded and unloaded Add other variables and methods you
 * think are necessary.
 */

public class RailCar extends Vehicle {

		private Block block;
		private Color color = Color.BLACK;

		public static final int UNIT = 10;
		public static final int U6 = 6 * UNIT;
		public static final int U5 = 5 * UNIT;
		public static final int U4 = 4 * UNIT;
		public static final int U3 = 3 * UNIT;
		public static final int U2 = 2 * UNIT;
		public static final int U15 = UNIT + UNIT / 2;
		public static final int U05 = UNIT / 2;
		public static final int BODY_WIDTH = U3;
		public static final int BODY_HEIGHT = U2;

		/** Testing adding index */
		public static void main(String[] args) {
				RailCar v = new RailCar(0, 0, 0);
				for (int i = 0; i < 5; i++) {
						v.add(new RailCar(0, 0, i + 1));
				}
				v.add(new RailCar(0, 0, 7), 1);
				RailCar temp = v;
				while (temp != null) {
						System.out.println(temp.getNumber());
						if (temp.getNext() != null) temp = (RailCar) temp.getNext().getData();
						else break;
				}
		}

		public RailCar(int x, int y, int number) {
				super(x, y, number);
				setRectangle(new Rectangle(x, y, 67, 30));
		}

		public void setBlock(Block block) {
				this.block = block;
				if (block != null)
						block.setLoction(getX() + BODY_WIDTH - (block.getWidth() / 2), getY() - UNIT);
		}

		public Block getBlock() {
				return block;
		}

		/**
		 * @param v
		 *            The vehicle in which to check if it overlap
		 * @return If the object is overlaping this object then return true else
		 *         false
		 */
		@Override
		public boolean overLap(Vehicle v) {
				return super.overLap(v);
		}

		@Override
		public void setLoction(int x, int y) {
				super.setLoction(x, y);
				if (block != null) block.setLoction(x + BODY_WIDTH - (block.getWidth() / 2), y - UNIT);
		}

		/** @return The color of the railcar */
		public Color getColor() {
				return color;
		}

		/**
		 * @param color
		 *            The color of the railcar
		 */
		public void setColor(Color color) {
				this.color = color;
		}

		/**
		 * Draw the rail car
		 * 
		 * @param g2
		 *            the graphics context
		 */
		public void draw(Graphics2D g2) {
				// think about whether getX() and getY() should be inherited
				// or defined in this class
				int xLeft = getX();
				int yTop = getY();
				g2.setColor(getColor());
				Rectangle2D.Double body = new Rectangle2D.Double(getX(), yTop + UNIT, U6, UNIT);
				Ellipse2D.Double frontTire = new Ellipse2D.Double(getX() + UNIT, yTop + U2, UNIT, UNIT);
				Ellipse2D.Double rearTire = new Ellipse2D.Double(getX() + U4, yTop + U2, UNIT, UNIT);

				// the bottom of the front windshield
				Point2D.Double r1 = new Point2D.Double(getX() + UNIT, yTop + UNIT);
				// the front of the roof
				Point2D.Double r2 = new Point2D.Double(getX() + U2, yTop);
				// the rear of the roof
				Point2D.Double r3 = new Point2D.Double(getX() + U4, yTop);
				// the bottom of the rear windshield
				Point2D.Double r4 = new Point2D.Double(getX() + U5, yTop + UNIT);

				// the right end of the hitch
				Point2D.Double r5 = new Point2D.Double(getX() + U6, yTop + U15);
				// the left end of the hitch
				Point2D.Double r6 = new Point2D.Double(getX() + U6 + U05, yTop + U15);

				Line2D.Double frontWindshield = new Line2D.Double(r1, r2);
				Line2D.Double roofTop = new Line2D.Double(r2, r3);
				Line2D.Double rearWindshield = new Line2D.Double(r3, r4);
				Line2D.Double hitch = new Line2D.Double(r5, r6);

				g2.draw(body);
				g2.draw(hitch);
				g2.draw(frontTire);
				g2.draw(rearTire);
				g2.draw(body);

				// think about whether getNumber() should be inherited or
				// defined in this class
				g2.drawString("" + getNumber(), getX() + U2, yTop + U2);
				g2.setColor(Color.GREEN);
				if (getBlock() != null) Block.draw(g2, getBlock());
		}
}
