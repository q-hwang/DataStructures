package DataStructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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

    protected BinaryNode<T> root;
    private int size;
    protected final List<Invariant<BinaryNode<T>>> invariants = new ArrayList<>();

    public BinarySearchTree () {
        root = null;
        size = 0;
        invariants.add(node -> {
            BinaryNode<T> left = node.getLeft();
            BinaryNode<T> right = node.getRight();
            return (left == null ||
                            left.getValue().compareTo(node.getValue()) < 0) &&
                    (right == null ||
                            node.getValue().compareTo(right.getValue()) < 0);
        });
        invariants.add(node -> node.getParent() != null || node == root);
        invariants.add(node -> node.getNum() >= 1);
    }

    /*

    Interface implementations

     */

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public boolean add (T value) {
        addValue(value);
        return true;
    }

    protected BinaryNode<T> addValue (T value) {
        if (value == null) {
            throw new IllegalArgumentException("Do not accept null");
        }
        size += 1;
        if (root == null) {
            root = new BinaryNode<>(value);
            return root;
        }
        return addTo(value, root);
    }

    /**
     * The recursive implementation of adding
     * @param value value to be added
     * @param t the current top node
     * @return the added node.
     */
    protected BinaryNode<T> addTo(T value, BinaryNode<T> t) {
        if (value.compareTo(t.getValue()) < 0) {
            if (t.getLeft() == null) {
                BinaryNode<T> newNode = new BinaryNode<>(value);
                t.setLeft(newNode);
                return newNode;
            }
            return addTo(value, t.getLeft());
        } else if (value.compareTo(t.getValue()) > 0) {
            if (t.getRight() == null) {
                BinaryNode<T> newNode = new BinaryNode<>(value);
                t.setRight(newNode);
                return newNode;
            }
            return addTo(value, t.getRight());
        } else {
            t.increment();
            return t;
        }
    }

    /**
     * The iterative implementation of adding
     * @param value value to be added
     * @return the added node
     */
    protected BinaryNode<T> addTo(T value) {
        BinaryNode<T> newNode = new BinaryNode<>(value);
        BinaryNode<T> current = root;
        BinaryNode<T> parent = null;
        boolean left = true;
        while (current != null) {
            parent = current;
            if (value.compareTo(current.getValue()) == 0) {
                current.increment();
                return current;
            }
            left = (value.compareTo(current.getValue()) < 0);
            current = left ? current.getLeft() : current.getRight();
        }
        assert root != null;
        if (left) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        return newNode;
    }

    @Override
    public boolean remove (Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Do not accept null");
        }
        //noinspection unchecked
        BinaryNode<T> node = find((T) value);
        if (node == null) return false;
        removeNode(node);
        return true;
    }

    protected void removeNode(BinaryNode<T> node) {
        size --;
        if (node.getNum() > 1) {
            node.decrement();
        } else if (node.getLeft() == null || node.getRight() == null) {
            slice(node);
        } else {
            BinaryNode<T> mid = node.getRight();
            while (mid.getLeft() != null) {
                mid = mid.getLeft();
            }
            slice(mid);
            replace(node, new BinaryNode<>(mid.getValue(), node.getLeft(),
                    node.getRight(), mid.getNum()));
        }
    }

    @Override
    public boolean contains(Object o) {
        //noinspection unchecked
        return find((T) o) != null;
    }

    /**
     * A helper method to find the element T
     * @param value value
     * @return the binaryNode containing value if the value exists.
     *         {@code null} otherwise.
     */
    protected BinaryNode<T> find(T value) {
        BinaryNode<T> current = root;
        while (current != null && !current.getValue().equals(value)) {
            current = value.compareTo(current.getValue()) < 0 ?
                    current.getLeft() : current.getRight();
        }
        return current;
    }

    /**
     * @return inorder iterator
     */
    @Override
    public Iterator<T> iterator() {
        return inOrderIterator();
    }

    /**
     * @return sorted array
     */
    @Override
    public Object[] toArray() {
        return toInOrderArray();
    }

    /**
     * @return sorted array
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        //noinspection unchecked
        return (T1[]) toInOrderArray();
    }

    /*

    Main tree operations

    */

    protected void slice(BinaryNode<T> n) {
        BinaryNode<T> newChild;
        if (n.getLeft() == null) newChild = n.getRight();
        else newChild = n.getLeft();
        replace(n, newChild);
    }

    protected void replace(BinaryNode<T> n, BinaryNode<T> newNode) {
        BinaryNode<T> parent = n.getParent();
        if (n == root) {
            root = newNode;
            root.setParentNull();
            return;
        }
        if (n == parent.getLeft()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
    }

    /*

    Tree traversal iterators and toArray

     */

    public Object[] toInOrderArray () {
        return toArray(inOrderIterator());
    }

    public Object[] toPostOrderArray () {
        return toArray(postOrderIterator());
    }

    public Object[] toPreOrderArray () {
        return toArray(preOrderIterator());
    }


    /**
     * A helper method to print array in the order of provided by iterator
     * @param itr iterator
     * @return array of values
     */
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

    public Iterator<T> preOrderIterator () {
        return new Iterator<T>() {
            Stack<BinaryNode<T>> stack = new Stack<>();
            int num = 0;
            T value;
            {
                stack.push(root);
            }

            @Override
            public boolean hasNext() {
                return (root != null && !stack.isEmpty()) || num != 0;
            }

            @Override
            public T next() {
                if (num != 0) {
                    num --;
                    return value;
                }
                BinaryNode<T> next = stack.pop();
                if (next.getRight() != null) {
                    stack.push(next.getRight());
                }
                if (next.getLeft() != null){
                    stack.push(next.getLeft());
                }
                value = next.getValue();
                num = next.getNum() - 1;
                return value;
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
                    int num = t.getNum();
                    T value = t.getValue();
                    for (int i = 0; i < num; i ++) {
                        q.add(value);
                    }
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
                    int num = t.getNum();
                    T value = t.getValue();
                    for (int i = 0; i < num; i ++) {
                        q.add(value);
                    }
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

    /*

    Check Invariant

     */

    protected void checkInvariant(BinaryNode<T> t) {
        if (t == null) {
            return;
        }
        invariants.forEach(invariant -> invariant.check(t));
        if (t.getLeft() != null) {
            checkInvariant(t.getLeft());
        }
        if (t.getRight() != null) {
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

    /*

    Unsupported Operations

     */

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
}
