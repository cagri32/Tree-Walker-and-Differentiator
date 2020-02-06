import java.io.FileNotFoundException;
import java.io.IOException;

public class TreeWalker extends IOTree {

	// it inherits root and size from LinkedBinaryTree vis IOTree;
	Position current;

	/************************************************************************************************/
	/** Constructors **/
	/** @param () ==> use default tree description **/
	/** (String toParse) ==> Tree built from this parsing **/
	/**                                                                                        	**/
	/************************************************************************************************/

	public TreeWalker() throws IOException {
		this("((10 20 30) 40 (null 50 (60 70 80)))"); // The TreeWalker constructor with use default tree description
		// this("null"); // The TreeWalker constructor with use default tree description
	}

	public TreeWalker(String toParse) {
		super(toParse);
		makeCurrent(root());
	}

	/************************************************************************************************
	 * root, parent, leftChild, and rightChild return the state position of given
	 * position. * - They create it if it is not there. *
	 ************************************************************************************************/

	public Position root(Position position) {
		/* My code had 7 */
		if (this.root() == null) { // position==null
			Element e = new Element('c', 100, "100", 'x');
			BTNode root = new BTNode(e, null, null, null);
			super.setRoot(root);
			position = this.root();
			return position;
		} else {
			return super.root();
		}
	}

	public Position parent(Position position) throws InvalidPositionException {
		/* My code had 11 lines */
		if (!this.isRoot(position) && super.parent(position) != null) {
			return super.parent(position);
		}
		if (this.isRoot(position)) {
			Element e = new Element('r', 10, "100", 'x');
			BTNode node = (BTNode) position;
			BTNode newNode = new BTNode(e, node, null, null);
			node.setParent(newNode);
			this.setRoot((Position) newNode);
			this.setSize(this.size() + 1);
			return this.set((Position) newNode);
		}
		throw new InvalidPositionException("position has no parent.");

	}

	public Position leftChild(Position position) {
		if (position == null)
			throw new InvalidPositionException("position is null.");
		BTNode node = (BTNode) position;
		/* My code had 7 lines */
		if (node.getLeft() == null) {
			int value = value(position) - 2;
			Element e = new Element('c', value, "100", 'x');
			BTNode leftchild = new BTNode(e, null, null, node);
			node.setLeft(leftchild);
			position = leftchild;
			this.setSize(this.size() + 1);
			return position;
		} else {
			return node.getLeft();
		}
	}

	public Position rightChild(Position position) {
		/* My code had 7 lines */
		if (position == null)
			throw new InvalidPositionException("position is null.");
		BTNode node = (BTNode) position;
		if (node.getRight() == null) {
			int value = value(position) + 2;
			Element e = new Element('c', value, "100", 'x');
			BTNode rightchild = new BTNode(e, null, null, node);
			node.setRight(rightchild);
			position = rightchild;
			this.setSize(this.size() + 1);
			return position;
		} else {
			return node.getRight();
		}
	}

	/************************************************************************************************
	 * rotateR rotates a node raising its left child to its position and lowering
	 * itself to the right * rotateL rotateR does the reverse *
	 ************************************************************************************************/

	public Position rotateR(final Position position) {

		/* My code had 16 lines */

		if (super.leftChild(position) == null) {
			System.out.println("No left child");
			return position;
		}
		BTNode parent = (BTNode) super.parent(position);
		BTNode node = (BTNode) position;
		BTNode child = (BTNode) super.leftChild(position);
		BTNode tree2 = (BTNode) super.rightChild((Position) child);
		if (parent != null && parent.getLeft() == node) {
			parent.setLeft(child);
		}
		if (parent != null && parent.getRight() == node) {
			parent.setRight(child);
		}
		if (node == this.root()) {
			this.setRoot((Position) child);
		}
		child.setParent(parent);
		child.setRight(node);
		node.setParent(child);
		node.setLeft(tree2);
		if (tree2 != null) {
			tree2.setParent(node);
		}
		return (Position) child;
	}

	public Position rotateL(final Position position) {
		/* My code had 16 lines */

		if (super.rightChild(position) == null) {
			System.out.println("No right child");
			return position;
		}
		BTNode parent = (BTNode) super.parent(position);
		BTNode node = (BTNode) position;
		BTNode child = (BTNode) super.rightChild(position);
		BTNode tree2 = (BTNode) super.leftChild((Position) child);
		if (parent != null && parent.getLeft() == node) {
			parent.setLeft(child);
		}
		if (parent != null && parent.getRight() == node) {
			parent.setRight(child);
		}
		if (node == this.root()) {
			this.setRoot((Position) child);
		}
		child.setParent(parent);
		child.setLeft(node);
		node.setParent(child);
		node.setRight(tree2);
		if (tree2 != null) {
			tree2.setParent(node);
		}
		return (Position) child;
	}

