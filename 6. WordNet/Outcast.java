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
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordNet;


    /**
     * Constructor of Outcast
     * Takes a WordNet object
     *
     * @param wordnet - WordNet object
     */
    public Outcast(WordNet wordnet) {
        if (wordnet == null)
            throw new IllegalArgumentException("Invalid parameter in the Outcast constructor()");
        this.wordNet = wordnet;
    }


    /**
     * Returns the outcast from a given array of WordNet nouns
     *
     * @param nouns - array of WordNet nouns
     * @return outcast from the array of WordNet nouns
     */
    public String outcast(String[] nouns) {
        Integer[] dist = new Integer[nouns.length];
        int max = -1;
        String maxNoun = "";
        for (int i = 0; i < nouns.length; i++) {
            dist[i] = 0;
            for (int j = 0; j < nouns.length; j++) {
                dist[i] += wordNet.distance(nouns[i], nouns[j]);
            }
            if (dist[i] > max) {
                max = dist[i];
                maxNoun = nouns[i];
            }
        }
        return maxNoun;
    }


    /**
     * Unit testing of <code>Outcast</code> data type
     *
     * @param args - the command line arguments
     *             first args - synset file
     *             second args - hypernym file
     *             other args - outcast files
     */
    public static void main(String[] args) {
        System.out.println("Taking inputs from synset and hypernym files and making WordNet...");
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }

}
