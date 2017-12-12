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

    BinaryNode(E value, BinaryNode<E> left, BinaryNode<E> right) {
        this.value = value;
        this.left = left;
        if (left != null) {
            left.parent = this;
        }
        this.right = right;
        if (right != null) {
            right.parent = this;
        }
    }

    BinaryNode (E value) {
        this.value = value;
        this.left = null;
        this.right = null;
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
        if (left != null) {
            left.parent = this;
        }
    }

    /**
     * Set the right child to new node
     * The parent of new child will be this
     * The parent of old child will be null
     * @param node
     */
    void setRight(BinaryNode<E> node) {
        if (right != null) {
            right.parent = null;
        }
        right = node;
        if (right != null) {
            right.parent = this;
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

    E getValue() {
        return value;
    }
}
