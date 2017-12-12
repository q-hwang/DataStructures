package DataStructures;

/**
 * The node in trie
 */
class TrieNode {
    private String c;
    private boolean stored;
    private TrieNode parent;
    private HashTable<String, TrieNode> children;

    TrieNode(){
        c = "";
        stored = false;
        children = new HashTable<>(1);
        parent = null;
    }

    TrieNode(String charactor, boolean is_stored,
             HashTable<String,TrieNode> children, TrieNode p){
        c = charactor;
        stored = is_stored;
        this.children =children;
        parent = p;
    }

    String getChar(){
        return c;
    }

    TrieNode geParent(){
        return parent;
    }

    HashTable<String,TrieNode> getChildren(){
        return children;
    }

    boolean stored(){
        return  stored;
    }

    /**
     * Set the status of the node: if the string is stored.
     */
    void set(boolean status){
        stored = status;
    }

    TrieNode getChild(String charactor){
        return children.get(charactor);
    }
}
