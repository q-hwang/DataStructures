package DataStructures;

import org.junit.*;

import static util.RandomString.randomString;


public class TriePerformance {
    private Trie t;

    /**
    * Print out the time needed by each insert() and contains(), as well as the
     * length of string corresponded
    */
    @Test
    public void testTime(){
        t = new Trie();
        long before,after;
        int n = 0;
        while(n ++ < 100) {
            String value = randomString(100);
            before = System.nanoTime();
            t.insert(value);
            after = System.nanoTime();
            System.out.println(value.length() + " " + (after-before) +" " + value);
        }
        System.out.println("===================");
        while(n-- > 0){
            String value = randomString(100);
            before = System.nanoTime();
            t.contains(value);
            after = System.nanoTime();
            System.out.println(value.length()+ " " + (after-before) + " "+ value);
        }

    }

}
