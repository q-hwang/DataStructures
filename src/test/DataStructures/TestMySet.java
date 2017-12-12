package DataStructures;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static util.RandomString.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test MySet randomly
 */
@RunWith(Parameterized.class)
public class TestMySet {
    MySet<String> set = new MySet<>();
    String value = randomString(100);
    LinkedList<String> list = new LinkedList<>();

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1000][0]);
    }

    /**
     * Add when he element is in the set or not
     */
    @Before
    public void add(){
        int n = 1000;
        int q = (int)(Math.random()*n);
        while(n-- > 0) {
            String v = randomString(100);
            set.add(v);
            list.add(v);
            if(n == q ){
                set.add(value);
                list.add(value);
            }
        }
        int size = set.size();
        set.add(value);
        assertTrue(size == set.size());
    }

    @Test
    public void contains(){
        String v = randomString(100);
        assertTrue(set.contains(v) == list.contains(v));
    }

    /**
     * Remove when the element is in the set or not
     */
    @After
    public void remove(){
        assertTrue(set.remove(value));
        int size = set.size();
        assertFalse(set.contains(value));
        assertFalse(set.remove(value));
        assertTrue(size == set.size());

    }


}
