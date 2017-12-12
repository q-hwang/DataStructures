package main.DataStructures;

import org.junit.Test;

import java.util.HashMap;

import static main.common.RandomString.randomString;
import static org.junit.Assert.assertTrue;

/**
 * Test HashTable randomly and compare HashTable with the standard HashMap
 */

public class TestHashTableMethods {
    private HashTable<String,String> table;
    private HashMap<String,String> s_table;

    private void put(String key, String value) {
        assertTrue(table.containsKey(key + "")
                == s_table.containsKey(key + ""));

        table.put(key, value);
        s_table.put(key, value);
        assertTrue(table.containsKey(key + "")
                == s_table.containsKey(key + ""));
        assertTrue(table.get(key + "").equals(s_table.get(key + "")));
        assertTrue(table.size() == s_table.size());
    }

    private void remove(String key) {
        table.remove(key);
        s_table.remove(key);
        assertTrue(table.containsKey(key + "")
                == s_table.containsKey(key + ""));
        if(table.get(key) != null) {
            assertTrue(table.get(key + "").equals(s_table.get(key + "")));
        }
        assertTrue(table.size() == s_table.size());
    }

    /**
     * Test put()
     * Put new key and existent key after rehashing. Compare its behavior with
     * HashMap provided by JAVA.
     */
    @Test
    public  void TestPutSimple(){
        table = new HashTable<>(1);
        s_table = new HashMap<>(1);
        String key = "AA";
        put(key, randomString(100));
        //put(key, randomString(),table,s_table);
        int n = 10000;
        while(n -- >0) {
            put("BB", randomString(100));
        }
        put(key, randomString(100));
        put(key, randomString(100));
    }

    /**
     * Test put() randomly.  Compare its behavior with HashMap provided by JAVA.
     */
    @Test
    public void TestPut(){
        table = new HashTable<>(1);
        s_table = new HashMap<>(1);
        HashTable<String,String> table = new HashTable<>(1);
        HashMap<String,String> s_table = new HashMap<>(1);
        int n =10000;
        while(n -- >0) {
            String key = randomString(100);
            String value = randomString(100);
            put(key, value);
            put(key, randomString(100));
        }
    }

    /**
     * Test remove()randomly.  Compare its behavior with HashMap provided by
     * JAVA.
     */
    @Test
    public void TestRemove(){
        table = new HashTable<>(1);
        s_table = new HashMap<>(1);
        int n =10000;
        while(n -- >0) {
            String key = randomString(100);
            String value = randomString(100);
            put(key, value);
            remove(key);
            remove(key);
        }
    }
}
