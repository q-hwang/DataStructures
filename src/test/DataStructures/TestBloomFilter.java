package DataStructures;


import org.junit.Test;

import static util.RandomString.randomString;
import static org.junit.Assert.assertTrue;

/**
 * Test BloomFilter
 */

public class TestBloomFilter {
    private MySet<String> set;
    private BloomFilter<String> filter;
    private final int insert_num = 100000;
    private final static int numBytes = 2000000;
    private final static int hash_num = 20;

    /**
     * Test the fail possibility
     */
   @Test
    public void Test(){
       filter = new BloomFilter<>(new MySet<>(), numBytes,hash_num);
       set = new MySet<>();
       insert();
       int false_count = 0;
       int n = 0;
       while(n++ < 10000){
           //Test false negative
           String random = randomString(10);
           if(!filter.mightContain(random)) {
               assertTrue(random,!set.contains(random));
           }

           //Test false positive
           random = randomString(10);
           if(filter.mightContain(random) && !set.contains(random)) {
               false_count ++;
           }
       }
       System.out.println("insert " + insert_num + " words, numBytes = "
               + numBytes + ", and use " + hash_num + " hash functions: "+
               " False positive possibility = " + false_count + " in " + n);
    }

    private void insert() {
        int n = insert_num;
        while (n-- > 0) {
            String random = randomString(10);
            filter.insert(random);
            set.add(random);
        }
    }

}