	/************************************************************************************************
	 * first, last, next, previous return this position according to the infix
	 * traversal order. * - The last two create it if it is not there. *
	 ************************************************************************************************/

	public Position first(Position position) {
		/* My code had 4 lines */

		position = this.root();
		BTNode node = (BTNode) position;
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;

	}
	// time complexity is in the number of bits 2^O(n)
	// amortized time is the time for per operation

	public Position last(Position position) {
		/* My code had 4 lines */
		position = this.root();
		BTNode node = (BTNode) position;
		while (node.getRight() != null) {
			node = node.getRight();
		}
		return node;
	}

	public Position next(Position position) {
		/* My code had 13 lines */
		Position newposition = position;

		if (super.rightChild(position) != null) {

			position = super.rightChild(position);
			while (super.leftChild(position) != null) {
				position = super.leftChild(position);
			}

		} else {

			while (super.parent(position) != null && super.rightChild(super.parent(position)) == position) {
				position = super.parent(position);
			}

			if (super.parent(position) != null) {

				position = super.parent(position);

			} else {
				position = this.set(this.rightChild(newposition));

			}
		}
		return position;
	}

	public Position previous(Position position) {
		/* My code had 13 lines */
		Position newposition = position;
		if (super.leftChild(position) != null) {
			position = super.leftChild(position);
			while (super.rightChild(position) != null) {
				position = super.rightChild(position);
			}
		} else {

			while (super.parent(position) != null && super.leftChild(super.parent(position)) == position) {
				position = super.parent(position);
			}

			if (super.parent(position) != null) {

				position = super.parent(position);

			} else {

				position = this.set(this.leftChild(newposition));
			}
		}
		return position;
	}

	/************************************************************************************************
	 * set sets the value of a position to be an integer within its binary search
	 * tree order * (set returns the same position to allow position =
	 * set(leftChild(position1)) *
	 ************************************************************************************************/

	public Position set(Position position) {
		/* My code had 11 lines */
		int i;
		if (position == this.first(position) && position == this.last(position)) {

			i = this.value(position);

		} else if (position == this.first(position)) {

			i = this.value(this.next(position)) - 10;

		} else if (position == this.last(position)) {

			i = this.value(this.previous(position)) + 10;

		} else {

			i = (this.value(this.previous(position)) + this.value(this.next(position))) / 2;
		}
		this.setValue(position, i);
		return position;

	}

	/*************************************************************************************************
	 * insert inserts a new node after the current node according to the tree's
	 * infix Traversal order,* i.e. go right and then left left left and put the new
	 * node there. *
	 ************************************************************************************************/

	public Position insert(Position position) {
		/* My code had 5 lines */
		// BTNode node = (BTNode) position;
		if (super.rightChild(position) == null) {
			return this.set(this.rightChild(position));
		}
		position = this.next(position);
		return this.set(this.leftChild(position));

	}

	/*************************************************************************************************
	 * deletes the current node * If no right child use deleteNoRight, if no left
	 * use deleteNoLeft * else move contents of next to current and delete next *
	 ************************************************************************************************/

	/* Deletes current when no right child */
	/* Parent adopts left child */

	private Position deleteNoRight(Position position) {
		if (super.rightChild(position) == null) {
			BTNode parent = (BTNode) super.parent(position);
			BTNode node = (BTNode) position;
			BTNode child = (BTNode) super.leftChild(position);
			if (parent != null && parent.getLeft() == node)
				parent.setLeft(child);
			if (parent != null && parent.getRight() == node)
				parent.setRight(child);
			if (node == root())
				setRoot(child);
			if (child != null) {
				child.setParent(parent);
				position = child;
			} else
				position = parent;
			return position;
		} else {
			System.out.println("Panic: Is right child");
			return null;
		}
	}

	/* Deletes current when no left child */
	/* Parent adopts right child */

	private Position deleteNoLeft(Position position) {
		if (super.leftChild(position) == null) {
			BTNode parent = (BTNode) super.parent(position);
			BTNode node = (BTNode) position;
			BTNode child = (BTNode) super.rightChild(position);
			if (parent != null && parent.getRight() == node)
				parent.setRight(child);
			if (parent != null && parent.getLeft() == node)
				parent.setLeft(child);
			if (node == root())
				setRoot(child);
			if (child != null) {
				child.setParent(parent);
				position = child;
			} else
				position = parent;
			return position;
		} else {
			System.out.println("Panic: Is left child");
			return null;
		}
	}

