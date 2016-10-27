 import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayDeque;

import javax.swing.JFrame;

public class Finder {

		BufferedImage screen, find;
		private Display dis;
		volatile int[] pixs;
		volatile int[] match;
		public static int data;
		public static Finder finder;

		public static void main(String... strs) {
				// Finder f = new Finder("scene.png", "waldo.png");
				Finder f = new Finder("color.png", "find.png");
				long start = System.currentTimeMillis();
				int location = f.findMatch(9);
				long end = System.currentTimeMillis();
				System.out.println("Time : " + (end - start));
				System.out.println("x : " + (location >> 16) + " y : " + (location & Short.MAX_VALUE));

		}

		
		/**
		 * @param screen
		 *            The map in which to search for the other image.
		 * @param find
		 *            The image in which to search for in the map
		 **/
		public Finder(String screen, String find) {
				finder = this;
				this.screen = Utils.getImage(screen);
				this.find = Utils.getImage(find);
				pixs = Utils.getPixs(this.screen);
				match = Utils.getPixs(this.find);
				Finder.data = (this.find.getWidth() << 16) | (this.find.getHeight()) & Short.MAX_VALUE;
				this.dis = new Display(Toolkit.getDefaultToolkit().getScreenSize());
				// this.dis = new Display(600, 600);
				Display.CreateFrame(dis, "Finder");
				if (this.screen == null || this.find == null) {
						System.err.println("Can not find image");
						System.exit(0);
				}
		}

		volatile int threadDone = 0;

		/**
		 * Try to find the image within the map using more then 1 thread if
		 * numberOfThread > 1
		 * 
		 * @param numberOfThread
		 *            The amount of thread in which to serach for the image
		 *
		 * @return The hash of the location of the search <br>
		 *         <code> x = hash >> 16  <br> y = hash & Short.MAX_VALUE</code>
		 **/
		public int findMatch(int numberOfThread) {
				RenderData.init(numberOfThread);
				getDisplay().start();
				int si = screen.getWidth() * screen.getHeight();
				final int scan = si / numberOfThread;
				ArrayDeque<Data> list = new ArrayDeque<Finder.Data>();
				for (int i = 0; i < numberOfThread; i++) {
						final int i2 = i;
						new Thread(new Runnable() {
								@Override
								public void run() {
										Data temp = findMatch(i2 * scan, scan);
										if (temp.off >= 0) list.add(temp);
										threadDone++;
								}
						}).start();
				}
				while (threadDone < numberOfThread) {
						try {
								Thread.sleep(20);
						} catch (InterruptedException e) { // Nothing we can do
						}
				}
				long bestMatch = Long.MAX_VALUE;
				int location = 0;
				for (Data d : list) {
						if (d.off < bestMatch) {
								bestMatch = d.off;
								location = d.loaction;
						}
				}
				RenderData.nullData();
				return location;
		}

		/***
		 * @param startIndex
		 *            The index in which to start the search.
		 * @param scanSize
		 *            The Number of pixels in which to check from the offset index.
		 * 
		 * @return The number of pixels off and the location of the best match in
		 *         which it found with in the range.
		 */
		public Data findMatch(int startIndex, int scanSize) {
				int w = screen.getWidth(), h = screen.getHeight(), w2 = find.getWidth(), h2 = find
						.getHeight(), w3 = w - w2, h3 = h - h2;
				int index = startIndex / scanSize;
				if (index < 0) index = 0;
				// TODO use only a part of the match and then search
				long bestMatch = Long.MAX_VALUE;
				int location = 0;
				long maxOff = (long) (w2 * h2 * 3 * 255 * 0.013); // 0.013 the lower

				// value before it
				// break.
				int scan = 0;
				int drawCount = 0;

				for (int y = (startIndex / h); y < h3 && scan < scanSize; y++) {
						for (int x = (startIndex % w); x < w3 && scan < scanSize; x++, scan++) {
								drawCount++;
								if (drawCount > 100) {
										RenderData.update(index, x << 16 | (y & Short.MAX_VALUE));
										drawCount = 0;

								}
								long tempMatch = match(pixs, match, x, y, w2, h2, w, maxOff);
								if (tempMatch < bestMatch && tempMatch >= 0) {
										bestMatch = (long) tempMatch;
										location = x << 16 | y;
										System.out.println("New best match hash x " + x + " y " + y);
										if (RenderData.getBestMatch() == null) {
												RenderData.setBestMatch(new Data(bestMatch, location));
										} else if (RenderData.getBestMatch().off > bestMatch) {
												RenderData.setBestMatch(new Data(bestMatch, location));
										}

								}
						}

				}
				return new Data(bestMatch, location);
		}

		/***/
		public int findMatch() {
				return findMatch(0, screen.getWidth() * screen.getHeight()).loaction;
		}

		/**
		 * @param map
		 *            The list of pixels for the screen/map
		 * @param find
		 *            The list of pixels in which to search in the map
		 * @param xOffset
		 *            The off set of the pixels in the x to search in map.
		 * @param yOffset
		 *            The off set of the pixels in the y to search in map.
		 * @param width
		 *            The width of the image to search for.
		 * @param height
		 *            The height of the image to search for.
		 * @param scanSize
		 *            The width of the map image.
		 * 
		 * @param maxoff
		 *            The number of pixels in which is allow to be off by. <br>
		 *            The color is broken down to the main 3 color and the value are
		 *            then check to see how off there are from each other, then the
		 *            value is adding to the number of off pixels
		 * 
		 * @return If the number of off pixels is less then maxOff then it return
		 *         the amount of off pixels <br>
		 *         else return -1
		 */
		public long match(int[] map, int[] find, int xOffset, int yOffset, int width, int height,
				int scanSize, long maxoff) {
				long match = 0;
				if (xOffset + width >= scanSize || yOffset + height > (map.length / scanSize)) return -1; // no
				// match.
				for (int y = 0; y < height; y++) {
						for (int x = 0; x < width; x++) {
								match += getMatch(map[(y + yOffset) * scanSize + (x + xOffset)],
										find[y * width + x]);
								// System.out.println(match);
								if (match > maxoff || (match > RenderData.getBestMatch().off)) return -1;

						}
				}
				return match;
		}

		/**
		 * @param colorOne
		 *            The first color
		 * @param colorTwo
		 *            The Second color
		 * @return The amount of color that are off, <br>
		 *         eg red = 255 on color one and red = 0 on the second one, <br>
		 *         that will return 255 in the other 2 color are the same
		 * **/
		public int getMatch(int colorOne, int colorTwo) {
				int n = 0;
				if (colorOne == colorTwo) return 0;
				n += Math.abs(((colorOne >> 16) & 0xFF) - ((colorTwo >> 16) & 0xFF)); // Red
				n += Math.abs(((colorOne >> 8) & 0xFF) - ((colorTwo >> 8) & 0xFF)); // Green
				n += Math.abs(((colorOne) & 0xFF) - ((colorTwo) & 0xFF)); // Blue
				return n;
		}

		public Display getDisplay() {
				return dis;
		}

		public static Finder getFinder() {
				return finder;
		}

		/**
		 * This class hold the loaction of the best match as well as it location in
		 * a hash
		 **/
		static class Data {
				final long off;
				final int loaction;

				public Data(long off, int loaction) {
						this.off = off;
						this.loaction = loaction;
				}

				public long getOff() {
						return off;
				}

				public int getLoaction() {
						return loaction;
				}

		}
}
