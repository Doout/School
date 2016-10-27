import java.awt.Color;
import java.awt.Graphics;

/**
 * Class that represents a turtle which is similar to a Logo turtle. This class
 * inherts from SimpleTurtle and is for students to add methods to.
 *
 * Copyright Georgia Institute of Technology 2004
 * 
 * @author Barb Ericson ericson@cc.gatech.edu <br>
 *         Edited by Baheer Kamal
 */
public class Turtle extends SimpleTurtle {
		// //////////////// constructors ///////////////////////

		/**
		 * Constructor that takes the x and y and a picture to draw on
		 * 
		 * @param x
		 *            the starting x position
		 * @param y
		 *            the starting y position
		 * @param picture
		 *            the picture to draw on
		 */
		public Turtle(int x, int y, Picture picture) {
				// let the parent constructor handle it
				super(x, y, picture);
		}

		/**
		 * Constructor that takes the x and y and a model display to draw it on
		 * 
		 * @param x
		 *            the starting x position
		 * @param y
		 *            the starting y position
		 * @param modelDisplayer
		 *            the thing that displays the model
		 */
		public Turtle(int x, int y, ModelDisplay modelDisplayer) {
				// let the parent constructor handle it
				super(x, y, modelDisplayer);
		}

		/**
		 * Constructor that takes the model display
		 * 
		 * @param modelDisplay
		 *            the thing that displays the model
		 */
		public Turtle(ModelDisplay modelDisplay) {
				// let the parent constructor handle it
				super(modelDisplay);

		}

		/**
		 * Constructor that takes a picture to draw on
		 * 
		 * @param p
		 *            the picture to draw on
		 */
		public Turtle(Picture p) {
				// let the parent constructor handle it
				super(p);
		}

		// ///////////////// methods ///////////////////////
		/**
		 * @param width
		 *            The width of the square to draw
		 ***/

		public void drawSquare(int width) {
				for (int i = 0; i < 4; i++) {
						forward(width);
						turnLeft();
				}
		}

		/** Draw a arrow with a size of 20 */
		public void drawArrow() {
				int size = 20;
				drawRectangle(size * 2, size);
				move(0, -size / 2);
				drawEquilateralTriangle(size * 2, false);

		}

		/**
		 * Draw a rectangle
		 * 
		 * @param width
		 *            The width for the rectangle
		 * @param height
		 *            The Height of the rectangle
		 */
		public void drawRectangle(int width, int height) {
				turnToFaceSouth();
				for (int i = 0; i < 4; i++) {
						forward(i % 2 == 0 ? height : width);
						turnRight();
				}
		}

		/** Draw a house with size 50 */
		public void drawHouse() {
				int size = 50;
				drawSquare(size);
				move(-size, -size);
				drawEquilateralTriangle(size);
				int height = (int) (size / 3 * 1.5);
				int width = (((size / 2) + (size / 8)) - ((size / 2) - (size / 8)));
				move((size / 2) + (size / 8), size - height);
				turnToFaceEast();
				drawRectangle(width, height);
				move(-((size / 25) + width), -(size / 10));
				turnToFaceNorth();
				drawSquare(size - (height + (size / 5)));
				move(((size / 25) + width), -(size - (height + (size / 5))));
				drawSquare(size - (height + (size / 5)));
		}

		/**
		 * @param x
		 *            The x location in which to set the pen to.
		 * @param y
		 *            The y location in which to set the pen to.
		 **
		 */
		public void setLoaction(int x, int y) {
				penUp();
				moveTo(x, y);
				penDown();
		}

		/**
		 * @param x
		 *            The x location in which to move the the pen to.
		 * @param y
		 *            The y location in which to move the pen to.
		 **
		 */
		public void move(int x, int y) {
				penUp();
				turnToFaceEast();
				forward(x);
				turnToFaceSouth();
				forward(y);
				penDown();
		}

		/*** Draw a flower */
		public void drawFlower() {
				drawPloy(4, 20, true);
				turnToFaceSouth();
				move(0, 14);
				forward(20);
				move(0, -24);
				turn(-30);
				for (int i = 0; i < 6; i++) {
						drawPloy(4, 20, i == 5 ? 9 : 11, false);
						if (i == 5) break;
						turn(180);
						penUp();
						forward(2);
						penDown();
						turn(-45);
				}
		}

		/**
		 * Draw a hexagon
		 * 
		 * @param sideLength
		 *            the length for the hexagon side
		 **/
		public void drawHexagon(int sideLength) {
				drawPloy(sideLength, 6, true);
		}

		/**
		 * Draw an hexagon
		 * 
		 * @param sideLength
		 *            the length for the hexagon side
		 * @param turn
		 *            Set the heading to face east before drawing
		 **/

		public void drawHexagon(int sideLength, boolean turn) {
				drawPloy(sideLength, 6, turn);
		}

		/**
		 * Draw an pentagon
		 * 
		 * @param sideLength
		 *            the length for the pentagon side
		 **/

		public void drawPentagon(int sideLength) {
				drawPloy(sideLength, 5, true);
		}

