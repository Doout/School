import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameToken implements VisibleShape {
		private boolean visible;
		public Rectangle bbox;
		private Pattern pattern;
		private int timeClick = 0;
		private Color color;
		protected Timer timer;

		/**
		 * 
		 * @param x
		 *            The X location
		 * @param y
		 *            The Y location
		 * @param width
		 *            The Width of the token
		 * @param height
		 *            The Height of the token
		 */
		public GameToken(int x, int y, int width, int height) {
				this(-1, x, y, width, height);
		}

		/**
		 * @param patternType
		 *            The {@link Pattern} type
		 * @param x
		 *            The X location
		 * @param y
		 *            The Y location
		 * @param width
		 *            The Width of the token
		 * @param height
		 *            The Height of the token
		 */
		public GameToken(int patternType, int x, int y, int width, int height) {
				bbox = new Rectangle(x, y, width, height);
				this.pattern = patternType == -1 ? new Pattern() : new Pattern(patternType);
				this.timer = new Timer(3000 + (int) (Math.random() * 3000));
				this.visible = true;
				this.color = Color.BLACK;
		}

		public void reset() {
				visible = true;
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
				GameToken other = (GameToken) obj;
				if (bbox == null) {
						if (other.bbox != null) return false;
				} else if (!bbox.equals(other.bbox)) return false;
				if (color == null) {
						if (other.color != null) return false;
				} else if (!color.equals(other.color)) return false;
				if (pattern == null) {
						if (other.pattern != null) return false;
				} else if (!pattern.equals(other.pattern)) return false;
				if (visible != other.visible) return false;
				return true;
		}

		/** THe method in which the token is draw from */
		@Override
		public void draw(Graphics2D g) {
				g.setColor(getColor());
				g.draw(getBox());
				if (visible || getClick() >= 2 || getColor().equals(Color.GREEN))
						pattern.draw(g, getBox());
		}

		/**
		 * Check if the shape pattem shoud be visibility or no
		 */
		@Override
		public void setVisibilityPolicy() {
				if (timer != null && !timer.isRunning()) {
						setVisible(false);
						timer = null;
				}
		}

		/**
		 * @param other
		 *            The visibleShape in which to check the if it over lap or not
		 * */
		@Override
		public boolean overlaps(VisibleShape other) {
				if (other instanceof GameToken) return bbox.intersects(((GameToken) other).getBox());
				return false;
		}

		/**
		 * @return the visible
		 */
		public boolean isVisible() {
				return visible;
		}

		/**
		 * @param visible
		 *            the visible to set
		 */
		public void setVisible(boolean visible) {
				this.visible = visible;
		}

		/**
		 * @return the bbox
		 */
		public Rectangle getBox() {
				return bbox;
		}

		/**
		 * @param bbox
		 *            the bbox to set
		 */
		public void setBox(Rectangle bbox) {
				this.bbox = bbox;
		}

		/**
		 * @return the pattern
		 */
		public Pattern getPattern() {
				return pattern;
		}

		/**
		 * @param pattern
		 *            the pattern to set
		 */
		public void setPattern(Pattern pattern) {
				this.pattern = pattern;
		}

		/**
		 * @return the color
		 */
		public Color getColor() {
				return color;
		}

		/**
		 * @param color
		 *            the color to set
		 */
		public void setColor(Color color) {
				this.color = color;
		}

		public int getClick() {
				return timeClick;
		}

		public void addClick() {
				timeClick++;
		}

		public void resetClick() {
				timeClick = 0;
		}

}
