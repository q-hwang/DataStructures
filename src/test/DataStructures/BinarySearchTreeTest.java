package DataStructures;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertTrue;
import static util.TestParameters.LARGE_TEST_NUM;

/**
 * Test the correctness by reference randomly
 */
public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> tree;
    private ArrayList<Integer> reference;
    private final static int MAX_ADD_SIZE = 100;
    private final static int MAX_DEL_SIZE = 10;
    private final static int MAX_VALUE = 1000;

    @Test
    public void Test() {
        reference = new ArrayList<>();
        tree = new BinarySearchTree<>();
        int size = (int) (Math.random() * MAX_ADD_SIZE);
        for (int i = 0; i < size; i++) {
            reference.add((int) (Math.random() * MAX_VALUE));
        }
        Checker[] checkers = new Checker[] {this::add, this::delete,
                                            this::contains};
        int n = LARGE_TEST_NUM;
        while (n-- > 0) {
            checkers[(int)(Math.random()*3)].check();
        }
    }

    @FunctionalInterface
    interface Checker {

        void check();

    }

    private void add() {
        int size = (int) (Math.random() * MAX_ADD_SIZE);
        Integer value;
        for (int i = 0; i < size; i++) {
            value = (int) (Math.random() * MAX_VALUE);
            reference.add(value);
            tree.add(value);
        }
        tree.checkInvariant(reference);
    }

    private void delete() {
        int deleteSize = (int) (Math.random() * MAX_DEL_SIZE);
        Integer value;
        for (int i = 0; i < deleteSize; i++) {
            value = (int) (Math.random() * MAX_VALUE);
            boolean success = reference.remove(value);
            assertTrue(tree.remove(value) == success);
        }
        tree.checkInvariant(reference);
    }

    private void contains() {
        Integer value = (int) (Math.random() * MAX_VALUE);
        assertTrue(tree.contains(value) == reference.contains(value));
    }

}
