/******************************************************************************
 *  Compilation:  javac-algs4 RandomizedQueue.java
 *  Execution:    java-algs4 RandomizedQueue
 *  Dependencies: StdRandom, Iterator
 *  
 *  Description: A randomized queue is similar to a stack or queue, except that
 *  the item removed is chosen uniformly at random from items in the data
 *  structure. 
 * 
 ******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n;
    private Item[] a;

    public RandomizedQueue() {
        a = (Item[]) new Object[1];
        n = 0;
    }
    
    public boolean isEmpty() {
        return n == 0;
    }
    
    public int size() {
        return n;
    }
    
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++)
            temp[i] = a[i];
        
        a = temp;
    }
    
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.NullPointerException();
        if (n == a.length)
            resize(2 * a.length);
        a[n++] = item;
    }
    
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int idx = StdRandom.uniform(n);
        Item item = a[idx];
        a[idx] = a[n-1];
        a[n-1] = null;
        n--;
        if (n > 0 && n == a.length/4)
            resize(a.length/2);
        return item;
    }
    
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int idx = StdRandom.uniform(n);
        return a[idx];
    }
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int i = n;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            return a[--i];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
    }
}