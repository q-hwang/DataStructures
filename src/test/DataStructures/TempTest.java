package DataStructures;

import java.util.Set;

public class TempTest {

    public static void main(String[] args) {
        HashTable<Integer, String> hashTable = new HashTable<>(4);
        Set<Integer> set = hashTable.keySet();
        hashTable.put(1,"a");
        hashTable.put(2,"b");
        hashTable.put(3,"c");
        System.out.println(hashTable.size());
        System.out.println(set.size());
        hashTable.clear();
        System.out.println(hashTable.size());
        System.out.println(set.size());
    }
}
