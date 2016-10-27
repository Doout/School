public class GameToken_2 extends GameToken {

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
		public GameToken_2(int patternType, int x, int y, int width, int height) {
				super(patternType, x, y, width, height);
				timer = null;
		}

		/**
		 * @param x
		 *            The X location
		 * @param y
		 *            The Y location
		 * @param width
		 *            The Width of the token
		 * @param height
		 *            The Height of the token
		 */
		public GameToken_2(int x, int y, int width, int height) {
				this(-1, x, y, width, height);
		}

		/** If the token is not visible then turn it on */
		@Override
		public void setVisibilityPolicy() {
				if (!isVisible()) setVisible(true);
		}
}