	public Position delete(Position position) {
		/* My code had 10 lines */
		if (super.rightChild(position) == null) {
			return this.deleteNoRight(position);
		}
		if (super.leftChild(position) == null) {
			return this.deleteNoLeft(position);
		}
		BTNode node = (BTNode) position;
		BTNode delete = (BTNode) this.next(position);
		node.setElement(delete.element());
		this.deleteNoLeft((Position) delete);
		return position;

	}

	public int evaluate(int x, int y, int z) {
		System.out.println("Test: evaluate");
		BTNode root = (BTNode) this.root();
		return this.eval(root, x, y, z);
	}

	public int eval(BTNode n, int x, int y, int z) {

		Element nElement = ((Element) n.element());
		if (nElement.IsNumb()) {
			return (value(n));
		} else if (nElement.c == 'x') {
			return (x);
		} else if (nElement.c == 'y') {
			return (y);
		} else if (nElement.c == 'z') { // ((Element) n.element()).IsChar() &&
			return (z);
		} else if (nElement.c == '+') {
			return (eval(n.getLeft(), x, y, z) + eval(n.getRight(), x, y, z));
		} else if (nElement.c == '-') {
			return (eval(n.getLeft(), x, y, z) - eval(n.getRight(), x, y, z));
		} else if (nElement.c == '*') {
			return eval(n.getLeft(), x, y, z) * eval(n.getRight(), x, y, z);
		} else if (nElement.c == '/' && eval(n.getRight(), x, y, z) != 0) {
			return eval(n.getLeft(), x, y, z) / eval(n.getRight(), x, y, z);
		} else {
			System.out.println("Test: eval" + " the element is " + n.element().toString());
			return 0;
		}
	}

	public void differentiate() {
		BTNode root = (BTNode) this.root();
		this.setRoot(this.derivative(root, 'x'));
		this.current = this.root();
		System.out.println("Test: differentiate");
	}

	public BTNode derivative(BTNode n, char c) {

		BTNode f = this.Copy(n);
		if (f == null) {
			return null;
		}
		BTNode g = this.Copy(n.getLeft());
		BTNode gPrime = this.derivative(g, c);
		BTNode h = this.Copy(n.getRight());
		BTNode hPrime = this.derivative(h, c);

		char opORnum = ((Element) n.element()).c;

		Element plus = new Element('+', 9, "+", 'c');
		Element times = new Element('*', 8, "*", 'c');
		Element minus = new Element('-', 7, "-", 'c');
		Element onee = new Element('1', 1, "1", 'x');
		Element zeroe = new Element('0', 0, "0", 'x');

		if (opORnum == c) {
			BTNode one = new BTNode(onee, null, null, null);
			return one;
		} else if (opORnum == 'y' || opORnum == 'z' || ((Element) n.element()).IsNumb()) {
			BTNode zero = new BTNode(zeroe, null, null, null);
			return zero;
		} else if (opORnum == '+') {
			f.setLeft(gPrime);
			f.setRight(hPrime);
			return f;
		} else if (opORnum == '-') {
			f.setLeft(gPrime);
			f.setRight(hPrime);
			return f;
		} else if (opORnum == '*') {
			f.setElement(plus);
			BTNode leftnode = new BTNode(times, gPrime, h, f);
			BTNode rightnode = new BTNode(times, g, hPrime, f);
			f.setLeft(leftnode);
			f.setRight(rightnode);
			return f;
		} else if (opORnum == '/') {
			BTNode leftnode = new BTNode(minus, null, null, f);
			BTNode leftleftnode = new BTNode(times, gPrime, h, leftnode);
			BTNode letfrightnode = new BTNode(times, g, hPrime, leftnode);
			leftnode.setLeft(leftleftnode);
			leftnode.setRight(letfrightnode);

			BTNode rightnode = new BTNode(times, h, h, f);
			f.setLeft(leftnode);
			f.setRight(rightnode);

			return f;
		}

		return null;
	}

	public void simplify() {
		BTNode root = (BTNode) this.root();
		if(root !=null) {
		//	this.setRoot(this.simplify(root));
			this.simplify(root);
		}
		System.out.println("Test: simplify");
	}

