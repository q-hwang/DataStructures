package DataStructures;

/**
 * The pair node
 */
class Node<K,V>{
    private K key;
    private V value;
    private Node<K,V> next;

    Node(K node_key,V node_value, Node<K,V> next_node){
        key = node_key;
        value = node_value;
        next = next_node;
    }

    Node<K,V> getNext(){
        return next;
    }

    K getKey(){
        return key;
    }

    V getValue(){
        return value;
    }

    void setNext(Node<K,V> n){
        next = n;
    }

}