		/**
		 * Draw an pentagon
		 * 
		 * @param sideLength
		 *            the length for the pentagon side
		 * @param turn
		 *            Set the heading to face east before drawing
		 **/
		public void drawPentagon(int sideLength, boolean turn) {
				drawPloy(sideLength, 5, turn);
		}

		/**
		 * Draw an equilateral triangle
		 * 
		 * @param sideLength
		 *            the lenght for the triangle
		 **/
		public void drawEquilateralTriangle(int sideLength) {
				drawEquilateralTriangle(sideLength, true);
		}

		/** Draw a phyramid with the size of 5 rectangle **/
		public void drawPyramid() {
				int w = 100, h = 10, temp, level = 8;
				for (int i = 0; i < level; i++) {
						temp = (int) (w * (1.00 - (i * (1d / level))));
						drawRectangle(temp, h);
						move(-(w /(2 * level)), -h);
				}
		}

		/** Draw the word Omer using line only. */
		public void drawName() {
				int temp;
				drawPloy(5, 20, true);// draw O
				move(20, 0);
				turnToFaceNorth();// draw M

				forward(15);
				backward(7);
				turnToFaceEast();
				move(10, 0);
				temp = getXPos() << 16 | getYPos();
				turnToFaceNorth();
				drawPloy(2, 30, 12, false);
				setLoaction(temp >> 16, temp & Short.MAX_VALUE);
				turnToFaceSouth();
				forward(7);
				move(8, -7);
				turnToFaceNorth();
				temp = getXPos() << 16 | getYPos();
				drawPloy(2, 30, 10, false);
				setLoaction(temp >> 16, temp & Short.MAX_VALUE);
				turnToFaceSouth();
				forward(7);

				// Draw E

				move(20, -7);
				turnToFaceNorth();
				temp = getXPos();
				drawPloy(3, 20, 10, false);
				temp = (Math.abs(getXPos() - temp));
				turnToFaceEast();
				forward(temp);
				backward(temp);
				turnToFaceSouth();
				drawPloy(3, 30, 8, false);

				// draw R
				move(10, 0);
				turnToFaceNorth();

				forward(18);
				backward(10);
				turnToFaceEast();
				move(10, -5);
				turnToFaceNorth();
				turn(-20);
				drawPloy(2, 30, 12, false);

		}

		/**
		 * @param size
		 *            The size of the star to draw
		 **/
		public void drawStar(int size) {
				drawStar(size, true);
		}

		/**
		 * @param size
		 *            The size of the star to draw
		 * @param turn
		 *            Set the heading to 18 before drawing the star, If true the
		 *            star will be draw up right else the star will be draw off set
		 **/
		public void drawStar(int size, boolean turn) {
				if (turn) setHeading(18);
				size = (int) (size / (2.5)); // FIX THE SIZE TO MATCH WITH WHAT BELOW
																		 // and
				for (int i = 0; i < 5; i++) {
						forward(size);
						penUp();
						forward(size / 2);
						penDown();
						forward(size);
						turn(144);
				}
		}

		/**
		 * Override the method to paint out a string after the super class is done.
		 * 
		 */
		public void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.setColor(Color.BLACK);

				g.drawString("By Baheer Kamal", 10, getModelDisplay().getHeight() - 11);

		}

		/**
		 * Draw an equilateral triangle
		 * 
		 * @param sideLength
		 *            the lenght for the triangle
		 * @param turntoFaceUp
		 *            If true set the heading to face up. <br>
		 *            If false draw without changing the heading
		 **/
		public void drawEquilateralTriangle(int sideLength, boolean turntoFaceUp) {
				drawPloy(sideLength, 3, turntoFaceUp);
		}

		/**
		 * @param sideLength
		 *            The lenght in which to draw the poly side with.
		 * @param numberofside
		 *            The number of side on the ploy
		 * @param turntoFaceUp
		 *            if true the turtle will face east before starting to draw.
		 **/
		public void drawPloy(int sideLength, int numberofside, boolean turntoFaceUp) {
				drawPloy(sideLength, numberofside, numberofside, turntoFaceUp);
		}

		/**
		 * @param sideLength
		 *            The length in which to draw the poly side with.
		 * @param numberofside
		 *            The number of side on the ploy
		 * @param numberofSideToDraw
		 *            The amount of side to draw.
		 * @param turntoFaceUp
		 *            if true the turtle will face east before starting to draw.
		 **/
		public void drawPloy(int sideLength, int numberofside, int numberofSideToDraw, boolean turntoFaceUp) {
				if (turntoFaceUp) turnToFaceEast();
				if (numberofside < 3) return;
				int angle = (180 * (numberofside - 2)) / numberofside;
				for (int i = 0; i < numberofSideToDraw; i++) {
						forward(sideLength);
						turn(180 + angle);

				}
		}

		/** Set the heading to face west */
		public void turnToFaceWest() {
				setHeading(270);
		}

		/** Set the heading to face east */
		public void turnToFaceEast() {
				setHeading(90);
		}

		/** Set the heading to face north */
		public void turnToFaceNorth() {
				setHeading(0);
		}

		/** Set the heading to face south */
		public void turnToFaceSouth() {
				setHeading(180);
		}

}