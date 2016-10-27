import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * @author Baheer Kamal This class hold the method in which more then one class
 *         might use
 **/
public class Utils {

		/**
		 * @param originalImage
		 *            The Image in which to resize
		 * @param type
		 *            The type of BufferedImage
		 * @param width
		 *            The new width of the image.
		 * @param height
		 *            The height of the new image
		 */
		public static BufferedImage resizeImage(BufferedImage originalImage, int type, int width,
				int height) {
				BufferedImage resizedImage = new BufferedImage(width, height, type);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(originalImage, 0, 0, width, height, null);
				g.dispose();
				return resizedImage;
		}

		/**
		 * Load the image from the resource folder
		 * 
		 * @param name
		 *            The name of the image file in the resource folder.
		 */
		public static BufferedImage getImage(String name) {
				URL temp = Utils.class.getResource(name);
				if (temp != null) {
						try {
								return ImageIO.read(temp);
						} catch (IOException e) {
								System.out.println(e);
								return null;
						}
				}
				return null;
		}

		/**
		 * @param img
		 *            The Image in which to return the array of pixels
		 * @return The array of pixels in the image.
		 */
		public static int[] getPixs(BufferedImage img) {
				int w = img.getWidth(), h = img.getHeight();
				int[] pix = new int[w * h];
				img.getRGB(0, 0, w, h, pix, 0, w);
				return pix;
		}

		public static int random(final int min, final int max) {
				return min + new Random().nextInt(max - min);
		}

}
