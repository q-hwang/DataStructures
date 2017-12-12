package DataStructures;

import org.junit.Test;

import static util.RandomString.randomString;


public class TestHashTablePerformance {
    HashTable<Integer, String> t = new HashTable<>(1);

    /**
     * Print out the time needed by each put() and get() as size increases. Also
     * print out the empty and collision performance.
     * The size increases from 1 to about 6500, due to the same key generated by
     * randomString().
     */
    @Test
    public void testTime(){

        long before,after;
        int n =10000;
        while(n -- >0) {
            String value = randomString(100);

            before = System.nanoTime();
            t.put((int) (Math.random() * 10000), value);
            after = System.nanoTime();
            System.out.print(t.size() +" "+ t.getTable().length + " " +
                    (after-before) + " ");

            before = System.nanoTime();
            t.get((int) (Math.random() * 10000));
            after = System.nanoTime();
            System.out.println(" " + (after-before) + " " + t.empty_number() +
                    " " + t.collision_number() + " " + t.max_collision() + " "+
                    t.clustering());
        }

    }

}