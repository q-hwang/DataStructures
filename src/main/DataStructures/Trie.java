package DataStructures;


/** A mutable collection of strings. */
public class Trie {

   private TrieNode root;
   /** Create an empty trie. */

   public Trie() {
      root = new TrieNode();
   }

   /** Add {@code elem} to the collection. */
   public void insert(String elem) {
       TrieNode current = root;
       for(int i = 0; i< elem.length(); i++){
          String n = elem.substring(i,i+1);
          TrieNode child = current.getChild(n);
          if(child != null) current = child;
          else{
              TrieNode newChild = new TrieNode(n,false,
                      new HashTable<>(1),current);
              current.getChildren().put(n,newChild);
              current = newChild;
          }
      }
       current.set(true);
   }

   /** Remove {@code elem} from the collection, if it is there. */
   public void delete(String elem) {
       TrieNode current = root;
       for(int i = 0; i< elem.length(); i++){
           String n = elem.substring(i,i+1);
           TrieNode child = current.getChild(n);
           if(child != null) current = child;
           else{
               return;
           }
       }
       current.set(false);
   }

   /** Return true if this trie contains {@code elem}, false otherwise. */
    public boolean contains(String elem) {
        TrieNode current = root;
        for(int i = 0; i< elem.length(); i++){
            String n = elem.substring(i,i+1);
            TrieNode child = current.getChild(n);
            if(child != null) current = child;
            else{
               return false;
            }
        }
        return current.stored();
    }

    /** 
     * Return a word contained in the trie of minimal length
     * with {@code prefix}. If no such word exists, return null.
     */
    public String closestWordToPrefix(String prefix) {
        TrieNode current = root;
        for(int i = 0; i< prefix.length(); i++){
            String n = prefix.substring(i,i+1);
            TrieNode child = current.getChild(n);
            if(child != null) current = child;
            else{
                return null;
            }
        }
        TrieNode node = findNearest(current);
        return find_string(node);
    }

    private TrieNode findNearest(TrieNode n){
        MyLinkedList<String,TrieNode> row = new MyLinkedList<>();
        row.add(n.getChar(),n);
        while(!row.isEmpty()){
            MyLinkedList<String,TrieNode> new_row = new MyLinkedList<>();
            Object[] r = shuffle(row);
            for(Object node : r){
                if(((TrieNode)node).stored()) return (TrieNode)node;
                Object[] s = ((TrieNode)node).getChildren().keySet().toArray();
                for (Object value : s) {
                    new_row.add((String) value,
                            ((TrieNode) node).getChild((String) value));
                }
            }
            row = new_row;
        }
        return null;
    }

    private Object[] shuffle( MyLinkedList<String,TrieNode> row){
        Object[] array = row.toValueArray();
        for(int i = 0; i < array.length; i++){
            int random =(int)(Math.random()*(array.length-i)) + i;
            Object temp = array[i];
            array[i] = array[random];
            array[random] = temp;
        }
        return array;
    }

    private String find_string(TrieNode n){
        if(n.geParent() == null) return "";
        else return find_string(n.geParent()) + n.getChar();
    }
}