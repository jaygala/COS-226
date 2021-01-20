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
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    /**
     * tiles: representation of the board
     * size: size of the board
     * ham: Hamming distance - number of tiles out of place
     * man: Manhattan distance - sum of Manhattan distances between tiles and goal
     * zeroRow: row number in which the zero/empty block is located
     * zeroCol: column number in which the zero/empty block is located
     */
    private int[][] tiles;
    private int size;
    private int ham;
    private int man;
    private int zeroRow;
    private int zeroCol;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    /**
     * Creates a board from an n-by-n array of tiles
     * Finds the location of zero/empty block
     * Computes ham and man distance
     *
     * @param tiles: representation of the board
     */
    public Board(int[][] tiles) {
        if (tiles == null)
            throw new IllegalArgumentException("Invalid parameter in Board constructor");
        this.size = tiles.length;
        this.ham = 0;
        this.man = 0;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tileAt(i, j) == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
                // else if the tile is not at its true location:
                else if (tileAt(i, j) != (i * size + j + 1)) {
                    ham++;
                    // trueI is the row number of the true location of the tile
                    // trueJ is the column number of the true location of the tile
                    int trueI = (tileAt(i, j) - 1) / size;
                    int trueJ = (tileAt(i, j) - 1) % size;
                    man += Math.abs(trueI - i);
                    man += Math.abs(trueJ - j);
                }


            }
        }
    }


    /**
     * string representation of this board
     *
     * @return String in the required format to display this board
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(size));
        sb.append('\n');
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                sb.append(String.format("%2d ", tileAt(row, col)));
            }
            sb.append('\n');
        }
        return sb.toString();
    }


    /**
     * tile at (row, col) or 0 if blank
     *
     * @param row required row number
     * @param col required column number
     * @return the number at the given tile location
     */
    public int tileAt(int row, int col) {
        if ((row < 0) || (row > size - 1) || (col < 0) || (col > size - 1))
            throw new IllegalArgumentException("Parameters out of range in tileAt() function");

        return tiles[row][col];
    }


    /**
     * board size n
     *
     * @return the size of the Board
     */
    public int size() {
        return size;
    }


    /**
     * Hamming distance - number of tiles out of place
     *
     * @return the hamming distance
     */
    public int hamming() {
        return ham;
    }


    /**
     * Total Manhattan distance - sum of Manhattan distances between tiles and goal
     *
     * @return the total Manhattan distance
     */
    public int manhattan() {
        return man;
    }


    /**
     * Is this board the goal board?
     *
     * @return true if the board is the goal board (true board)
     * false if the board is not the goal board yet
     */
    public boolean isGoal() {
        if (ham == 0 && man == 0)
            return true;
        return false;
    }


    /**
     * Checks whether this board equals <code>y</code>
     *
     * @param y Another Board to compare this Board with
     * @return true if this Board is equal to <code>y</code>
     */
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        return Arrays.deepEquals(tiles, ((Board) y).tiles);
    }


    /**
     * All neighbouring boards of this Board
     * Using Stack for the iterable
     * Can be 2,3 or 4 neighbours
     *
     * @return stack(as an iterable) with all the neighbours of the Board
     */
    public Iterable<Board> neighbors() {
        int[][][] nei = new int[4][size][size];
        nei[0] = swap(zeroRow, zeroCol, zeroRow - 1, zeroCol);
        nei[1] = swap(zeroRow, zeroCol, zeroRow + 1, zeroCol);
        nei[2] = swap(zeroRow, zeroCol, zeroRow, zeroCol - 1);
        nei[3] = swap(zeroRow, zeroCol, zeroRow, zeroCol + 1);

        Stack<Board> stack = new Stack<Board>();
        for (int i = 0; i < 4; i++) {
            if (nei[i] != null) {
                stack.push(new Board(nei[i]));
            }
        }
        return stack;
    }

    /**
     * Swaps the tile at <code>row, col</code> to new location <code>newRow, newCol</code>
     *
     * @param row    - the row number of the tile's original position
     * @param col    - the column number of the tile's original position
     * @param newRow - the row number of the tile's new position
     * @param newCol - the column number of the tile's new position
     * @return a double dimensional array indicating the positions of each tiles representing the new board
     */
    private int[][] swap(int row, int col, int newRow, int newCol) {
        if ((newRow < 0) || (newRow > size - 1) || (newCol < 0) || (newCol > size - 1))
            return null;
        int[][] copy = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                copy[i][j] = tileAt(i, j);
            }
        }
        copy[row][col] = tileAt(newRow, newCol);
        copy[newRow][newCol] = 0;
        return copy;
    }


    /**
     * Checks if the board is solvable
     *
     * @return true if the board is solvable
     * false if the board is unsolvable
     */
    public boolean isSolvable() {
        int[] arr = new int[size * size - 1];
        int k = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) > 0)
                    arr[k++] = tileAt(i, j);
            }
        }
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] < arr[j])
                    count++;
            }
        }
        if (size % 2 != 0)
            return (count % 2 == 0);
        else
            return ((count + zeroRow) % 2 != 0);
    }

    // unit testing (required)

    /**
     * Unit testing of the <code>Board</code> data type from file inputted
     * <p>
     * // * @param args the command line arguments
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
        StdOut.println(b1.toString());
        StdOut.println("Size of the board is: " + b1.size());
        StdOut.println("Is this board the goal: " + b1.isGoal());
        StdOut.println("Is this board solvable: " + b1.isSolvable());
        StdOut.println("Hamming distance of the board is: " + b1.hamming());
        StdOut.println("Manhattan distance of the board is " + b1.manhattan());
        Stack<Board> s1 = (Stack<Board>) b1.neighbors();
        StdOut.println("The neighbours of this Board are:- ");
        for (Board i : s1)
            StdOut.println(i);
    }

}
