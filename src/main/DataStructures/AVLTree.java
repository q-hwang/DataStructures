package DataStructures;

import java.util.Arrays;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    public AVLTree () {
        super();
        /*
        invariants.add(node -> {
            int lSize = heightOfTree(node.getLeft());
            int rSize = heightOfTree(node.getRight());
            return Math.abs(lSize - rSize) <= 1;
        });
        */
    }

    public boolean add (T value) {
        BinaryNode<T> newNode = addValue(value);
        BinaryNode<T> parent = newNode.getParent();
        reBalance(parent);
        //checkInvariant(root);
        return true;
    }

    public boolean remove (T value) {
        if (value == null) {
            throw new IllegalArgumentException("Do not accept null");
        }
        //noinspection unchecked
        BinaryNode<T> node = find((T) value);
        if (node == null) return false;

        BinaryNode<T> parent = node.getParent();
        removeNode(node);
        reBalance(parent);
        return true;
    }

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
                        heightOfTree(l.getRight()) >= 1) {
                    parent = rotateToRight(parent);
                } else {
                    rotateToLeft(l);
                    parent = rotateToRight(parent);
                }
            } else if (rSize - lSize >= 2) {
                if (heightOfTree(r.getRight()) -
                        heightOfTree(r.getLeft()) >= 1) {
                    parent = rotateToLeft(parent);
                } else {
                    rotateToRight(r);
                    parent = rotateToLeft(parent);
                }
            }
            parent = parent.getParent();
        }
    }

    private int heightOfTree(BinaryNode<T> node) {
        if (node == null) {
            return 0;
        }
        return node.getSize();
    }

    private BinaryNode<T> rotateToLeft(BinaryNode<T> node) {
        checkInvariant(node);
        BinaryNode<T> newTop = node.getRight();
        node.setRight(null);
        replace(node, newTop);
        BinaryNode<T> temp = newTop.getLeft();
        newTop.setLeft(null);
        node.setRight(temp);
        newTop.setLeft(node);
        checkInvariant(temp);
        checkInvariant(node);
        checkInvariant(newTop);
        checkInvariant(root);
        return newTop;
    }

    private BinaryNode<T> rotateToRight(BinaryNode<T> node) {
        checkInvariant(node);
        BinaryNode<T> newTop = node.getLeft();
        node.setLeft(null);
        replace(node, newTop);
        BinaryNode<T> temp = newTop.getRight();
        newTop.setRight(null);
        node.setLeft(temp);
        newTop.setRight(node);
        checkInvariant(newTop);
        checkInvariant(root);
        return newTop;
    }
}
