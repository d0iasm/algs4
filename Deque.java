/******************************************************************************
 *  Compilation:  javac-algs4 Deque.java
 *  Execution:    java-algs4 Deque
 *  Dependencies: StdRandom, Iterator
 *  
 *  Description: A double-ended queue or deque is supports adding and removing 
 *  items from either the front or the back of the data structure
 * 
 ******************************************************************************/

import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;


public class Deque<Item> implements Iterable<Item> {
    private node<Item> first;
    private node<Item> last;
    private int n;
    
    private class node<Item> {
        Item item;
        node<Item> next;
        node<Item> prev;
    }
    
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }
    
    public boolean isEmpty()
    {  return n == 0;  }
    
    public int size()
    {  return n;  }
    
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.nullPointerException();
        node<Item> newFirst = new node<Item>();
        newFirst.item = item;
        newFirst.prev = null;
        newFirst.next = first;
        
        if (isEmpty())
        {  last = newFirst;  }
        else
        {  first.prev = newFirst;  }
        first = newFirst;
        n++;
    }
    
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.nullPointerException();
        node<Item> newLast = new node<Item>();
        newLast.item = item;
        newLast.prev = last;
        newLast.next = null;
        
        if (isEmpty())
        {  first = newLast;  }
        else
        {  last.next = newLast;  }
        last = newLast;
        n++;
    }
    
    public Item removeFirst() {
        if (n == 0)
            throw new java.util.noSuchElementException();
        Item firstElement = first.item;
        first = first.next;
        n--;
        
        if (isEmpty())
        {  last = null;  }
        else
        {  first.prev = null;  }
        return firstElement;
    }
    
    public Item removeLast() {
        if (n == 0)
            throw new java.util.noSuchElementException();
        Item lastElement = last.item;
        last = last.prev;
        n--;
        
        if (isEmpty())
        {  first = null;  }
        else
        {  last.next = null;  }
        return lastElement;
    }
    
    public Iterator<Item> iterator() {
        // iterator()
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private node<Item> current = first;
        public boolean hasnext()
        {  return current != null;  }
        
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasnext())
                throw new java.util.noSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String [] args) {
    
    }
}