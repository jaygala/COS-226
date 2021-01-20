/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class BurrowsWheeler {

    /**
     * Applies Burrows-Wheeler transform
     * Reads from standard input and writes to standard output
     */
    public static void transform() {
        // Taking input
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        BinaryStdOut.flush();
        char[] lastCharsSB = new char[csa.length()];
        for (int i = 0; i < csa.length(); i++) {
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                lastCharsSB[i] = csa.getLastCharAt(i);
            }
            else {
                lastCharsSB[i] = csa.getLastCharAt(i);
            }
        }
        for (int i = 0; i < lastCharsSB.length; i++) {
            BinaryStdOut.write(lastCharsSB[i]);
        }
        BinaryStdOut.close();
    }


    /**
     * Applies Burrows-Wheeler inverse transform
     * Reads from standard input and writes to standard output
     */
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString();
        char[] a = t.toCharArray();
        int R = 256;
        int n = a.length;
        // Key-indexed counting algorithm
        int[] count = new int[R + 1];
        int[] aux = new int[n];
        int[] next = new int[n];
        for (int i = 0; i < n; i++)
            count[a[i] + 1]++;
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        for (int i = 0; i < n; i++) {
            aux[count[a[i]]] = a[i];
            next[count[a[i]]] = i;
            count[a[i]]++;
        }
        StringBuilder finalString = new StringBuilder();
        int current = first;
        for (int i = 0; i < n; i++) {
            finalString.append((char) aux[current]);
            current = next[current];
        }
        System.out.println(finalString.toString());
    }


    /**
     * Implements Burrows-Wheeler transform and inverse transform
     * if args[0] is "-", applies Burrows-Wheeler transform
     * if args[0] is "+", applies Burrows-Wheeler inverse transform
     *
     * @param args - the command line arguments
     *             first argument has to be "-" or "+"
     */
    public static void main(String[] args) {
        if (args[0].equals("-"))
            transform();
        else if (args[0].equals("+"))
            inverseTransform();
    }

}
