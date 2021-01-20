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

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Testing of RandomizedQueue from an input file
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        try {
            while (true) {
                queue.enqueue(StdIn.readString());
            }
        }
        catch (java.util.NoSuchElementException e) {
            int i = 0;
            for (String a : queue) {
                if (i < k) {
                    StdOut.println(a);
                    i++;
                }
                else break;
            }
        }

    }
}
