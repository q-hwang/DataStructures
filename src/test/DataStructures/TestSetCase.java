package DataStructures;

import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test MySet by cases
 */
@RunWith(Parameterized.class)
public class TestSetCase {
    MySet<String> set;

    String insert_word;
    boolean before_add;
    boolean after_add;
    String remove_word;
    boolean before_remove;
    boolean after_remove;
    int size;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { new String[]{"Ok", "a", "normal", "one"},false,  "O", false, true, "O", true, false, 4},//Test normal
                { new String[]{"Ok", "a", "normal", "normal", "one"},false,  "O", false, true, "normal", true, false, 4},//Test initial repeat String
                { new String[]{"Ok", "a", "normal", "normal", "one"},false,  "normal", true, true, "normal", true, false, 3},//Test add existent element
                { new String[]{}, true,  "", false, true, "", true, false, 0},//Test empty String
                { new String[]{}, true,  "something", false, true, "whay", false, false, 1},//remove nonexistent thing
        });
    }

    public TestSetCase(String[] initial,boolean e,String i_word, boolean b, boolean a, String re, boolean br, boolean ar, int s){
        set = new MySet<>();
        for(int i = 0; i < initial.length; i ++){
            set.add(initial[i]);
        }
        insert_word = i_word;
        before_add = b;
        after_add = a;
        remove_word = re;
        before_remove = br;
        after_remove = ar;
        size = s;
        assertTrue(set.isEmpty() == e);
    }

    /**
     * HashTable and MySet change together
     */
    @Before
    public void test_change(){
        HashTable<String,String> table = new HashTable<>(1);
        MySet<String> s = (MySet<String>) table.keySet();
        table.put("AA","BB");
        table.put("AA","CC");
        assertTrue(s.contains("AA"));
        table.remove("AA");
        assertFalse(s.contains("AA"));
    }


    @Test
    public void add(){
        assertTrue(set.contains(insert_word) == before_add);
        set.add(insert_word);
        assertTrue(set.contains(insert_word) == after_add);
    }

    @After
    public void remove(){
        assertTrue(set.contains(remove_word) == before_remove);
        set.remove(remove_word);
        assertTrue(set.contains(remove_word) == after_remove);
        System.out.println(set.size());
        assertTrue(set.size() == size);
    }


}
