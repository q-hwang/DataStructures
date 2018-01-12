package DataStructures;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Heap<E extends Comparable<E>> extends AbstractQueue<E> {

    private List<E> array;
    private int n;

    public Heap(int nChildren){
        array = new LinkedList<>();
        n = nChildren;
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.size() == 0;
    }

    private void moveUp(int i) {
        assert i < size() && i >= 0;
        int current = i;
        int next;
        while (current > 0){
            next = (current - 1) / n;
            if(array.get(current).compareTo(array.get(next)) < 0) {
                swap(current, next);
                current = next;
            } else {
                break;
            }
        }
    }

    private void moveDown(int i) {
        assert i < size() && i >= 0;
        int current = i;
        int next;
        int min;
        while (current > 0){
            min = current;
            for (int j = 1; j <= n; j ++) {
                next = current*n + j;
                if (array.get(min).compareTo(array.get(next)) > 0) {
                    min = next;
                }
            }
            if (min != current) {
                swap(current, min);
                current = min;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        E temp = array.get(i);
        array.set(i, array.get(j));
        array.set(j, temp);
    }

    @Override
    public boolean offer(E e) {
        array.add(e);
        moveUp(size() - 1);
        return false;
    }

    @Override
    public E poll() {
        if (size() <= 0) {
            return null;
        }
        E first = array.get(0);
        array.set(0, array.remove(size() - 1));
        moveDown(0);
        return first;
    }

    @Override
    public E peek() {
        if (size() <= 0) {
            return null;
        }
        return array.get(0);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public E next() {
                return array.get(i++);
            }
        };
    }
}
