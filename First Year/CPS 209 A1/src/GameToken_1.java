public class GameToken_1 extends GameToken {

		

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
		public GameToken_1(int x, int y, int width, int height) {
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
		public GameToken_1(int patternType, int x, int y, int width, int height) {
				super(patternType, x, y, width, height);
		}

		/**
		 * Make the token turn off after a set time then turn it back on with
		 *  a random time where the time on is the max value
		 * **/
		@Override
		public void setVisibilityPolicy() {
				if (!timer.isRunning()) {
						setVisible(!isVisible());
						if (isVisible()) timer.reset();
						else timer.resetRandom();
				}
		}

}
