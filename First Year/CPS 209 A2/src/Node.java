import java.util.NoSuchElementException;

public class Node<T> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + ((getData() == null) ? 0 : getData().hashCode());
				result = prime * result + ((getNext() == null) ? 0 : getNext().hashCode());
				return result;
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
				Node other = (Node) obj;
				if (data == null) {
						if (other.getData() != null) return false;
				} else if (!data.equals(other.getData())) return false;
				if (trailer == null) {
						if (other.trailer != null) return false;
				} else if (!trailer.equals(other.trailer)) return false;
				return true;
		} 

		private Node<T> trailer;
		private T data;

		/**
		 * @param node
		 *            The node in which to add to the end of the list
		 */
		public void add(Node<T> node) {
				if (trailer != null) trailer.add(node);
				else trailer = node;
		}

		public static void main(String[] args) {
				Node<String> info = new Node<String>();
				info.setData("test");
				Node<String> info2 = new Node<String>();
				info2.setData("test2");
				Node<String> info3 = new Node<String>();
				info3.setData("test3");
				// info.shiftNode(info2);

				Node<String> info4 = new Node<String>();
				info4.setData("test4");
				info.add(info2);
				// info.add(info3);
				info.add(info4);
				info.add(info3, 1);
				// System.out.println(info.removeLast().getData());
				// info.remove(1);
				Node<String> temp = info;
				while (temp != null) {
						System.out.println(temp.getData());
						temp = temp.trailer;
				}
		}

		/**
		 * @param ne
		 *            The node in which to add to the index of the list
		 * @param index
		 *            The index in which to add the new node, Index start from the
		 *            node which is call
		 **/
		public void add(Node<T> ne, int index) {
				if (index == 0) {
						addFrist(ne);
						return;
				}
				Node<T> before = this;
				for (int i = 1; i < index && before != null; i++) {
						before = trailer;
				}
				if (before.trailer == null) {
						before.add(ne);
						return;
				}
				Node<T> temp = new Node<T>();
				temp.setData(before.trailer.getData());
				temp.trailer = before.trailer.getNext();
				before.trailer = ne;
				before.trailer.add(temp);
				before.trailer.data = ne.getData();
		}

		/**
		 * @param newFirst
		 *            The new node which should be added first
		 */
		public void addFrist(Node<T> newFirst) {
				Node<T> temp = new Node<T>();
				temp.setData(data);
				temp.trailer = trailer;
				this.trailer = temp;
				data = newFirst.getData();
		}

		/**
		 * @param node
		 *            The node in which should be remove
		 */
		public void remove(Node<T> node) {
				if (trailer == null) return;
				if (node == null) throw new NoSuchElementException();
				if (trailer.equals(node)) {
						trailer = trailer.trailer;
				} else trailer.remove(node);
		}

		/**
		 * @param index
		 *            The index from this node to be remove
		 * @return The node that was remove
		 */
		public Node<T> remove(int index) {
				if (trailer == null) return null;
				if (index <= 1) {
						Node<T> temp = trailer;
						trailer = trailer.trailer;
						temp.trailer = null;
						return temp;
				} else return trailer.remove(--index);
		}

		/** @return The next node */
		public Node<T> getNext() {
				return trailer;
		}

		/**
		 * @param data
		 *            The data in which to set for this class
		 */
		public void setData(T data) {
				this.data = data;
		}

		/** @return THe data that was in this node */
		public T getData() {
				return data;
		}

		/** @return The node in whihc was remove which is the last node */
		public Node<T> removeLast() {
				if (trailer == null) return null;
				else if (trailer.trailer == null) {
						Node<T> temp = trailer;
						trailer = null;
						return temp;
				} else return trailer.removeLast();
		}

}
