package main.DataStructures;

/**
 * My Linked List, contains key and value in each node
 */
public  class MyLinkedList<K,V> implements MyLinkedListInterface<K,V> {
    private Node<K,V>  head;
    private int size;

    public MyLinkedList(){
        head = null;
        size = 0;
    }

    @Override
    public void add(K k, V obj){
        head = new Node<>(k,obj,head);
        size += 1;
    }

    @Override
    public V remove(Object k){
        Node<K,V> before = head;
        Node<K,V> current;
        for(current = head; current != null; current = current.getNext()){
            if(current.getKey().equals(k)){
                if(current == head) head = head.getNext();
                else before.setNext(current.getNext());

                size -= 1;
                return current.getValue();
            }
            else{
                before = current;
            }
        }
        return null;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean containsKey(Object k){
        Node current;
        for(current = head; current != null; current = current.getNext()){
            if(current.getKey().equals(k)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object obj){
        Node<K,V> current;
        for(current = head; current != null; current = current.getNext()){
            if(current.getValue().equals(obj)){
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object k){
        Node<K,V> current;
        for(current = head; current != null; current = current.getNext()){
            if(current.getKey().equals(k)){
                return current.getValue();
            }
        }
        return null;
    }

    /**
     * @return the first node in the list
     */
    Node<K, V> getHead(){
        return head;
    }

    /*

     Test and debug methods

     */

    /**
     * Print all keys in the list
     */
    String toStringKey(){
        StringBuilder m = new StringBuilder();
        Node<K,V> n;
        for(n = head; n != null; n = n.getNext()){
            m.append(n.getKey()).append(" ");
        }
        return m.toString();
    }

    /**
     * @return The array of values in the list
     */
    Object[] toValueArray(){
        Object[] array = new Object[size];
        Node<K,V> n = head;
        for(int i = 0; i < size; i++){
            array[i] = n.getValue();
            n = n.getNext();
        }
        return array;
    }

}


