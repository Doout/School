

public class RenderData {

		private static int[] data;

		/**
		 * @param The
		 *            number of thread that was use in the search
		 */
		public static void init(int numberOfThread) {
				data = new int[numberOfThread];
		}

		/**
		 * @param index
		 *            The index in the array to update
		 * @param hash
		 *            The hash of the location, x and y are less then 2E16 (0x10000)
		 *            <code> hash = x << 16 | y</code>
		 */
		public static void update(int index, int hash) {
				synchronized (data) {
						data[index] = hash;
				}
		}

		public static Finder.Data bestMatch = new Finder.Data(Long.MAX_VALUE, 0);

		/**
		 * @param match
		 *            The data for the best match found so far in the search
		 */
		public static void setBestMatch(Finder.Data match) {
				bestMatch = match;
		}

		/** @return The best match found */
		public static Finder.Data getBestMatch() {
				return bestMatch;
		}

		/**
		 * Set the array list to = null<br>
		 * Use inti(int) before using update(int,int)
		 **/
		public static void nullData() {
				data = null;
		}

		/**
		 * @param the
		 *            array of int of the data
		 */
		public static int[] getData() {
				if (data == null) return null;
				return data.clone();
		}

}
