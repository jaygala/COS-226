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

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class PointST<Value> {

    /**
     * bst: a red-black BST with key as <code>Point2D</code> and varying value
     */
    private RedBlackBST<Point2D, Value> bst;


    /**
     * Creates an empty red-black BST of <code>Points2D</code>
     */
    public PointST() {
        bst = new RedBlackBST<Point2D, Value>();
    }


    /**
     * Checks if the symbol table of points is empty or not
     *
     * @return true - if the symbol table is empty
     * false - if the symbol table is empty
     */
    public boolean isEmpty() {
        return bst.isEmpty();
    }


    /**
     * Number of points in the symbol table
     * Size of the symbol table
     *
     * @return the number of points/size of the symbol table
     */
    public int size() {
        return bst.size();
    }


    /**
     * Add the key, value pair in the symbol table
     * Associate the value <code>val</code> with point <code>p</code> as key
     *
     * @param p   - <code>Point2D</code> as a key in the symbol table
     * @param val - value corresponding to the key
     */
    public void put(Point2D p, Value val) {
        if (p == null || val == null)
            throw new IllegalArgumentException("Invalid parameters in put() function");
        bst.put(p, val);
    }


    /**
     * Retrieves the value correspoding to the key <code>p</code> in the symbol table
     *
     * @param p - the key to fetch the value
     * @return the value associated with point <code>p</code>
     */
    public Value get(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Invalid parameter in get() function");
        return bst.get(p);
    }


    /**
     * Does the symbol table contain point <code>p</code>
     *
     * @param p - point
     * @return true - if point <code>p</code> is present in the symbol table
     * false - if point <code>p</code> is not present in the symbol table
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Invalid parameter in contains() function");
        return bst.contains(p);
    }


    /**
     * All points in the symbol table
     *
     * @return all points in the symbol table
     */
    public Iterable<Point2D> points() {
        return bst.keys();
    }


    /**
     * All the points that are inside the rectangle <code>rect</code> or
     * on the boundary
     *
     * @param rect - a rectangle
     * @return an iteratable containing all the points in the symbol table
     * that are inside or on the boundary of the rectangle <code>rect</code>
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException("Invalid parameter in range() function");
        Stack<Point2D> stack = new Stack<Point2D>();
        for (Point2D p : points()) {
            if (rect.contains(p))
                stack.push(p);
        }
        return stack;
    }


    /**
     * Nearest neighbour of point <code>p</code> in the symbol table
     *
     * @param p - point
     * @return the nearest neighbour (point) of point <code>p</code>
     * null - if the symbol table is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException("Invalid parameter in nearest() function");
        if (isEmpty())
            return null;
        Point2D min = null;
        double dist = Double.POSITIVE_INFINITY;
        for (Point2D a : points()) {
            double thisDist = a.distanceSquaredTo(p);
            if (dist > thisDist) {
                dist = thisDist;
                min = a;
            }
        }
        return min;
    }


    /**
     * Unit testing of <code>PointST</code> data type
     *
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        PointST<Integer> pointST = new PointST<Integer>();
        StdOut.println("Is it Empty? - " + pointST.isEmpty());
        StdOut.println(
                "Adding the points (1,1), (2,4), (6,3), (5,5), (4,7), (3,1) with values 1,2,3,4,5,6 respectively");
        pointST.put(new Point2D(1, 1), 1);
        pointST.put(new Point2D(2, 4), 2);
        pointST.put(new Point2D(6, 3), 3);
        pointST.put(new Point2D(5, 5), 4);
        pointST.put(new Point2D(4, 7), 5);
        pointST.put(new Point2D(3, 1), 6);
        StdOut.println("Is it Empty? - " + pointST.isEmpty());
        StdOut.println("Size is - " + pointST.size());
        StdOut.println("Get value corresponding to (6,3): " + pointST.get(new Point2D(6, 3)));
        StdOut.println("Get value corresponding to (1,1): " + pointST.get(new Point2D(1, 1)));
        StdOut.println("Get value corresponding to (1,2): " + pointST.get(new Point2D(1, 2)));
        StdOut.println("Does it contain (1,1): " + pointST.contains(new Point2D(1, 1)));
        StdOut.println("Does it contain (2,1): " + pointST.contains(new Point2D(2, 1)));
        StdOut.println("All the points it contains are:-");
        for (Point2D p : pointST.points())
            StdOut.println(p);
        StdOut.println();
        StdOut.println("All the points contained in the rectangle marked by (1,2) and (6,5) are:-");
        for (Point2D p : pointST.range(new RectHV(1, 2, 6, 5)))
            StdOut.println(p);
        StdOut.println();
        StdOut.println("The point closest to (3,4) is: " + pointST.nearest(new Point2D(3, 4)));
    }

}
