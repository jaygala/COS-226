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

import java.util.Comparator;

public class Term implements Comparable<Term> {

    /**
     * query: word or phrase associated with Term
     * weight: a long integer indicating the weight of the Term
     */
    private String query;
    private long weight;


    /**
     * Constructor for Class <code>Term</code>
     * Initializes a term with the given query string and weight
     *
     * @param query  a word or phrase
     * @param weight non negative long integer
     */
    public Term(String query, long weight) {
        if ((query == null) || weight < 0) {
            throw new IllegalArgumentException("Invalid Parameters in Term() constructor");
        }
        this.setQuery(query);
        this.setWeight(weight);
    }


    /**
     * A class to implement comparing based on the descending
     * order of the weights of the Terms
     */
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term o1, Term o2) {
            return Long.compare(o2.getWeight(), o1.getWeight());
        }
    }

    /**
     * Compares the two terms in descending order by weight
     *
     * @return <code>ReverseWeightOrder</code> Comparator that compares two terms
     */
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    /**
     * Class to implement comparing 2 <code>Terms</code> in lexicographic order
     * but based on first r characters of each query
     */
    private static class PrefixOrder implements Comparator<Term> {
        // r: the number of characters used for comparing
        private int r;

        /**
         * Constructor of <code>PrefixOrder</code>
         *
         * @param r the number of characters used for comparing
         */
        public PrefixOrder(int r) {
            this.r = r;
        }

        /**
         * Finding the minimum of three integers
         *
         * @param a the first integer
         * @param b the second integer
         * @param c the third integer
         * @return the minimum integer
         */
        private int findMin(int a, int b, int c) {
            if (b < a) {
                return Math.min(c, b);
            }
            else {
                return Math.min(c, a);
            }
        }

        /**
         * Compares two <code>Terms</code> in lexicographic order but based on
         * first r characters of each query.
         *
         * @param o1 the first <code>Term</code>
         * @param o2 the second <code>Term</code>
         * @return a positive integer - if <code>o1</code> > <code>o2</code>
         * a negative integer - if <code>o1</code> < <code>o2</code>
         * 0 - if <code>o1</code> = <code>o2</code>
         */
        public int compare(Term o1, Term o2) {
            int minLen = findMin(o1.getQuery().length(), o2.getQuery().length(), r);
            for (int i = 0; i < minLen; i++) {
                if (o1.getQuery().charAt(i) > o2.getQuery().charAt(i)) return 1;
                else if (o1.getQuery().charAt(i) < o2.getQuery().charAt(i)) return -1;
            }
            if (minLen == r) {
                return 0;
            }
            else {
                return Integer.compare(o1.getQuery().length(), o2.getQuery().length());
            }
        }
    }


    /**
     * Compares the two <code>Terms</code> in lexicographic order but based on
     * first r characters of each query.
     *
     * @param r the number of characters used for comparing
     * @return Comparator based on Prefix Order
     */
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Invalid value in byPrefixOrder()");
        }
        return new PrefixOrder(r);
    }


    /**
     * Compares the two terms in lexicographic order by query.
     *
     * @param that <code>Term</code> to compare this term with
     * @return an integer according to lexicographic order of queries of the Term
     */
    public int compareTo(Term that) {
        return query.compareTo(that.getQuery());
    }


    /**
     * Returns a string representation of this term in the following format:
     * the weight, followed by a tab, followed by the query.
     *
     * @return String in the above format
     */
    public String toString() {
        return Long.toString(weight) + '\t' + query;
    }

    /**
     * Returns the <code>weight</code> of the <code>Term</code>
     *
     * @return the <code>weight</code> of the <code>Term</code>
     */
    public long getWeight() {
        return weight;
    }

    /**
     * Sets the <code>weight</code> according to the parameter <code>weight</code>
     *
     * @param weight the <code>weight</code> of the <code>Term</code> to set
     */
    public void setWeight(long weight) {
        this.weight = weight;
    }

    /**
     * Returns the <code>query</code> of the <code>Term</code>
     *
     * @return the <code>query</code> of the <code>Term</code>
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the <code>query</code> according to the parameter <code>query</code>
     *
     * @param query the <code>query</code> of the <code>Term</code> to set
     */
    public void setQuery(String query) {
        this.query = query;
    }

    // unit testing (required)

    /**
     * Unit testing for <code>Term</code> data type
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Term t1 = new Term("do", 2);
        Term t2 = new Term("dogcatcher", 1);
        StdOut.println(t1.toString());
        StdOut.println(t2.toString());
        StdOut.println(t1.compareTo(t2));
        Comparator<Term> comparator = Term.byReverseWeightOrder();
        StdOut.println(comparator.compare(t1, t2));
        Comparator<Term> comparator2 = Term.byPrefixOrder(3);
        StdOut.println(comparator2.compare(t1, t2));
    }


}
