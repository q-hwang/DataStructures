package main.DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Basic Binary Search Tree
 * Invariant:
 * left < parent <= right
 * No Null value
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> implements Collection<T>{


    private BinaryNode<T> root;
    private int size;

    public BinarySearchTree () {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        //noinspection unchecked
        return find((T) o) != null;
    }

    @Override
    public Iterator<T> iterator() {
        return inOrderIterator();
    }

    @Override
    public Object[] toArray() {
        return toInOrderArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        //noinspection unchecked
        return (T1[]) toInOrderArray();
    }

    public boolean add (T value) {
        if (value == null) {
            throw new IllegalArgumentException("Do not accept null");
        }
        BinaryNode<T> newNode = new BinaryNode<>(value);
        size += 1;
        if (root == null) {
            root = newNode;
            return true;
        }
        // recursive implementation
        addTo(value, root);
        // iterative implementation
        // addTo(value);
        return true;
    }

    /**
     * The recursive implementation of adding
     * @param value value to be added
     * @param t the current top node
     */
    private void addTo(T value, BinaryNode<T> t) {
        if (value.compareTo(t.getValue()) < 0) {
            if (t.getLeft() == null) {
                t.setLeft(new BinaryNode<>(value));
                return;
            }
            addTo(value, t.getLeft());
        } else {
            if (t.getRight() == null) {
                t.setRight(new BinaryNode<>(value));
                return;
            }
            addTo(value, t.getRight());
        }
    }

    /**
     * The iterative implementation of adding
     * @param value value to be added
     */
    private void addTo(T value) {
        BinaryNode<T> newNode = new BinaryNode<>(value);
        BinaryNode<T> current = root;
        BinaryNode<T> parent = null;
        boolean left = true;
        while (current != null) {
            parent = current;
            left = (value.compareTo(current.getValue()) < 0);
            current = left ? current.getLeft() : current.getRight();
        }
        assert root != null;
        if (left) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    public boolean remove (T value) {
        if (value == null) {
            throw new IllegalArgumentException("Do not accept null");
        }
        BinaryNode<T> node = find(value);
        if (node == null) return false;
        size --;
        if (node.getLeft() == null || node.getRight() == null) {
            slice(node);
        } else {
            BinaryNode<T> mid = node.getRight();
            while (mid.getLeft() != null) {
                mid = mid.getLeft();
            }
            slice(mid);
            replace(node, new BinaryNode<>(mid.getValue(), node.getLeft(),
                    node.getRight()));
        }
        return true;
    }

    private BinaryNode<T> find(T value) {
        BinaryNode<T> current = root;
        while (current != null && !current.getValue().equals(value)) {
            current = value.compareTo(current.getValue()) < 0 ?
                    current.getLeft() : current.getRight();
        }
        return current;
    }

    private Object[] toArray (Iterator<T> itr) {
        Object[] result = new Object[size];
        int n = 0;
        while (itr.hasNext()) {
            result[n] = itr.next();
            n ++;
        }
        assert n == size;
        return result;
    }

    public Object[] toInOrderArray () {
        return toArray(inOrderIterator());
    }

    public Object[] toPostOrderArray () {
        return toArray(postOrderIterator());
    }

    public Object[] toPreOrderArray () {
        return toArray(preOrderIterator());
    }

    public Iterator<T> preOrderIterator () {
        return new Iterator<T>() {
            Stack<BinaryNode<T>> stack = new Stack<>();
            {
                stack.push(root);
            }

            @Override
            public boolean hasNext() {
                return root != null && !stack.isEmpty();
            }

            @Override
            public T next() {
                BinaryNode<T> next = stack.pop();
                if (next.getRight() != null) {
                    stack.push(next.getRight());
                }
                if (next.getLeft() != null){
                    stack.push(next.getLeft());
                }
                return next.getValue();
            }
        };
    }

    public Iterator<T> inOrderIterator () {
        return new Iterator<T>() {
            Queue<T> queue = queue(root);

            private Queue<T> queue(BinaryNode<T> t) {
                Queue<T> q = new LinkedList<T>();
                if (t != null && t.getValue() != null) {
                    q.addAll(queue(t.getLeft()));
                    q.add(t.getValue());
                    q.addAll(queue(t.getRight()));
                }
                return q;
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                return queue.poll();
            }
        };
    }

    public Iterator<T> postOrderIterator () {
        return new Iterator<T>() {
            Queue<T> queue = queue(root);

            private Queue<T> queue(BinaryNode<T> t) {
                Queue<T> q = new LinkedList<T>();
                if (t != null && t.getValue() != null) {
                    q.addAll(queue(t.getLeft()));
                    q.addAll(queue(t.getRight()));
                    q.add(t.getValue());
                }
                return q;
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public T next() {
                return queue.poll();
            }
        };
    }

    private void slice(BinaryNode<T> n) {
        BinaryNode<T> newChild;
        if (n.getLeft() == null) newChild = n.getRight();
        else newChild = n.getLeft();
        replace(n, newChild);
    }

    private void replace(BinaryNode<T> n, BinaryNode<T> newNode) {
        BinaryNode<T> parent = n.getParent();
        if (n == root) {
            root = newNode;
            return;
        }
        if (n == parent.getLeft()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    private void checkInvariant(BinaryNode<T> t) {
        if (t == null) {
            return;
        }
        if (t.getLeft() != null) {
            assert t.getLeft().getValue().compareTo(t.getValue()) < 0;
            checkInvariant(t.getLeft());
        }
        if (t.getRight() != null) {
            assert t.getValue().compareTo(t.getRight().getValue()) <= 0;
            checkInvariant(t.getRight());
        }
    }


    void checkInvariant(ArrayList<T> reference) {
        reference.sort(Comparable::compareTo);
        assert Arrays.equals(reference.toArray(), toInOrderArray());
        Object[] preOrderArray = toPreOrderArray();
        Object[] postOrderArray =toPostOrderArray();
        checkInvariant(root);
    }
}
