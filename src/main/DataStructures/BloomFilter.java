package DataStructures;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 * A collection of elements of type E for which the only operation is a
 * probabilistic membership test.
 */
public class BloomFilter<E> {
   private byte[] bitArray;
   private int numHashFunctions;

   /**
    * Create a new Bloom filter with {@code elems} inside. The bit array is of
    * length 8 * numBytes. The Bloom filter uses the specified number of hash
    * functions.
    * 
    * @param elems
    *           The collection of elements to be added to this filter
    * @param numBytes
    *           The length of the byte array representing bit array
    * @param numHashFunctions
    *           The number of hash functions to be used in this filter
    */
   public BloomFilter(Collection<E> elems, int numBytes, int numHashFunctions) {
      bitArray = new byte[numBytes];
      this.numHashFunctions = numHashFunctions;
      Object[] list = elems.toArray();
      for(Object elem : list) {
         for (int i = 0; i < numHashFunctions; i++) {
            String c = ((Integer)i).toString();
            int index = hash(c,elem);
            bitArray[index /8] = (byte)(bitArray[index /8]| 1 << index % 8);
         }
      }
   }

   private int hash(String character, Object elem){
      try {
         int n = (elem.toString()+character).hashCode();
         ByteBuffer bytevalue = ByteBuffer.allocate(4);
         bytevalue.putInt(n);
         MessageDigest digester = MessageDigest.getInstance("SHA-1");
         digester.update(bytevalue.array());
         ByteBuffer wrapped = ByteBuffer.wrap(digester.digest());
         return Math.floorMod(wrapped.getInt(),bitArray.length*8);
      }catch (NoSuchAlgorithmException e){
         return 0;
      }
   }

   /**
    * Add {@code elem} to the Bloom filter.
    */
   public void insert(E elem) {

      for (int i = 0; i < numHashFunctions; i++) {
         String c = ((Integer)i).toString();
         int index = hash(c,elem);
         //System.out.println(index);
         bitArray[index /8] = (byte)(bitArray[index /8]| 1 << index % 8);
      }
   }

   /**
    * Check whether {@code elem} might be in the collection.
    * Only false positive
    */
   public boolean mightContain(E elem) {
      for (int i = 0; i < numHashFunctions; i++) {
         String c = ((Integer)i).toString();
         int index = hash(c,elem);
         if(!((bitArray[index/8] >> index % 8 & 1) == 1)) return  false;
      }
      return true;
   }

}
