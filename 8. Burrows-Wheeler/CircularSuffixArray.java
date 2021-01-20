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

public class CircularSuffixArray {

    /**
     * s: word (String)
     * csaSorted: sorted array of Circular Suffixes
     */
    private String s;
    private CircularSuffix[] csaSorted;

    private class CircularSuffix implements Comparable<CircularSuffix> {

        /**
         * string: String/word associated with CircularSuffix
         * index: index of the String/word
         */
        private String string;
        private int index;

        public CircularSuffix(String string, int index) {
            if (string == null || index < 0 || index > string.length() - 1)
                throw new IllegalArgumentException("Invalid parameter in CircularSuffix() constructor");
            this.string = string;
            this.index = index;
        }

        public int compareTo(CircularSuffix that) {
            for (int i = 0; i < string.length(); i++) {
                int compare = string.charAt((index + i) % string.length()) - that.string
                        .charAt((that.index + i) % string.length());
                if (compare != 0)
                    return compare;
            }
            return 0;
        }
    }


    /**
     * Constructor for Circular Suffix Array
     * Initializes CircularSuffix
     *
     * @param s - the string inputted
     */
    public CircularSuffixArray(String s) {
        if (s == null)
            throw new IllegalArgumentException("Invalid parameter in CircularSuffixArray() constructor");
        this.s = s;
        csaSorted = new CircularSuffix[length()];
        for (int i = 0; i < length(); i++) {
            csaSorted[i] = new CircularSuffix(s, i);
        }
        Arrays.sort(csaSorted);
    }


    /**
     * Returns length of the string s
     *
     * @return - length of s
     */
    public int length() {
        return s.length();
    }


    /**
     * Returns index of the ith sorted suffix
     *
     * @param i - indicates which sorted suffix's index is to be returned
     * @return - index of the ith sorted suffix
     */
    public int index(int i) {
        if (i < 0 || i > length() - 1)
            throw new IllegalArgumentException("Invalid parameter in  index()");
        return csaSorted[i].index;
    }


    /**
     * Returns the last character of the ith sorted suffix
     *
     * @param i - indicates which sorted suffix's last character is to be returned
     * @return - the last character of the string in the ith sorted suffix
     */
    public char getLastCharAt(int i) {
        if (i < 0 || i > length() - 1)
            throw new IllegalArgumentException("Invalid parameter in  getLastCharAt()");
        if (csaSorted[i].index == 0) {
            return csaSorted[i].string.charAt(length() - 1);
        }
        return csaSorted[i].string.charAt(csaSorted[i].index - 1);
    }


    /**
     * Unit testing of CircularSuffixArray
     *
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        String string = "ABRACADABRA!";
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(string);
        StdOut.println("The string is: " + string);
        StdOut.println("The length of string is: " + circularSuffixArray.length());
        for (int i = 0; i < circularSuffixArray.length(); i++) {
            StdOut.println(circularSuffixArray.csaSorted[i].string + " - index[" + i + "] is: "
                                   + circularSuffixArray.index(i));
        }
    }

}
