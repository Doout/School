import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Pattern {

		private int type;

		// Create random pattern type
		public Pattern() {
				this((int) Math.random() * Settings.PATTERN_NUMBER_OF_PATTEN);
		}

		/**
		 * @param type
		 *            The type of patten to draw
		 */
		public Pattern(int type) {
				this.type = type;
		}

		/** @return The type for this patten */
		public int getType() {
				return type;
		}

		/**
		 * @param type
		 *            The type to change to
		 */
		public void setType(int type) {
				this.type = type;
		}

		/**
		 * @param g
		 *            The Graphics
		 * @param r
		 *            The game token box to draw the patten in
		 */
		public void draw(Graphics2D g, Rectangle r) {
				switch (type) {
				case Settings.PATTERN_CROSS:
						g.fillRect(r.x + (r.width / 4), r.y, (r.width / 4) * 2, r.height);
						g.fillRect(r.x, r.y + r.height / 4, r.width, (r.height / 4) * 2);
						break;
				case Settings.PATTERN_CIRCLEPLUS:
						g.drawOval(r.x, r.y, r.width, r.height);
				case Settings.PATTERN_SQUARE_X:
						g.drawLine(r.x, r.y, r.x + r.width, r.y + r.height);
						g.drawLine(r.x, r.y + r.height, r.x + r.width, r.y);
						break;
				default:
						break;
				}
		}

		/**
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
				if (this == obj) return true;
				if (obj == null) return false;
				if (getClass() != obj.getClass()) return false;
				Pattern other = (Pattern) obj;
				if (type != other.type) return false;
				return true;
		}
}
