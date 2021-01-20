/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // construct an empty deque

    /**
     * Constructor for Deque
     */
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }


    /**
     * Returns true if this Deque is empty
     *
     * @return true - if this Deque is empty
     * false - otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }


    /**
     * Returns the number of items on this Deque
     *
     * @return the number of items on this Deque
     */
    public int size() {
        return size;
    }


    /**
     * Adds the item to the front
     *
     * @param item - the item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item to be added is null");
        }
        else {
            if (size == 0) {
                first = new Node();
                first.item = item;
                first.next = null;
                first.prev = null;
                last = first;
            }
            else {
                Node oldfirst = first;
                first = new Node();
                first.item = item;
                first.next = oldfirst;
                first.prev = oldfirst.prev;
                oldfirst.prev = first;
            }
            size++;
        }
    }


    /**
     * Adds the item to the back
     *
     * @param item - the item to add
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item to be added is null");
        }
        else {
            if (size == 0) {
                addFirst(item);
            }
            else {
                Node oldlast = last;
                last = new Node();
                oldlast.next = last;
                last.prev = oldlast;
                last.item = item;
                last.next = null;
                size++;
            }
        }
    }


    /**
     * Removes and returns the item from the front
     *
     * @return the item on this Deque from the front
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Can't remove as the Deque is empty");
        }
        else {
            Item item = first.item;
            if (size == 1) {
                first = null;
                last = null;
            }
            else {
                first = first.next;
                first.prev = null;
            }
            size--;
            return item;
        }

    }


    /**
     * Removes and returns the item from the back
     *
     * @return the item on this Deque from the front
     */
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("Can't remove as the Deque is empty");
        }
        else {
            Item item = last.item;
            if (size == 1) {
                first = null;
                last = null;
            }
            else {
                last = last.prev;
                last.next = null;
            }
            size--;
            return item;
        }
    }


    /**
     * Returns an iterator over items in order from front to back
     *
     * @return an iterator over items in order from front to back
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Class required for an iterator
     * doesn't implement remove() since its optional
     */
    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return (current != null);
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not implemented in iterator");
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    /**
     * Unit tests the <code>Deque</code> data type
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Deque<Integer> queue = new Deque<Integer>();
        queue.addFirst(1);
        StdOut.println(queue.size());
        queue.removeLast();
        StdOut.println(queue.size());
        queue.addLast(5);
        queue.removeFirst();
        StdOut.println(queue.size());
        queue.addFirst(2);
        queue.addFirst(3);
        StdOut.println(queue.size());
        queue.addLast(4);
        for (int a : queue) {
            StdOut.println(a);
        }

    }

}
