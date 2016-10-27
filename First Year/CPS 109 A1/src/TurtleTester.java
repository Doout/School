import java.awt.Color;

import javax.swing.JFrame;

/****
 * @author Baheer
 */
public class TurtleTester {

		public static void main(String[] args) {

				World w = new World(600, 600);
				Turtle t = new Turtle(100, 100, w);
				t.setColor(getRandomColor());
				t.drawHouse();
				t.setColor(getRandomColor());
				t.setLoaction(200, 50);
				t.setColor(getRandomColor());
				t.drawArrow();
				t.setColor(getRandomColor());
				t.move(170, 0);
				t.drawRectangle(100, 20);
				t.setColor(getRandomColor());
				t.setLoaction(100, 300);
				t.drawFlower();
				t.setColor(getRandomColor());
				t.move(50, 0);
				t.drawHexagon(20, true);
				t.setColor(getRandomColor());
				t.move(50, 0);
				t.drawPentagon(20, true);
				t.setColor(getRandomColor());
				t.move(50, 0);
				t.drawEquilateralTriangle(50, true);
				t.setColor(getRandomColor());
				t.move(160, 0);
				t.drawPyramid();
				t.setColor(getRandomColor());
				t.move(100, 100);
				t.drawName();
				t.setColor(getRandomColor());
				t.setLoaction(300, 400);
				t.setColor(getRandomColor());
				t.drawStar(50);
				t.setLoaction(0, 0);

		}

		/** @return A random color **/
		public static Color getRandomColor() {
				return new Color((int) (Math.random() * 255), (int) (Math.random() * 255),
						(int) (Math.random() * 255));
		}

}
