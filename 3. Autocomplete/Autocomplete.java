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

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {

    /**
     * terms: an array of <code>Terms</code>
     */
    private Term[] terms;

    /**
     * Constructor of <code>Autocomplete</code>
     * Initializes the data structure from the given array of terms.
     *
     * @param terms an array of <code>Terms</code>
     */
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("Invalid parameter in Autocomplete constructor");
        }
        this.terms = terms;
        Arrays.sort(this.terms);
    }


    /**
     * Returns all terms that start with the given prefix, in descending order
     * of weight.
     *
     * @param prefix the prefix that the terms should start with
     * @return an array of <code>Terms</code> in descending order of weight
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Invalid parameter in allMatches()");
        }
        Term temp = new Term(prefix, 0);
        Comparator<Term> comparatorK = Term.byPrefixOrder(prefix.length());
        int first = BinarySearchDeluxe.firstIndexOf(terms, temp, comparatorK);
        int last = BinarySearchDeluxe.lastIndexOf(terms, temp, comparatorK);
        Term[] ans = new Term[last - first + 1];
        for (int i = 0; i < last - first + 1; i++) {
            ans[i] = terms[first + i];
        }
        Arrays.sort(ans, Term.byReverseWeightOrder());
        return ans;
    }


    /**
     * Returns the number of terms that start with the given prefix.
     *
     * @param prefix the prefix that the terms should start with
     * @return the number of terms that start with given prefix
     */
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        else {
            Term temp = new Term(prefix, 1);
            Comparator<Term> comparatorK = Term.byPrefixOrder(prefix.length());
            int first = BinarySearchDeluxe.firstIndexOf(terms, temp, comparatorK);
            int last = BinarySearchDeluxe.lastIndexOf(terms, temp, comparatorK);
            return last - first + 1;
        }
    }

    /**
     * Unit testing of <code>Autocomplete</code> data type from a file inputted
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}
