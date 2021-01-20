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
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    /**
     * moves: number of moves to the goal Board
     * node: a search node
     */
    private int moves;
    private SearchNode node;

    // find a solution to the initial board (using the A* algorithm)

    /**
     * Solver Constructor
     * Finds a solution to the initial board (using the A* algorithm)
     *
     * @param initial initial <code>Board</code>
     */
    public Solver(Board initial) {
        if ((initial == null) || !(initial.isSolvable())) {
            throw new IllegalArgumentException(
                    "Invalid parameter or the Board is not solvable in the Solver constructor");
        }

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        moves = 0;
        node = new SearchNode(initial, 0, null);
        pq.insert(node);
        // A* algorithm:
        while (!node.b.isGoal()) {
            node = pq.delMin();
            for (Board i : node.b.neighbors()) {
                if (node.prev == null || !i.equals(node.prev.b)) {
                    SearchNode toInsert = new SearchNode(i, node.moves + 1, node);
                    pq.insert(toInsert);
                }
            }
            moves = node.moves;
        }
    }


    /**
     * Minimum number of moves to solve the initial <code>Board</code>
     *
     * @return the minimum number of moves to solve the initial <code>Board</code>
     */
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution

    /**
     * Sequence of boards in a shortest solution
     * Using Stack as the iterable
     *
     * @return stack (as an iterable) that contains the sequence of the boards to the shortest solution
     */
    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        SearchNode localNode = node;
        while (localNode != null) {
            stack.push(localNode.b);
            localNode = localNode.prev;
        }
        return stack;
    }


    private class SearchNode implements Comparable<SearchNode> {
        /**
         * b - the Board corresponding to this search node
         * moves - the number of moves used to reach this node
         * prev - the previous <code>SearchNode</code>
         * priority - the priority used to compare with other <code>SearchNode</code>
         */
        private Board b;
        private int moves;
        private SearchNode prev;
        private int priority;

        /**
         * SearchNode constructor
         * Initialises the variables defined above
         *
         * @param b     - the Board corresponding to this search node
         * @param moves - the number of moves used to reach this node
         * @param prev  - the previous <code>SearchNode</code>
         */
        private SearchNode(Board b, int moves, SearchNode prev) {
            this.b = b;
            this.moves = moves;
            this.prev = prev;
            this.priority = moves + b.manhattan();
        }

        /**
         * Compares this search node with another search node
         *
         * @param n2 other <code>SearchNode</code>
         * @return 1 if this node has a higher priority than <code>n2</code>
         * -1 if this node has a lower priority than <code>n2</code>
         * 0 otherwise
         */
        public int compareTo(SearchNode n2) {
            if (n2 == null)
                throw new IllegalArgumentException(
                        "Invalid parameter in compareTo() in SearchNode");
            if (priority > n2.priority)
                return 1;
            else if (priority == n2.priority)
                return b.manhattan() - n2.b.manhattan();
            return 0;
        }
    }

    // test client (see below)

    /**
     * Unit testing of the <code>Solver</code> data type from file inputted
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int size = in.readInt();
        int[][] a = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                a[i][j] = in.readInt();
            }
        }
        Board b1 = new Board(a);
        System.out.println("The Board is:-");
        System.out.println(b1.toString());
        Solver solver = new Solver(b1);
        System.out.println("Minimum number of moves = " + solver.moves());
        Stack<Board> s1 = (Stack<Board>) solver.solution();
        System.out.println("The step-by-step solution is:-");
        for (Board i : s1)
            StdOut.println(i);
    }

}
