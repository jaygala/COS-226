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

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BinarySearchDeluxe {


    /**
     * Returns the index of the first key in the sorted array a[] that is equal
     * to the search key, or -1 if no such key.
     *
     * @param a          sorted array
     * @param key        search key
     * @param comparator comparator used
     * @return the index of the first key in <code>a</code> if present
     * -1 if key not present
     */
    public static <Key> int firstIndexOf(Key[] a, Key key,
                                         Comparator<Key> comparator) {
        if ((a == null) || (key == null) || (comparator == null)) {
            throw new IllegalArgumentException("Invalid parameters in firstIndexOf()");
        }
        int lo = 0;
        int hi = a.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            int compare = comparator.compare(key, a[mid]);
            if (compare <= 0) hi = mid;
            else lo = mid + 1;
        }
        if (comparator.compare(key, a[lo]) == 0) {
            return lo;
        }
        return -1;
    }


    /**
     * Returns the index of the last key in the sorted array a[] that is equal
     * to the search key, or -1 if no such key.
     *
     * @param a          sorted array
     * @param key        search key
     * @param comparator comparator used
     * @return the index of the last key in <code>a</code> if present
     * -1 if key not present
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if ((a == null) || (key == null) || (comparator == null)) {
            throw new IllegalArgumentException("Invalid parameters in lastIndexOf()");
        }
        int lo = 0;
        int hi = a.length - 1;
        int ans = -1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int compare = comparator.compare(key, a[mid]);
            if (compare < 0) hi = mid - 1;
            else if (compare > 0) lo = mid + 1;
            else {
                ans = mid;
                lo = mid + 1;
            }
        }
        return ans;
    }

    /**
     * Unit testing for <code>BinarySearchDeluxe</code> data type
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Term[] t = new Term[4];
        t[0] = new Term("catiee", 0);
        t[1] = new Term("dogy", 1);
        t[2] = new Term("doggoe", 2);
        t[3] = new Term("doggy", 3);
        // String[] a = { "A", "A", "G", "G", "G", "G" };
        Arrays.sort(t);
        for (int i = 0; i < t.length; i++) {
            StdOut.println(t[i]);
        }
        int findex = BinarySearchDeluxe.firstIndexOf(t, new Term("do", 0), Term.byPrefixOrder(2));
        StdOut.println(findex);
        int lindex = BinarySearchDeluxe.lastIndexOf(t, new Term("do", 0), Term.byPrefixOrder(2));
        StdOut.println(lindex);
    }
}
