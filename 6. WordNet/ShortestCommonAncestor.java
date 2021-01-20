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

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {

    /**
     * G: Digraph
     * root: root vertex (vertex numbers are indicated as integers)
     */
    private Digraph G;
    private int root;


    /**
     * Constructor
     * Takes a rooted DAG as argument (checks if the input Digraph is a rooted DAG)
     *
     * @param G: Digraph
     */
    public ShortestCommonAncestor(Digraph G) {
        if (G == null)
            throw new IllegalArgumentException("Invalid Parameter in the constructor");
        this.G = new Digraph(G);
        DirectedCycle directedCycle = new DirectedCycle(G);
        if (directedCycle.hasCycle())
            throw new IllegalArgumentException("Input Digraph is cyclic (it needs to be a DAG)");
        int zeroOut = 0;    // all the vertices that have zero out degree
        for (int y = 0; y < G.V(); y++) {
            if (G.outdegree(y) == 0) {
                zeroOut++;
                root = y;
                if (zeroOut > 1)
                    throw new IllegalArgumentException("Input Digraph is not rooted");
            }
        }
    }


    /**
     * Length of shortest ancestral path between two vertices
     *
     * @param v - first vertex
     * @param w - second vertex
     * @return length os the shortest ancestral path between v and w
     */
    public int length(int v, int w) {
        int length = 0;
        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(G, w);
        int commonAncestor = ancestor(v, w);
        length += bv.distTo(commonAncestor) + bw.distTo(commonAncestor);
        return length;
    }


    /**
     * Shortest common ancestor of two vertices
     *
     * @param v - first vertex
     * @param w - second vertex
     * @return shortest common ancestor of vertices v and w
     */
    public int ancestor(int v, int w) {
        BreadthFirstDirectedPaths bv = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths bw = new BreadthFirstDirectedPaths(G, w);
        boolean[] passed = new boolean[G.V()];
        for (int i : bv.pathTo(root)) {
            passed[i] = true;
        }
        for (int j : bw.pathTo(root)) {
            if (passed[j])
                return j;
        }
        return root;
    }


    /**
     * Length of shortest ancestral path of two vertex subsets
     *
     * @param subsetA - first vertex subset
     * @param subsetB - second vertex subset
     * @return length of shortest ancestral path of subsetA and subsetB
     */
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        DeluxeBFS bA = new DeluxeBFS(G, subsetA);
        DeluxeBFS bB = new DeluxeBFS(G, subsetB);
        int minDist = Integer.MAX_VALUE;
        for (int i : bA.getMarked()) {
            if (bB.hasPathTo(i)) {
                if (minDist > bB.distTo(i) + bA.distTo(i)) {
                    minDist = bB.distTo(i) + bA.distTo(i);
                }
            }
        }
        return minDist;
    }


    /**
     * Shortest common ancestor of two vertex subsets
     *
     * @param subsetA - first vertex subset
     * @param subsetB - second vertex subset
     * @return shortest common ancestor of subsetA and subsetB
     */
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        DeluxeBFS bA = new DeluxeBFS(G, subsetA);
        DeluxeBFS bB = new DeluxeBFS(G, subsetB);
        int minDist = Integer.MAX_VALUE;
        int ancestor = Integer.MAX_VALUE;
        for (int i : bA.getMarked()) {
            if (bB.hasPathTo(i)) {
                if (minDist > bB.distTo(i) + bA.distTo(i)) {
                    minDist = bB.distTo(i);
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }


    /**
     * Unit testing of <code>ShortestCommonAncestor</code> data type
     *
     * @param args - the command line arguments
     *             the first argument (args[0]): is the file containing the digraph
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}

