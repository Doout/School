import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BrainTrainer extends Display {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1468148744561492678L;
		// Brain Traniner var

		private int total;
		private int timeClick = 0; // set the mouse game token pattem
		private GameToken mouse;
		private ArrayList<GameToken> boxs;
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg;

		// Create a new blank cursor.
		Cursor blankCursor;

		/**
		 * @see Display#Display(int, int)
		 * */
		public BrainTrainer(int width, int height) {
				super(width, height);
		}

		/**
		 * Just the main method which to start the game
		 * */
		public static void main(String[] args) {
				Display game = new BrainTrainer(400, 600);
				Display.CreateFrame(game, "Brain Trainer");
				game.start();
		}
		/**
		 * The method which update the game. In this case the visibility policy for each box
		 * */
		@Override
		public void update() {
				for (GameToken t : boxs)
						t.setVisibilityPolicy();
		}
		/**
		 * the method in which draw the game.
		 * @param g The graphics class which hold some of the draw method.*/
		@Override
		public void render(Graphics2D g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
				for (GameToken t : boxs) {
					
						t.draw(g);
				}
				g.setColor(Color.black);
				mouse.draw(g);
				g.drawString("Scored " + this.total, 20, 20);

		}
		/**This method run only one when the class get made.
		 * Set the mouse listener and find all the location to put the box**/
		@Override
		public void init() {
				this.addMouseListener(getMouseListener());
				this.addMouseMotionListener(getMouseMotionListener());
				mouse = new GameToken(10, 10, Settings.GAMETOKEN_WIDTH, Settings.GAMETOKEN_HEIGHT);
				mouse.setVisible(true);
				mouse.setColor(Color.BLUE);
				boxs = new ArrayList<GameToken>();
				cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
				blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0),
						"blank cursor");
				setCursor(blankCursor);
				GameToken temp;
				int yOffSet = 30;
				int w = (WIDTH - Settings.GAMETOKEN_WIDTH);
				int h = (HEIGHT - Settings.GAMETOKEN_HEIGHT - yOffSet);
				for (int i = 0; i < 20; i++) {
						int timeTry = 0;
						while (timeTry++ < 50) {
								switch ((int) (Math.random() * 3)) {
								case 1:
										temp = new GameToken_2(
												(int) (Math.random() * Settings.PATTERN_NUMBER_OF_PATTEN),
												(int) (Math.random() * w), yOffSet + (int) (Math.random() * h),
												Settings.GAMETOKEN_WIDTH, Settings.GAMETOKEN_HEIGHT);
										break;
								case 2:

										temp = new GameToken_1(
												(int) (Math.random() * Settings.PATTERN_NUMBER_OF_PATTEN),
												(int) (Math.random() * w), yOffSet + (int) (Math.random() * h),
												Settings.GAMETOKEN_WIDTH, Settings.GAMETOKEN_HEIGHT);
										break;
								default:
										temp = new GameToken((int) (Math.random() * Settings.PATTERN_NUMBER_OF_PATTEN),
												(int) (Math.random() * w), yOffSet + (int) (Math.random() * h),
												Settings.GAMETOKEN_WIDTH, Settings.GAMETOKEN_HEIGHT);
										break;
								}

								if (!overLap(temp)) {
										boxs.add(temp);
										break;
								}

						}
				}
		}
		/**Check if the game token over lap any other token
		 * 
		 * @param game the GameToken in which to check if it over lap
		 * */
		public boolean overLap(GameToken game) {
				for (GameToken g : boxs)
						if (g.overlaps(game)) return true;
				return false;
		}
		/**Make and return the mouse listener*/
		public MouseListener getMouseListener() {
				return new MouseListener() {

						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseExited(MouseEvent e) {
						}

						@Override
						public void mouseEntered(MouseEvent e) {
						}

						@Override
						public void mouseClicked(MouseEvent e) {
								if (e.getButton() == 3) {
										mouse.getPattern().setType(++timeClick % Settings.PATTERN_NUMBER_OF_PATTEN);
								} else if (e.getButton() == 1) {
										int index = -1;
										double max = 0;
										for (int i = 0; i < boxs.size(); i++) {
												if (mouse.getBox().intersects(boxs.get(i).getBox())) {
														Rectangle2D rect = mouse.getBox().createIntersection(
																boxs.get(i).getBox());
														if ((rect.getWidth() * rect.getHeight()) > max) {
																max = (rect.getWidth() * rect.getHeight());
																index = i;
														}
												}
										}
										if (index >= 0) {
												if (boxs.get(index).getPattern().equals(mouse.getPattern())
														&& !boxs.get(index).getColor().equals(Color.green)) {
														boxs.get(index).setColor(Color.green);
														if (boxs.get(index).getClick() < Settings.TRY_ALLOW) {
																if (boxs.get(index).getClick() < 2) total += (2 - boxs.get(index)
																		.getClick());
																else total++;
														}
												} else {
														// if the click time is move then 2
														boxs.get(index).addClick();
														if (boxs.get(index).getClick() >= Settings.TRY_ALLOW
																&& !boxs.get(index).getColor().equals(Color.green)) {
																boxs.get(index).setColor(Color.RED);
																total--;
														}
												}
										}
								}
						}
				};
		}
		/**Make the mouse montion listerner and return it*/
		public MouseMotionListener getMouseMotionListener() {
				return new MouseMotionListener() {

						@Override
						public void mouseMoved(MouseEvent e) {
								mouse.getBox().setLocation(e.getPoint());
						}

						@Override
						public void mouseDragged(MouseEvent e) {
						}
				};
		}

}
