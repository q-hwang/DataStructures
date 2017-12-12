package DataStructures;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.RandomString.randomString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Trie randomly
 */
@RunWith(Parameterized.class)
public class TestTrieMethods {
    Trie t;
    String value = randomString(100);
    ArrayList<String> list;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[1000][0]);
    }

    @Before
    public void insert() {
        t = new Trie();
        list = new ArrayList<>();
        int n = 1000;
        int q = (int) (Math.random() * 100);
        while (n-- > 0) {
            String v = randomString(100);
            t.insert(v);
            list.add(v);
            if(n == q ){
                t.insert(value);
                list.add(value);
            }
        }
    }

    @Test
    public void contains(){
        String v = randomString(2);
        assertTrue("\""+v+"\""+list.contains(v),t.contains(v)
                == list.contains(v));
    }

    //@After
    public void delete(){
        assertTrue(t.contains(value));
        t.delete(value);
        assertFalse(t.contains(value));
    }

}
