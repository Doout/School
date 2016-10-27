import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Vehicle extends Node<Vehicle> {

		private int x, y, number;
		private Rectangle rect;

		// private Vehicle trailer is in Node class

		public Vehicle(int x, int y, int number) {
				super();
				this.x = x;
				this.y = y;
				this.number = number;
		}

		public abstract void draw(Graphics2D g);

		/**
		 * @param v
		 *            The vehicle in which to check if the this vehicle intersect it
		 * @return If the this vehicle intersect the other vehicle then return true
		 *         else false
		 */
		public boolean overLap(Vehicle v) {
				return rect.intersects(v.getRectangle());
		}

		/**
		 * @param p
		 *            The point in which to check if the vehicle contain it
		 * @return If the point contain in the area of the vehicle then return true
		 *         else false
		 */
		public boolean overLap(Point p) {
				return rect.contains(p);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + number;
				result = prime * result + ((rect == null) ? 0 : rect.hashCode());
				result = prime * result + x;
				result = prime * result + y;
				return result;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj == null) return false;
				if (getClass() != obj.getClass()) return false;
				Vehicle other = (Vehicle) obj;
				if (number != other.number) return false;
				if (rect == null) {
						if (other.rect != null) return false;
				} else if (!rect.equals(other.rect)) return false;
				if (x != other.x) return false;
				if (y != other.y) return false;
				return true;
		}

		public void setRectangle(Rectangle rect) {
				this.rect = rect;
		}

		public Rectangle getRectangle() {
				return rect;
		}

		public Vehicle getData() {
				return this;
		}

		/**
		 * @param x
		 *            the x to set
		 */
		public void setX(int x) {
				this.x = x;
		}

		/**
		 * @param y
		 *            the y to set
		 */
		public void setY(int y) {
				this.y = y;
		}

		/**
		 * @param number
		 *            the number to set
		 */
		public void setNumber(int number) {
				this.number = number;
		}

		/**
		 * @return the x
		 */
		public int getX() {
				return x;
		}

		/**
		 * @return the y
		 */
		public int getY() {
				return y;
		}

		/**
		 * @return the number
		 */
		public int getNumber() {
				return number;
		}

		/**
		 * @param node
		 *            The Frist node in which to check colour from
		 * @param c
		 *            The new Color
		 */



		public static Vehicle getOverLapFirst(ArrayList<Vehicle> data, Point p) {
				for (Node<Vehicle> node : data) {
						if (node != null && node.getData() != null && node.getData().overLap(p))
								return node.getData();
				}
				return null;
		}

		/**
		 * @param g
		 *            {@link Graphics2D}
		 * @param node
		 *            The node in which to first start drawing
		 */
		public static void drawNode(Graphics2D g, Node<Vehicle> node) {
				if (node == null) return;
				if (node.getData() != null) node.getData().draw(g);
				if (node.getNext() != null) drawNode(g, node.getNext());
		}

		/**
		 * @param data
		 *            The list of nodes
		 * @param v
		 *            The vehicle in which to check if overlap
		 * @return If overlap return true else false
		 */
		public static boolean isNodeOverLap(ArrayList<Vehicle> data, Vehicle v) {
				return getOverLapNode(data, v) != null;
		}

		/**
		 * @param data
		 *            The list of nodes
		 * 
		 * @param v
		 *            The vehicle in which to check if overlap
		 */
		public static Vehicle getOverLapNode(ArrayList<Vehicle> data, Vehicle v) {
				Node<Vehicle> temp = null;
				for (Vehicle node : data) {
						if (node == null) continue;
						temp = node;
						while (temp != null) {
								if (temp.getData().overLap(v)
										&& !temp.getData().getRectangle().equals(v.getRectangle())) return node;
								temp = temp.getNext();
						}
				}
				return null;
		}

		/**
		 * @param data
		 *            The list of nodes
		 * 
		 * @param v
		 *            The vehicle in which to check if overlap
		 */
		public static Vehicle getOverLapLastNode(ArrayList<Vehicle> data, Vehicle v) {
				Node<Vehicle> temp = null;
				for (Vehicle node : data) {
						if (node == null) continue;
						temp = node;
						while (temp != null) {
								if (temp.getNext() == null)
										if (temp.getData().overLap(v)
												&& !temp.getData().getRectangle().equals(v.getRectangle())) return node;
								temp = temp.getNext();
						}
				}
				return null;
		}

		/**
		 * @param node
		 *            Frist node to check if it can load a block
		 * @param block
		 *            The block in which to load
		 */
		public static boolean loadCar(Vehicle node, Block block) {
				if (node == null) return false;
				if (!(node.getData() instanceof RailCar)) return loadCar(node.getNext().getData(), block);
				if (((RailCar) node.getData()).getBlock() == null) {
						((RailCar) node.getData()).setBlock(block);
						return true;
				}
				if (node.getNext() == null) return false;
				return loadCar(node.getNext().getData(), block);
		}

		/**
		 * @param node
		 *            The frist node in the node set in which to check
		 * 
		 * @return The {@link Block} that was remove
		 */
		public static Block removeBlock(Vehicle node) {
				if (node == null) return null;
				if (!(node.getData() instanceof RailCar)) return removeBlock(node.getNext().getData());
				if (((RailCar) node.getData()).getBlock() != null) {
						Block temp = ((RailCar) node.getData()).getBlock();
						((RailCar) node.getData()).setBlock(null);
						return temp;
				}
				return removeBlock(node.getNext().getData());
		}

		/**
		 * @param node
		 *            The started node in which to update the location from
		 * @see Utils#setLocationVehicle(Node, int, int)
		 */
		public static void updateLocation(Vehicle node) {
				setLocationVehicle(node, node.getData().getX(), node.getData().getY());
		}

		/**
		 * * @param node The started node in which to update the location from
		 * 
		 * @param x
		 *            The started x
		 * @param y
		 *            The started y
		 **/
		public static void setLocationVehicle(Vehicle node, int x, int y) {
				if (node == null) return;
				if (node.getData() != null) node.getData().setLoction(x, y);
				/*
				 * if (node.getNext() != null)
				 * setLocationVehicle(node.getNext().getData(), x +
				 * node.getData().getRectangle().width, y);
				 */
		}

		/**
		 * * @param x The started x
		 * 
		 * @param y
		 *            The started y
		 */
		public void setLoction(int x, int y) {
				this.x = x;
				this.y = y;
				rect.setLocation(x, y);
				if (getNext() != null) getNext().getData().setLoction(x + getRectangle().width, y);
		}

}
