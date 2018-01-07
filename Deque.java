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
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<Item> newFirst = new Node<>();
        newFirst.item = item;
        newFirst.prev = null;
        newFirst.next = first;

        if (isEmpty()) {
            last = newFirst;
        } else {
            first.prev = newFirst;
        }
        first = newFirst;
        n++;
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        Node<Item> newLast = new Node<>();
        newLast.item = item;
        newLast.prev = last;
        newLast.next = null;

        if (isEmpty()) {
            first = newLast;
        } else {
            last.next = newLast;
        }
        last = newLast;
        n++;
    }

    public Item removeFirst() {
        if (n == 0)
            throw new NoSuchElementException();
        Item firstElement = first.item;
        first = first.next;
        n--;

        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return firstElement;
    }

    public Item removeLast() {
        if (n == 0)
            throw new NoSuchElementException();
        Item lastElement = last.item;
        last = last.prev;
        n--;

        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return lastElement;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}