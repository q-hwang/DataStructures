package DataStructures;

/**
 * Created by HQ on 9/19/2017.
 */
interface MyLinkedListInterface<K,V>{

    /**
     * Add a key-value pair into the linked list
     * @param k key
     * @param v value
     */
    void add(K k, V v);

    /**
     * Remove the key-value pair from the linked list
     * @param k key
     * @return the value paired with the key
     */
    V remove(K k);

    /**
     * @return the number of nodes in the linked list
     */
    int size();

    /**
     * @return true if there is only a null head node
     */
    boolean isEmpty();

    /**
     * @return true if the lined list contains the key
     */
    boolean containsKey(Object k);

    /**
     * @return true if the lined list contains the value
     */
    boolean containsValue(Object obj);

    /**
     * Get the value paired with key
     * @param k the key
     * @return the first value paired with the key, or null if not found
     */
    V get(Object k);

}

