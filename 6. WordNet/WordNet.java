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

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    /**
     * G: Digraph
     * idToNouns: Matches ID to the respective noun
     * nounsToIds: Matches a noun with the respective IDs
     * sca: shortest common ancestor
     */
    private Digraph G;
    private RedBlackBST<Integer, String> idToNouns;
    private RedBlackBST<String, Queue<Integer>> nounToIds;
    private ShortestCommonAncestor sca;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Invalid parameters in the constructor of WordNet");
        In synsetFile = new In(synsets);
        In hypernymsFile = new In(hypernyms);
        idToNouns = new RedBlackBST<Integer, String>();
        nounToIds = new RedBlackBST<String, Queue<Integer>>();
        // reading from the synset file and populating idToNouns and nounToIds
        while (synsetFile.hasNextLine()) {
            String line = synsetFile.readLine();
            String[] fields = line.split(",");
            String[] nouns = fields[1].split(" ");
            for (String i : nouns) {
                Queue<Integer> queue = nounToIds.get(i);
                if (queue == null) {
                    queue = new Queue<Integer>();
                }
                queue.enqueue(Integer.valueOf(fields[0]));
                nounToIds.put(i, queue);
            }
            idToNouns.put(Integer.valueOf(fields[0]), fields[1]);
        }
        // reading hypernym file and making digraph
        G = new Digraph(idToNouns.size());
        while (hypernymsFile.hasNextLine()) {
            String line = hypernymsFile.readLine();
            String[] fields = line.split(",");
            for (int i = 1; i < fields.length; i++) {
                G.addEdge(Integer.parseInt(fields[0]), Integer.parseInt(fields[i]));
            }
        }
        sca = new ShortestCommonAncestor(G);
    }


    /**
     * All WordNet nouns
     *
     * @return an iterable of all wordnet nouns
     */
    public Iterable<String> nouns() {
        return nounToIds.keys();
    }


    /**
     * Checks if a <code>word</code> (String) is a WordNet noun?
     *
     * @param word - String to check
     * @return true - if <code>word</code> is a WordNet noun
     * false - if <code>word</code> is not a WordNet noun
     */
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException("Invalid parameter in isNoun()");
        return nounToIds.contains(word);
    }


    /**
     * Shortest common ancestor of two nouns in the WordNet digraph
     *
     * @param noun1 - first noun
     * @param noun2 - second noun
     * @return shortest common ancestor of noun1 and noun2
     */
    public String sca(String noun1, String noun2) {
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException("Nouns are not a WordNet noun - in sca()");
        Queue<Integer> q1 = nounToIds.get(noun1);
        Queue<Integer> q2 = nounToIds.get(noun2);
        int ancestor = sca.ancestorSubset(q1, q2);
        return idToNouns.get(ancestor);
    }


    /**
     * Distance between two nouns in the WordNet digraph
     *
     * @param noun1 - first noun
     * @param noun2 - second noun
     * @return distance between noun1 and noun2
     */
    public int distance(String noun1, String noun2) {
        if (!isNoun(noun1) || !isNoun(noun2))
            throw new IllegalArgumentException("Nouns are not a WordNet noun - in distance()");
        Queue<Integer> q1 = nounToIds.get(noun1);
        Queue<Integer> q2 = nounToIds.get(noun2);
        int dist = sca.lengthSubset(q1, q2);
        return dist;
    }


    /**
     * Unit testing of <code>WordNet</code> data type
     *
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Taking inputs from files - synsets.txt and hypernyms.txt ...");
        WordNet wordNet = new WordNet("synsets.txt", "hypernyms.txt");
        StdOut.println("Is 'police' a WordNet noun:   " + wordNet.isNoun("police"));
        StdOut.println("Is 'dfhdfhjh' a WordNet noun:   " + wordNet.isNoun("dfhdfhjh"));
        StdOut.println(
                "The nouns corresponding to the ID 13745 are:   " + wordNet.idToNouns.get(13745));
        StdOut.println("The IDs corresponding to the word 'President' are:   " + wordNet.nounToIds
                .get("President"));
        StdOut.println("The shortest common ancestor for 'cow' and 'horse' are:   " +
                               wordNet.sca("cow", "horse"));
        StdOut.println("The distance between 'cow' and 'horse' is:   " +
                               wordNet.distance("cow", "horse"));
    }

}