	public BTNode simplify(BTNode n) {
		BTNode f = this.Copy(n);
//		if (f == null) {
//			return null;
//		}
		Element zeroe = new Element('0', 0, "0", 'x');
		Element onee = new Element('1', 1, "1", 'x');

		if (((Element) f.element()).type == 'x' 
				|| ((Element) f.element()).type == 's') {
			if(isRoot((Position) f) ) {
				setRoot(f);
			}
			//return Copy(f);
		} else {
			BTNode g = this.simplify(n.getLeft());
			BTNode h = this.simplify(n.getRight());
			
			char opORnum = ((Element) n.element()).c;

			Element gElement = ((Element) g.element());
			Element hElement = ((Element) h.element());
			if (opORnum == '+') {
				if (gElement.IsNumb() && gElement.x == 0) {
					System.out.println("Test: simplify0+");
					n.setElement(h.element());
					n.setLeft(h.getLeft());
					n.setRight(h.getRight());
				} else if (hElement.IsNumb() && hElement.x == 0) {
					System.out.println("Test: simplify+0");
					n.setElement(g.element());
					n.setLeft(g.getLeft());
					n.setRight(g.getRight());
				} else if (hElement.IsNumb() && gElement.IsNumb()) {
					System.out.println("Test: simplify num + num");
					Element sum = new Element('+', hElement.x + gElement.x, "sum", 'x');
					n.setElement(sum);
					n.setLeft(null);
					n.setRight(null);
				}
			} else if (opORnum == '-') {
				if (hElement.IsNumb() && hElement.x == 0) {
					System.out.println("Test: simplify - 0");
					n.setElement(g.element());
					n.setLeft(g.getLeft());
					n.setRight(g.getRight());
				} else if (hElement.IsNumb() && gElement.IsNumb()) {
					System.out.println("Test: simplify num - num");
					Element deduct = new Element('-', gElement.x - hElement.x, "deduct", 'x');
					n.setElement(deduct);
					n.setLeft(null);
					n.setRight(null);
				} else if (!gElement.IsChar() && !hElement.IsChar() && gElement.c == hElement.c) {
					System.out.println("Test: simplify gElement.c == hElement.c");
					n.setElement(zeroe);
					n.setLeft(null);
					n.setRight(null);
				}
			} else if (opORnum == '*') {
				if (gElement.x == 1) {
					n.setElement(h.element());
					n.setLeft(h.getLeft());
					n.setRight(h.getRight());
					System.out.println("Test: simplify1*");
				} else if (hElement.x == 1) {
					n.setElement(g.element());
					n.setLeft(g.getLeft());
					n.setRight(g.getRight());
					System.out.println("Test: simplify*1");

				} else if ((gElement.IsNumb()&& gElement.x == 0)
						|| (hElement.IsNumb()&& hElement.x == 0)) {
					System.out.println("Test: simplify *0 or 0* ");
					n.setElement(zeroe);
					n.setLeft(null);
					n.setRight(null);
				} else if (hElement.IsNumb() && gElement.IsNumb()) {
					System.out.println("Test: simplify num * num");
					Element multip = new Element('*', gElement.x * hElement.x, "multip", 'x');
					n.setElement(multip);
					n.setLeft(null);
					n.setRight(null);
				}
			} else if (opORnum == '/') {
				if (hElement.IsNumb() && hElement.x == 1) {
					System.out.println("Test: simplify/1");
					n.setElement(g.element());
					n.setLeft(g.getLeft());
					n.setRight(g.getRight());
				} else if (gElement.IsNumb() && gElement.x == 0) {
					System.out.println("Test: simplify0/");
					n.setElement(zeroe);
					n.setLeft(null);
					n.setRight(null);
				} else if (hElement.IsNumb() && hElement.x == 0) {
					System.out.println("Test: Error simplify/0");
				} else if (hElement.IsNumb() && gElement.IsNumb() && gElement.x != 0) {
					System.out.println("Test: simplify num / num");
					Element division = new Element('/', gElement.x / hElement.x, "division", 'x');
					n.setElement(division);
					n.setLeft(null);
					n.setRight(null);
				} else if (!gElement.IsChar() && !hElement.IsChar() && gElement.c == hElement.c) {
					System.out.println("Test: simplify / equal elements");
					n.setElement(onee);
					n.setLeft(null);
					n.setRight(null);
				}
			}
		}
		makeCurrent(root());
		return n;
	}

	public BTNode Copy(BTNode b) {
		if (b == null) {
			return null;
		}
		BTNode root = new BTNode();
		root.setParent(b.getParent());
		root.setElement(b.element());
		if (b.getLeft() != null) {
			root.setLeft(Copy(b.getLeft()));
		}
		if (b.getRight() != null) {
			root.setRight(Copy(b.getRight()));
		}
		return root;
	}

}
