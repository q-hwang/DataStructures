package main.DataStructures;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test the correctness by reference and randomized test
 */
@RunWith(Parameterized.class)
public class BinarySearchTreeTest {
    private BinarySearchTree<Integer> tree;
    private ArrayList<Integer> reference;
    private final static int MAX_ADD_SIZE = 100;
    private final static int MAX_DEL_SIZE = 10;
    private final static int MAX_VALUE = 1000;


    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1000][0]);
    }

    @Before
    public void setUp() {
        reference = new ArrayList<>();
        int size = (int) (Math.random() * MAX_ADD_SIZE);
        for (int i = 0; i < size; i++) {
            reference.add((int) (Math.random() * MAX_VALUE));
        }
        tree = new BinarySearchTree<>();
        reference.forEach(tree::add);
        tree.checkInvariant(reference);
    }

    @Test
    public void delete(){
        ArrayList<Integer> del = new ArrayList<>();
        int deleteSize = (int) (Math.random() * MAX_DEL_SIZE);
        for (int i = 0; i < deleteSize; i++) {
            del.add((int) (Math.random() * MAX_VALUE));
        }
        for(Integer m : del) {
            boolean success = reference.remove(m);
            assertTrue(tree.remove(m) == success);
        }
        tree.checkInvariant(reference);
    }

}
