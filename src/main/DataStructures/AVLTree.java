package DataStructures;

/**
 * AVL tree
 * Invariants:
 * Binary research tree invariants
 * | left subtree height - right subtree height | <= 1 --> balanced
 * @param <T>
 */
public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    /**
     * Construct an empty AVL tree
     */
    public AVLTree () {
        super();
        invariants.add(node -> {
            int lSize = heightOfTree(node.getLeft());
            int rSize = heightOfTree(node.getRight());
            return Math.abs(lSize - rSize) <= 1;
        });

    }

    @Override
    public boolean add (T value) {
        BinaryNode<T> newNode = addValue(value);
        reBalance(newNode.getParent());
        return true;
    }

    @Override
    public boolean remove (Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Do not accept null");
        }
        //noinspection unchecked
        BinaryNode<T> node = find((T) value);
        if (node == null) return false;
        reBalance(removeValue(node));
        checkInvariant(root);
        return true;
    }

    /**
     * A helper method to redolence the tree
     * @param parent the lowest unbalanced node
     */
    private void reBalance(BinaryNode<T> parent) {
        BinaryNode<T> l;
        BinaryNode<T> r;
        while(parent != null) {
            l = parent.getLeft();
            r = parent.getRight();
            int lSize = heightOfTree(l);
            int rSize = heightOfTree(r);
            if (lSize - rSize >= 2) {
                if (heightOfTree(l.getLeft()) -
                        heightOfTree(l.getRight()) >= 0) {
                    parent = rotateToRight(parent);
                } else {
                    rotateToLeft(l);
                    parent = rotateToRight(parent);
                }
            } else if (rSize - lSize >= 2) {
                if (heightOfTree(r.getRight()) -
                        heightOfTree(r.getLeft()) >= 0) {
                    parent = rotateToLeft(parent);
                } else {
                    rotateToRight(r);
                    parent = rotateToLeft(parent);
                }
            }
            checkInvariant(parent);
            parent = parent.getParent();
        }
        checkInvariant(root);
    }

    /*

    Rotation Operations

     */


    private BinaryNode<T> rotateToLeft(BinaryNode<T> node) {
        BinaryNode<T> newTop = node.getRight();
        node.setRight(null);
        replace(node, newTop);
        BinaryNode<T> temp = newTop.getLeft();
        newTop.setLeft(null);
        node.setRight(temp);
        newTop.setLeft(node);
        return newTop;
    }

    private BinaryNode<T> rotateToRight(BinaryNode<T> node) {
        BinaryNode<T> newTop = node.getLeft();
        node.setLeft(null);
        replace(node, newTop);
        BinaryNode<T> temp = newTop.getRight();
        newTop.setRight(null);
        node.setLeft(temp);
        newTop.setRight(node);
        return newTop;
    }

    private int heightOfTree(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getSize();
    }
}
