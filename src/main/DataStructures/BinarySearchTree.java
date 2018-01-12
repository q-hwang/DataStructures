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
 * left < parent < right
 * The duplicate values are stored in the same node
 * No Null value
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<T>> implements Collection<T>{

    BinaryNode<T> root;
    private int size;
    final List<Invariant<BinaryNode<T>>> invariants = new ArrayList<>();

    /**
     * Construct an empty tree
     */
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

    @Override
    public boolean add (T value) {
        addValue(value);
        return true;
    }

    /**
     * A helper method to check sanity and add the value to the tree
     * @param value the value to add
     * @return the added node
     */
    BinaryNode<T> addValue(T value) {
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
    private BinaryNode<T> addTo(T value, BinaryNode<T> t) {
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
        removeValue(node);
        return true;
    }

    /**
     * Remove the value from the tree. If the tree has more than one piece of
     * this value, the size of the node will be decreased by one. Otherwise,
     * the node will be removed
     * @param node the node corresponding to the value
     * @return the lowest affected parent
     */
    BinaryNode<T> removeValue(BinaryNode<T> node) {
        size --;
        BinaryNode<T> lowestAffectedParent;
        if (node.getNum() > 1) {
            node.decrement();
            lowestAffectedParent = node.getParent();
        } else if (node.getLeft() == null || node.getRight() == null) {
            lowestAffectedParent = node.getParent();
            slice(node);
        } else {
            BinaryNode<T> mid = node.getRight();
            while (mid.getLeft() != null) {
                mid = mid.getLeft();
            }
            lowestAffectedParent = mid.getParent();
            if (lowestAffectedParent == node) {
                lowestAffectedParent = mid;
            }
            slice(mid);
            mid.setLeft(node.getLeft());
            mid.setRight(node.getRight());
            replace(node, mid);
        }
        return lowestAffectedParent;
    }

    @Override
    public boolean contains(Object o) {
        //noinspection unchecked
        return find((T) o) != null;
    }

    /**
     * A helper method to find the node containing the value.
     * @param value the value
     * @return the binaryNode containing value if the value exists.
     *         {@code null} otherwise.
     */
    BinaryNode<T> find(T value) {
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

    /**
     * Slice the node out of the tree. The node sliced will have no children and
     * parent
     * @param n the node to slice
     */
    private void slice(BinaryNode<T> n) {
        BinaryNode<T> newChild;
        if (n.getLeft() == null) {
            newChild = n.getRight();
            n.setRight(null);
        }
        else {
            newChild = n.getLeft();
            n.setLeft(null);
        }
        replace(n, newChild);

    }

    /**
     * Replace the node {@code n}  with {@code newNode}
     * The old node will have no parent
     * @param n the node to be replaced
     * @param newNode the new node
     */
    void replace(BinaryNode<T> n, BinaryNode<T> newNode) {
        BinaryNode<T> parent = n.getParent();
        if (n == root) {
            root = newNode;
            if (root != null) {
                root.setParentNull();
            }
            return;
        }
        if (n == parent.getLeft()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        n.setParentNull();
    }

    /*

    Tree traversal iterators and toArray

     */

    private Object[] toInOrderArray() {
        return toArray(inOrderIterator());
    }

    private Object[] toPostOrderArray() {
        return toArray(postOrderIterator());
    }

    private Object[] toPreOrderArray() {
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

    private Iterator<T> preOrderIterator() {
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

    private Iterator<T> inOrderIterator() {
        return new Iterator<T>() {
            Queue<T> queue = queue(root);
            private Queue<T> queue(BinaryNode<T> t) {
                Queue<T> q = new LinkedList<>();
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

    private Iterator<T> postOrderIterator() {
        return new Iterator<T>() {
            Queue<T> queue = queue(root);

            private Queue<T> queue(BinaryNode<T> t) {
                Queue<T> q = new LinkedList<>();
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

    void checkInvariant(BinaryNode<T> t) {
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

    /**
     * Check invariants and comparing with reference
     * @param reference a list that contains the same elements as this tree
     */
    void checkInvariant(ArrayList<T> reference) {
        reference.sort(Comparable::compareTo);
        assert Arrays.equals(reference.toArray(), toInOrderArray());
        Object[] preOrderArray = toPreOrderArray();
        Object[] postOrderArray =toPostOrderArray();
        checkInvariant(root);
    }

    private StringBuilder buildTree(StringBuilder sb, BinaryNode<T> node,
                                    int depth){
        if (node == null) {
            return sb;
        }
        for (int i = 0; i < depth; i++) {
            sb.append("-");
        }
        sb.append(node.getValue().toString())
                .append(" ")
                .append(node.getNum())
                .append("\n");
        StringBuilder l = buildTree(sb, node.getLeft(), depth + 1);
        return buildTree(l, node.getRight(), depth + 1);
    }

    /**
     * Print the tree structure
     */
    void printTree() {
        System.out.println(buildTree(new StringBuilder(), root, 0));
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
