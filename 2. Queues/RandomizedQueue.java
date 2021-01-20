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
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int size;

    /**
     * Constructor for <code>RandomizedQueue</code>
     */
    public RandomizedQueue() {
        s = (Item[]) new Object[2];
        size = 0;
    }


    /**
     * Returns true if this <code>RandomizedQueue</code> is empty
     *
     * @return true - if this <code>RandomizedQueue</code> is empty
     * false - otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Returns the number of items on this <code>RandomizedQueue</code>
     *
     * @return the number of items on this <code>RandomizedQueue</code>
     */
    public int size() {
        return size;
    }

    /**
     * Resizes the <code>RandomizedQueue</code> to the size <code>capacity</code>
     *
     * @param capacity new size of the <code>RandomizedQueue</code>
     */
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }


    /**
     * Adds the <code>item</code> to the <code>RandomizedQueue</code>
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item to be enqueued is null");
        }
        else {
            if (size == s.length) {
                resize(2 * s.length);
            }
            s[size++] = item;
        }
    }


    /**
     * Removes and returns any random <code>item</code> from the
     * <code>RandomizedQueue</code>
     *
     * @return any random <code>item</code> from the <code>RandomizedQueue</code>
     */
    public Item dequeue() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "Cannot dequeue as the RandomizedQueue is empty");
        }
        else {
            StdRandom.shuffle(s, 0, size);
            Item item = s[size - 1];
            s[size - 1] = null;
            size--;
            if (size > 0 && size == s.length / 4) resize(s.length / 2);
            return item;
        }
    }


    /**
     * Returns any random <code>item</code> from the <code>RandomizedQueue</code>
     *
     * @return any random <code>item</code> from the <code>RandomizedQueue</code>
     */
    public Item sample() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "Cannot sample as the RandomizedQueue is empty");
        }
        else {
            int x = StdRandom.uniform(size);
            return s[x];
        }
    }


    /**
     * Returns an independent iterator over items in random order
     *
     * @return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        StdRandom.shuffle(s, 0, size);
        return new ReverseArrayIterator();
    }

    /**
     * Class required for an iterator
     * doesn't implement remove() since its optional
     */
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i = size;

        public boolean hasNext() {
            return (i > 0);
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not implemented in iterator");
        }

        public Item next() {
            return s[--i];
        }
    }

    /**
     * Unit tests the <code>RandomizedQueue</code> data type
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 2;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        StdOut.println(queue.size());
        queue.enqueue(5);
        queue.enqueue(6);
        StdOut.println(queue.size());
        queue.dequeue();
        queue.dequeue();
        StdOut.println(queue.size());

        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        for (int a : queue) {
            StdOut.println(a);
        }
    }

}
