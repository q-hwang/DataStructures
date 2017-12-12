package DataStructures;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Test Trie by cases
 */
@RunWith(Parameterized.class)
public class TestTrieCase {
    private Trie t;
    private String insert_word;
    private boolean before_add;
    private boolean after_add;
    private String remove_word;
    private boolean before_remove;
    private boolean after_remove;
    private String prefix;
    private String nearest;


    @Parameterized.Parameters
    public static List<Object[]> data() {
        Object[][] test1 = new Object[][]{
                { new String[]{"CS2112"}, " ", false, true,
                        " ", true, false, "C", "CS2112"},
                // Do not find not stored string, insert delete " "
                { new String[]{"CS2112", "CS"}, "CS", true, true,
                        "OK", false, false, "C", "CS"},
                // find nearest: exist before insert
                { new String[]{"CS2112", "COW"}, "CO", false, true,
                        "OK", false, false, "C", "CO"},
                //find nearest: not exist before insert
                { new String[]{"CS2112", "COW"}, "CS", false, true,
                        "OK", false, false, "CS", "CS"},
                //find nearest, including self
                { new String[]{"CS2110", "cs211"}, "CS", false, true,
                        "OK", false, false, "CS21", "CS2110"},
                // case sensitive
        };

        return Arrays.asList(test1);
    }



    public TestTrieCase(String[] initial,String i_word, boolean b, boolean a,
                        String re, boolean br, boolean ar, String pre,
                        String get){
        t= new Trie();
        for (String anInitial : initial) {
            t.insert(anInitial);
        }
        insert_word = i_word;
        before_add = b;
        after_add = a;
        remove_word = re;
        before_remove = br;
        after_remove = ar;
        prefix = pre;
        nearest = get;
    }

    @Test
    public void insert_remove(){
        assertTrue(t.contains(insert_word) == before_add);
        t.insert(insert_word);
        assertTrue(t.contains(insert_word) == after_add);

        assertTrue(t.contains(remove_word) == before_remove);
        t.delete(remove_word);
        assertTrue(t.contains(remove_word) == after_remove);

    }

    @After
    public void findnearest(){
        assertTrue(t.closestWordToPrefix(prefix).equals(nearest));
    }


}
