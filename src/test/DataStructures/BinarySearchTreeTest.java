package DataStructures;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static util.TestParameters.LARGE_TEST_NUM;
import static util.TestParameters.MID_TEST_NUM;
import static util.TestParameters.MIN_TEST_NUM;

/**
 * Test the correctness by reference randomly
 */
@RunWith(Parameterized.class)
public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> tree;
    private ArrayList<Integer> reference;
    private final static int MAX_ADD_SIZE = 10;
    private final static int MAX_DEL_SIZE = 10;
    private final static int MAX_VALUE = 100;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new BinarySearchTree<Integer>()},
                {new AVLTree<Integer>()}
        });
    }

    public BinarySearchTreeTest(BinarySearchTree<Integer> tree) {

        this.tree = tree;
        reference = new ArrayList<>();
    }

    @Test
    public void Test() {
        int size = (int) (Math.random() * MAX_ADD_SIZE);
        for (int i = 0; i < size; i++) {
            add();
        }
        Checker[] checkers = new Checker[] {this::add, this::delete,
                                            this::contains};
        // long time = System.nanoTime();
        int n = LARGE_TEST_NUM;
        while (n-- > 0) {
            checkers[(int)(Math.random()*3)].check();
        }
        // System.out.println(System.nanoTime() - time);
    }

    @FunctionalInterface
    interface Checker {

        void check();

    }

    private void add() {
        Integer value = (int) (Math.random() * MAX_VALUE);
        reference.add(value);
        tree.add(value);
        tree.checkInvariant(reference);
    }

    private void delete() {
        Integer value = (int) (Math.random() * MAX_VALUE);
        boolean success = reference.remove(value);
        assertTrue(tree.remove(value) == success);
        tree.checkInvariant(reference);
    }

    private void contains() {
        Integer value = (int) (Math.random() * MAX_VALUE);
        assertTrue(tree.contains(value) == reference.contains(value));
    }
}
