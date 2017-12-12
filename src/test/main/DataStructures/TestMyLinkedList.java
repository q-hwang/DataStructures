package main.DataStructures;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static main.common.RandomString.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Linked List randomly
 */
@RunWith(Parameterized.class)
public class TestMyLinkedList {

    private MyLinkedList<String, String> list;
    private String key = randomString(100);
    private String value = randomString(100);

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1000][0]);
    }

    /**
     * Add randomly
     */
    @Before
    public void add(){
        list = new MyLinkedList<>();
        int n = 1000;
        int q = (int)(Math.random()*n);
        while(n-- > 0) {
            list.add(randomString(100), randomString(100));
            if(n == q ) list.add(key, value);
        }
    }

    @Test
    public void contains(){
        assertTrue(list.containsKey(key));
        assertTrue(list.containsValue(value));
    }

    /**
     * Remove when list contains the element or not
     */
    @After
    public void remove(){
        assertTrue(list.remove(key) != null);
        while(list.remove(key) != null);
        assertFalse(list.containsKey(key));
        assertTrue(list.remove(key) == null);
        assertTrue(list.get(key) == null);
    }

}
