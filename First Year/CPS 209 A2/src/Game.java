import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Game extends Display { 

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public static final int BLOCK_WIDTH = 20;
		public static final int BLOCK_HEIGHT = 20;

		private int clickIndex; // The index in which the click is on
		private ArrayList<Vehicle> data = new ArrayList<Vehicle>();
		private Vehicle select;
		private Stack<Block> blocks = new Stack<Block>();
		private int blockX, blockY; // The start location of the block
		private static Object BLOCK = new Object();
		int selectX, selectY; // Offset for the drag

		public Game(int width, int height) {
				super(width, height);
		}

		/** This method only run once when the class is made. */
		@Override
		public void init() {
				addMouseListener(getMouseListener());
				addMouseMotionListener(getMouseMotionListener());
		}

		/** The method in which is call to render everything */
		@Override
		public void render(Graphics2D g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.BLACK);
				// g.drawString(x + " : " + y, 10, 20);
				synchronized (BLOCK) {
						for (Node<Vehicle> n : data) {
								Vehicle.drawNode(g, n);
						}
						g.setColor(Color.green);
						for (Block b : blocks) {
								Block.draw(g, b);
						}
				}
				if (clickIndex > 6) {
						g.setColor(Color.BLACK);
						g.fillRect(blockX - (BLOCK_WIDTH / 2), blockY + (BLOCK_HEIGHT), BLOCK_WIDTH * 2,
								BLOCK_HEIGHT / 2);
				}
		}

		@Override
		public void update() {
				// Dont need to update anything. just leave blank for superclass
		}

		/** Restart the app, reset the list and clickindex to 0 */
		public void restart() {
				this.blocks = new Stack<Block>();
				this.data = new ArrayList<Vehicle>();
				this.clickIndex = 0;
		}

		public static void main(String[] args) {
				Game game = new Game(400, 600);
				JMenuBar bar = new JMenuBar();
				JMenu menu = new JMenu("File");
				JMenuItem ne = new JMenuItem("New");
				ne.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										game.restart();
								}
						}
				});
				JMenuItem exit = new JMenuItem("Exit");
				exit.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
								System.exit(0);
						}
				});
				menu.add(exit);
				menu.add(ne);
				bar.add(menu);
				JMenu menu2 = new JMenu("Stack");
				JMenuItem pop = new JMenuItem("pop");
				pop.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										if (game.select != null) {
												Block temp = game.blocks.pop();
												if (!Vehicle.loadCar(game.select.getData(), temp)) game.blocks.add(temp);
										}
								}
						}
				});
				JMenuItem push = new JMenuItem("push");
				push.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										if (game.select != null) {
												Block temp = Vehicle.removeBlock(game.select.getData());
												if (temp == null) return;
												temp.setLoction(game.blockX,
														game.blockY - (BLOCK_HEIGHT * game.blocks.size()));
												game.blocks.add(temp);
										}
								}
						}
				});
				menu2.add(pop);
				menu2.add(push);
				bar.add(menu2);
				JMenu menu3 = new JMenu("List");
				JMenuItem addFrist = new JMenuItem("Add First");
				addFrist.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										if (game.select != null && !game.select.equals(game.data.get(0))) {
												game.data.remove(game.select);
												game.data.get(0).add(game.select, 1);
												Vehicle.updateLocation(game.data.get(0));
												Vehicle.changeRailCarColor(game.select, Color.black);
												game.select = null;
										}
								}
						}
				});
				JMenuItem addLast = new JMenuItem("Add Last");
				addLast.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										if (game.select != null && !game.select.equals(game.data.get(0))) {
												game.data.remove(game.select);
												game.data.get(0).add(game.select);
												Vehicle.updateLocation(game.data.get(0));
												Vehicle.changeRailCarColor(game.select, Color.black);
												game.select = null;
										}
								}
						}
				});
				JMenuItem removeFrist = new JMenuItem("Remove First");
				removeFrist.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										if (game.data.get(0).getNext() != null) {
												Node<Vehicle> readd = game.data.get(0).remove(1);
												Vehicle.updateLocation(game.data.get(0));

												while (true) {
														readd.getData().setLoction(
																(int) (Math.random() * game.WIDTH)
																		- readd.getData().getRectangle().width,
																(int) (Math.random() * game.HEIGHT)
																		- readd.getData().getRectangle().height);
														if (readd.getData() instanceof RailCar)
																((RailCar) readd.getData()).setColor(Color.black);
														if (game.addVehicle(readd.getData())) break;
												}

										}
								}
						}
				});
				JMenuItem removeLast = new JMenuItem("Remove Last");
				removeLast.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
								synchronized (BLOCK) {
										if (game.data.get(0).getNext() != null) {
												Node<Vehicle> readd = game.data.get(0).removeLast();
												Vehicle.updateLocation(game.data.get(0));

												while (true) {

														readd.getData().setLoction(
																(int) (Math.random() * game.WIDTH)
																		- readd.getData().getRectangle().width,
																(int) (Math.random() * game.HEIGHT)
																		- readd.getData().getRectangle().height);
														if (readd.getData() instanceof RailCar)
																((RailCar) readd.getData()).setColor(Color.black);
														if (game.addVehicle(readd.getData())) break;
												}

										}
								}
						}
				});
				menu3.add(addFrist);
				menu3.add(addLast);
				menu3.add(removeFrist);
				menu3.add(removeLast);
				bar.add(menu3);
				Display.CreateFrame(game, bar, "GAME");
				game.start();
		}

		/** @return Make and return the mouse montion listener */
		public MouseMotionListener getMouseMotionListener() {
				return new MouseMotionListener() {

						@Override
						public void mouseMoved(MouseEvent e) {

						}

						@Override
						public void mouseDragged(MouseEvent e) {
								if (select != null)
										Vehicle.setLocationVehicle(select.getData(), e.getX() - selectX, e.getY()
												- selectY);
						}
				};
		}

		/** @return Make and return the mouse listener */
		public MouseListener getMouseListener() {
				return new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
								if (select != null) {
										Vehicle node = Vehicle.getOverLapLastNode(data, select.getData());
										if (node != null && !(select.getData() instanceof TrainEngine)) {
												((RailCar) select.getData()).setColor(Color.BLACK);
												data.remove(select);
												// data.remove(node);
												node.add(select);
												Vehicle.setLocationVehicle(node, node.getData().getX(), node.getData()
														.getY());
												// data.add(node);
												select = null;
										}
								}

						}

						@Override
						public void mousePressed(MouseEvent e) {
								if (select != null) {
										selectX = e.getX() - select.getData().getX();
										selectY = e.getY() - select.getData().getY();
								}
						}

						@Override
						public void mouseExited(MouseEvent arg0) {
								// TODO Auto-generated method stub

						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
								// TODO Auto-generated method stub

						}

						@Override
						public void mouseClicked(MouseEvent e) {

								if (clickIndex == 0) {
										addVehicle(new TrainEngine(e.getX(), e.getY(), 0));
										clickIndex++;
								} else if (clickIndex > 0 && clickIndex < 6) {
										if (addVehicle(new RailCar(e.getX(), e.getY(), clickIndex))) clickIndex++;
								} else if (clickIndex == 6) {
										synchronized (BLOCK) {
												for (int i = 0; i < 5; i++) {
														blocks.add(new Block(e.getX(), e.getY() - (BLOCK_HEIGHT * i),
																BLOCK_WIDTH, BLOCK_HEIGHT, (char) (((int) ('A')) + i)));
												}
												blockX = e.getX();
												blockY = e.getY();
										}
										clickIndex++;
								} else {
										if (select != null) {
												Vehicle.changeRailCarColor(select, Color.black);
										}
										select = Vehicle.getOverLapFirst(data, e.getPoint());
										Vehicle.changeRailCarColor(select, Color.RED);
								}
						}
				};
		}

		/**
		 * @param v
		 *            The vehicle in which to check if it overlap if not add to the
		 *            list
		 * @return True if the vehicle is added to the list
		 */
		public boolean addVehicle(Vehicle v) {
				if (Vehicle.isNodeOverLap(data, v)) return false;
				synchronized (BLOCK) {
						data.add(v);
				}
				return true;
		}
}
