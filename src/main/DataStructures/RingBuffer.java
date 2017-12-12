package DataStructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RingBuffer<T> implements BlockingQueue<T> {

    private int head;
    private int tail;
    private final int capacity;
    private Queue<T> queue;
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock r = rwLock.readLock();
    private final Lock w = rwLock.writeLock();

    public RingBuffer(int n) {
        head = 0;
        tail = 0;
        queue = new LinkedList<>();
        capacity = n;
    }

    private void addToLast(T t) {
        w.lock();
        try {
            tail += 1;
            queue.add(t);
        } finally {
            w.unlock();
        }
    }

    private T removeFirst() {
        w.lock();
        try {
            head += 1;
            return queue.remove();
        } finally {
            w.unlock();
        }
    }

    @Override
    public boolean add(T t) {
        r.lock();
        try {
            if ((tail - head) >= capacity) {
                throw new IllegalStateException("No space available");
            }
        } finally {
            r.unlock();
        }
        addToLast(t);
        return true;
    }

    @Override
    public boolean offer(T t) {
        r.lock();
        try {
            if ((tail - head) >= capacity) {
                return false;
            }
        } finally {
            r.unlock();
        }
        addToLast(t);
        return true;
    }

    @Override
    public T remove() {
        r.lock();
        try {
            if ((tail - head) <= 0) {
                throw new NoSuchElementException();
            }
        } finally {
            r.unlock();
        }
        return removeFirst();
    }

    @Override
    public T poll() {
        r.lock();
        try {
            if ((tail - head) <= 0) {
                return null;
            }
        } finally {
            r.unlock();
        }
        return removeFirst();
    }


    private T peekFirst() {
        w.lock();
        try {
            return queue.peek();
        } finally {
            w.lock();
        }
    }


    @Override
    public T element() {
        r.lock();
        try {
            if ((tail - head) <= 0) {
                throw new NoSuchElementException();
            }
        } finally {
            r.unlock();
        }
        return peekFirst();
    }

    @Override
    public T peek() {
        r.lock();
        try {
            if ((tail - head) <= 0) {
                return null;
            }
        } finally {
            r.unlock();
        }
        return peekFirst();
    }

    @Override
    public void put(T t) throws InterruptedException {
        r.lock();
        while ((tail - head) >= capacity) {
            RingBuffer.this.wait();
        }
        r.unlock();
        addToLast(t);
    }

    @Override
    public boolean offer(T t, long timeout, TimeUnit unit)
            throws InterruptedException {
        r.lock();
        try {
            while ((tail - head) >= capacity) {
                unit.timedWait(this, timeout);
                return false;
            }
        } finally {
            r.unlock();
        }
        addToLast(t);
        return true;
    }

    @Override
    public T take() throws InterruptedException {
        r.lock();
        while ((tail - head) <= 0) {
            RingBuffer.this.wait();
        }
        r.unlock();
        return removeFirst();
    }

    @Override
    public T poll(long timeout, TimeUnit unit) throws InterruptedException {
        r.lock();
        try {
            while ((tail - head) <= 0) {
                unit.timedWait(this, timeout);
                return null;
            }
        } finally {
            r.unlock();
        }
        return removeFirst();
    }

    @Override
    public int remainingCapacity() {
        r.lock();
        try {
            return capacity - (tail - head);
        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean remove(Object o) {
        r.lock();
        try {
            if ((tail - head) <= 0) {
                return false;
            }
        } finally {
            r.unlock();
        }
        w.lock();
        try {
            head += 1;
            return queue.remove(o);
        } finally {
            w.unlock();
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        r.lock();
        try {
            return queue.containsAll(c);
        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        r.lock();
        try {
            if ((tail - head) >= capacity) {
                return false;
            }
        } finally {
            r.unlock();
        }
        w.lock();
        queue.addAll(c);
        tail = head + queue.size();
        w.unlock();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        r.lock();
        try {
            if ((tail - head) < 0) {
                return false;
            }
        } finally {
            r.unlock();
        }
        w.lock();
        queue.removeAll(c);
        head = tail - queue.size();
        w.unlock();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        r.lock();
        try {
            if ((tail - head) < 0) {
                return false;
            }
        } finally {
            r.unlock();
        }
        w.lock();
        boolean changed = queue.retainAll(c);
        head = tail - queue.size();
        w.unlock();
        return changed;
    }

    @Override
    public void clear() {
        r.lock();
        try {
            if ((tail - head) < 0) {
                return;
            }
        } finally {
            r.unlock();
        }
        w.lock();
        try {
            queue.clear();
        } finally {
            w.unlock();
        }
    }

    @Override
    public int size() {
        r.lock();
        try {
           return tail - head;
        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        r.lock();
        try {
            return tail - head == 0;
        } finally {
            r.unlock();
        }
    }

    @Override
    public boolean contains(Object o) {
        r.lock();
        try {
            return queue.contains(o);
        } finally {
            r.unlock();
        }
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        r.lock();
        try {
            return queue.toArray();
        } finally {
            r.unlock();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        r.lock();
        try {
            return queue.toArray(a);
        } finally {
            r.unlock();
        }
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        w.lock();
        try {
            c.addAll(this);
            c.clear();
            int n = tail - head;
            head = tail;
            return n;
        } finally {
            w.unlock();
        }
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        w.lock();
        try {
            int n = 0;
            for(; head < tail; head ++, n++) {
                if (n >= maxElements) {
                    break;
                }
                c.add(queue.poll());
            }
            return n;
        } finally {
            w.unlock();
        }
    }
}
