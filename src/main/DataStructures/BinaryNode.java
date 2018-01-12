package DataStructures;

/**
 * BinaryNode class
 * The parent is maintained automatically inside class
 * The parent of root will be null by default
 */
class BinaryNode<E> {

    private E value;
    private BinaryNode<E> left;
    private BinaryNode<E> right;
    private BinaryNode<E> parent = null;
    /**
     * The height of this node
     */
    private int size;
    /**
     * The number of same values in this node
     * (duplicated values are stored in one node)
     */
    private int num = 1;

    /**
     * Construct a node with no children and only containing one value
     * @param value the value
     */
    BinaryNode (E value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.size = 1;
    }

    /**
     * Construct a node with full information
     * @param value the value
     * @param left left child
     * @param right right child
     * @param num the number of same value stored
     */
    BinaryNode(E value, BinaryNode<E> left, BinaryNode<E> right, int num) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.num = num;
        update();
    }

    /**
     * Set the left child to new node
     * The parent of new child will be this
     * The parent of old child will be null
     * @param node new child
     */
    void setLeft(BinaryNode<E> node) {
        if (left != null) {
            left.parent = null;
        }
        left = node;
        update();
    }

    /**
     * Set the right child to new node
     * The parent of new child will be this
     * The parent of old child will be null
     * @param node the new right node
     */
    void setRight(BinaryNode<E> node) {
        if (right != null) {
            right.parent = null;
        }
        right = node;
        update();
    }

    void setParentNull() {
        parent = null;
    }

    void increment() {
        num ++;
    }

    void decrement() {
        num --;
    }

    /**
     * Update parent relationship ans heights of nodes
     */
    private void update() {
        if (left != null) {
            left.parent = this;
        }
        if (right != null) {
            right.parent = this;
        }
        updateSize();
    }

    /**
     * Update heights of nodes recursively to the root
     */
    private void updateSize() {
        int l = 0;
        int r = 0;
        if (left != null) {
            l = left.getSize();
        }
        if (right != null) {
            r = right.getSize();
        }
        size = Math.max(l, r) + 1;
        if (parent != null) {
            parent.updateSize();
        }
    }

    BinaryNode<E> getLeft() {
        return left;
    }

    BinaryNode<E> getRight() {
        return right;
    }

    BinaryNode<E> getParent() {
        return parent;
    }

    int getNum() {
        return num;
    }

    E getValue() {
        return value;
    }

    int getSize() {
        return size;
    }
}
