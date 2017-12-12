package DataStructures;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Set;
import java.util.Collection;

/**
 * Hash table
 * The key should will be kept unique in the table
 * Resize when the array size equals the number of elements
 * Collision is dealt by chaining
 * @param <K> The type of the key
 * @param <V> The type of the value
 */
public class HashTable<K, V> implements Map<K, V>{

   private MyLinkedList<K,V>[] table;
   private int size;

   /**
    * Initialize a hash table with the specified number of buckets.
    * @param initNumBuckets initial number of buckets
    */
   public HashTable(int initNumBuckets) {
      //noinspection unchecked
      table = (MyLinkedList<K, V>[]) new MyLinkedList<?, ?>[initNumBuckets];
      for (int i = 0; i < table.length; i++) {
         table[i] = new MyLinkedList<>();
      }
      size = 0;
   }

   @Override
   public int size() {
      return size;
   }

   /**
    * A helper function for hashing
    * Hash function: hashcode + SHA-1
    * @param key key
    * @return hash value
    */
   private int hash(Object key){
      try {
         int n = key.hashCode();
         ByteBuffer bytevalue = ByteBuffer.allocate(4);
         bytevalue.putInt(n);
         MessageDigest digester = MessageDigest.getInstance("SHA-1");
         digester.update(bytevalue.array());
         ByteBuffer wrapped = ByteBuffer.wrap(digester.digest());
         return Math.floorMod(wrapped.getInt(), table.length);
      }catch (NoSuchAlgorithmException e){
         return 0;
      }

   }

   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   @Override
   public boolean containsKey(Object key) {
      //noinspection SuspiciousMethodCalls
      int k = hash(key);
      return table[k].containsKey(key);
   }

   @Override
   public boolean containsValue(Object value) {
      for(int i = 0; i < table.length; i++) {
         MyLinkedList<K, V> list = table[i];
         if(list.containsValue(value)) return true;
      }
      return false;
   }

   @Override
   public V get(Object key) {
      int k = hash(key);
      return table[k].get(key);
   }

   /**
    * If the map doesn't have the key, the key-value paired is added. If the map
    * has the key, the value of it will be updated to the new value
    * @param key Key to be added
    * @param value Value to be added
    * @return the original value of key if the key is found, or null if not
    *         found.
    */
   @Override
   public V put(K key, V value) {
      int k = hash(key);
      if(containsKey(key)) {
         int s = table[k].size();
         V originalValue = table[k].remove(key);
         table[k].add(key,value);
         assert s == table[k].size();
         return  originalValue;
      }
      size += 1;
      table[k].add(key,value);
      if(size >= table.length){
         rehash();
      }
      return null;
   }

   private void rehash(){
      @SuppressWarnings("unchecked") MyLinkedList<K,V>[] old_table = table;
      //noinspection unchecked
      table = (MyLinkedList<K,V>[]) new MyLinkedList<?,?>[2*table.length];
      for(int i = 0; i < table.length; i++){
         table[i] = new MyLinkedList<>();
      }
       for (MyLinkedList<K, V> list : old_table) {
           for (Node<K, V> n = list.getHead(); n != null; n = n.getNext()) {
               int kk = hash(n.getKey());
               table[kk].add(n.getKey(), n.getValue());
           }
       }
   }

   @Override
   public V remove(Object key) {
      int k = hash(key);
      if(containsKey(key)) size--;
      return table[k].remove(key);
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> m) {
     for(K key : m.keySet()){
        put(key, m.get(key));
     }
   }

   @Override
   public void clear() {
      //noinspection unchecked
      table = (MyLinkedList<K,V>[]) new MyLinkedList<?, ?>[table.length];
      size = 0;
   }

   @Override
   public Set<K> keySet() {
       return new MySet<>(this);
   }

   MyLinkedList<K,V>[] getTable(){
      return table;
   }

    /*

     Unsupported Operations

     */

    /**
     *
     * @return
     */
    @Override
   public Collection<V> values() {
      throw new UnsupportedOperationException();
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      throw new UnsupportedOperationException();
   }

   /**
    * Get the String representation of the hash table
    * @return the number of objects in each buckets, separated by "||"
    */
   public String toString(){
      StringBuilder buckets = new StringBuilder("|| ");
      for(int i = 0; i < table.length; i ++){
         buckets.append(table[i].toStringKey()).append(" ||");
      }
      return buckets.toString();
   }

    /*

     Performance test methods

     */

   /**
    * @return The number of empty linked list in array buckets
    */
   int empty_number(){
      int empty = 0;
       for (MyLinkedList<K, V> aTable : table) {
           if (aTable.isEmpty()) empty++;
       }
      return empty;
   }

   /**
    * @return The number of elements added to a non-empty linked list
    */
   int collision_number(){
      int c = 0;
       for (MyLinkedList<K, V> aTable : table) {
           if (aTable.size() > 1) c += aTable.size() - 1;
       }
      return c;
   }

    /**
     * Calculate the c, clustering factor, to test the randomness of hashing
     * method
     * @return clustering facter
     */
    double clustering(){
        int count = 0;
        for (MyLinkedList<K, V> aTable : table) {
            count += aTable.size() * aTable.size();
        }
        if(size == 1) return 1;
        return ((double)count/size - 1) * (table.length/(size - 1));
    }

   /**
    * The max number of elements in a linked list
    * @return max collision
    */
   int max_collision(){
      int max = 0;
       for (MyLinkedList<K, V> aTable : table) {
           if (aTable.size() > max) max = aTable.size();
       }
      if(max <= 1) return 0;
      return max;
   }
}
