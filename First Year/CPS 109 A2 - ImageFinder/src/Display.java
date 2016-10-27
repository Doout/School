import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

/*****/
public class Display extends Canvas implements Runnable {

		/**
     *
     */
		private static final long serialVersionUID = 8145456036194879237L;
		public final int WIDTH;
		public final int HEIGHT;
		private volatile boolean running;
		private Thread renderThread;

		/**
		 * @param d
		 *            The dimension of the frame
		 **/
		public Display(Dimension d) {
				this((int) d.getWidth(), (int) d.getHeight());
		}

		/**
		 * @param width
		 *            The width of the frame
		 * @param height
		 *            The height of the frame
		 */
		public Display(int width, int height) {
				this.HEIGHT = height;
				this.WIDTH = width;
				final Dimension size = new Dimension((int) (WIDTH), (int) (HEIGHT));
				this.setPreferredSize(size);
				this.setMinimumSize(size);
				this.setMaximumSize(size);
				this.setVisible(true);
				this.setFocusable(true);
		}

		/** Start the thread in which allow the screen to update */
		public void start() {
				if (this.running) return;
				this.renderThread = new Thread(this);
				this.running = true;
				this.renderThread.start();

		}

		/**
		 * Stop the thread from running. After this method is call the screen will
		 * not update anymore
		 **/
		public void Stop() {
				if (!this.running) { return; }
				this.running = false;
				try {
						this.renderThread.join(500);
				} catch (final InterruptedException e) {
						System.exit(0);
				}
		}

		// The main int for the search
		private int w, h, w2, h2, w3, h3;
		private static final Color search = new Color(255, 255, 255, 200), find = new Color(0, 0,0,
				100);
		// The image which is render
		private BufferedImage img;
		// Zoom in stuff
		private long time;
		private int zoom, maxZoom = 100, xOffSet = 0, yOffSet = 0, xx, yy;

		/**
		 * The main method in which everything is render and also update as well. <br>
		 * This method dont use the gpu.
		 */
		public void render() {
				final BufferStrategy bs = this.getBufferStrategy();
				if (bs == null) {
						this.createBufferStrategy(2);
						return;
				}
				final Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				// TODO
				int[] data = RenderData.getData();
				if (w == 0) {
						//inti all the var that we need to render the boxs
						w = Finder.getFinder().screen.getWidth() / getWidth();// get the
																																	// scale of
																																	// the image
						h = Finder.getFinder().screen.getHeight() / getHeight();
						w2 = (Finder.data >> 16) / w;
						h2 = (Finder.data & Short.MAX_VALUE) / h;
						w3 = (Finder.getFinder().screen.getWidth() - getWidth()) / maxZoom;
						h3 = (Finder.getFinder().screen.getHeight() - getHeight()) / maxZoom;
						//resize to fix the frame
						this.img = Utils.resizeImage(Finder.getFinder().screen, BufferedImage.TYPE_INT_ARGB,
								getWidth(), getHeight());
				}
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.drawImage(img, xOffSet, yOffSet, null);
				if (data != null) { // render the box for the search
						g.setColor(search);
						for (int i = 0; i < data.length; i++) {
								int x = data[i] >> 16;
								int y = data[i] & Short.MAX_VALUE;
								if (x <= 0 || y <= 0) continue;
								g.fillRect(x / w, y / h, w2, h2);
						}
				}
				if (RenderData.getBestMatch() != null) { // render the best match if found
						if (RenderData.getData() != null) {
								g.setColor(find);
								int x = RenderData.getBestMatch().getLoaction() >> 16;
								int y = RenderData.getBestMatch().getLoaction() & Short.MAX_VALUE;
								g.fillRect(x / w, y / h, w2, h2);
						} else { // render the best match and zoom in as well
								if (zoom < maxZoom - 10)
										if (System.currentTimeMillis() - time > 100) {
												if (zoom == 0) {
														int x = RenderData.getBestMatch().getLoaction() >> 16;
														int y = RenderData.getBestMatch().getLoaction() & Short.MAX_VALUE;
														Graphics2D g2 = Finder.getFinder().screen.createGraphics();
														g2.setColor(find);
														g2.fillRoundRect(x, y, w2 * w, h2 * h, w2 * w / 10, h2 * h / 10);
														g2.setColor(Color.BLACK);
														g2.drawRoundRect(x, y, w2 * w, h2 * h, w2 * w / 10, h2 * h / 10);
														g2.dispose();

														xx = x / maxZoom;
														yy = y / maxZoom;
												}
												zoom++;
												time = System.currentTimeMillis();
												this.img = Utils.resizeImage(Finder.getFinder().screen,
														BufferedImage.TYPE_INT_ARGB, getWidth() + (zoom * w3), getHeight()
																+ +(zoom * h3));
												yOffSet -= yy;
												xOffSet -= xx;
										}

						}
				}
				g.dispose();
				bs.show();

		}

		/**
		 * Create a frame in which the canvas can be render on
		 * 
		 * @param dis
		 *            The Display in which to add to the frame
		 * @param name
		 *            The title of the frame (GUI)
		 **/
		public static JFrame CreateFrame(Display dis, String name) {
				final Display game = dis;
				final JFrame frame = new JFrame();
				// frame.setUndecorated(true);
				frame.add(game);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setResizable(false);
				frame.setLocationRelativeTo(null);
				frame.setTitle(name);
				frame.setVisible(true);
				frame.setAlwaysOnTop(true);
				return frame;
		}

		@Override
		public void run() {
				while (this.running) {
						render();
				}
		}
}