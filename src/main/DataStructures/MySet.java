package DataStructures;

import java.util.Set;
import java.util.Iterator;
import java.util.Collection;

/**
 * The set is implemented by the HashTable.
 */
public class MySet<E> implements Set<E> {

    private HashTable<E,?> table;

    /**
     * The set will be the keySet of the table
     * @param table The HashTable that changes simultaneously with set.
     */
    MySet(HashTable<E,?> table){
        this.table = table;
    }

    public MySet(){
        table = new HashTable<>(1);
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public boolean isEmpty() {
        return table.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return table.containsKey(o);
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        MyLinkedList<E,?>[] list = table.getTable();
        Object[] array = new Object[table.size()];
        int array_index = 0;
        for (MyLinkedList<E, ?> l : list) {
            Node<E, ?> n = l.getHead();
            for (; n != null; n = n.getNext(), array_index++) {
                array[array_index] = n.getKey();
            }
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        if(!table.containsKey(e)) {
            table.put(e,null);
            return true;
        }
        else return false;
    }

    @Override
    public boolean remove(Object o) {
        if(table.containsKey(o)) {
            table.remove(o);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean changed = false;
        @SuppressWarnings("unchecked") E[] array = (E[])c.toArray();
        for(int i = 0; i < c.size(); i++){
            if(add(array[i])) changed = true;
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        @SuppressWarnings("unchecked") E[] array = (E[])c.toArray();
        for(int i = 0; i < c.size(); i++){
            if(remove(array[i])) changed = true;
        }
        return changed;
    }

    @Override
    public void clear() {
        table = new HashTable<E,Object>(1);
    }

}
