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
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class KdTreeST<Value> {

    private class Node implements Comparable<Point2D> {
        /**
         * p: the point
         * val: the value associated with the point in the symbol table
         * rect: the axis-aligned rectangle correspondinng to this node
         * lb: the left/bottom subtree
         * rt: the right/top subtree
         * hv: horizontal or vertical
         * hv = +1 for vertical and -1 for horizontal
         */
        private Point2D p;
        private Value val;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int hv;

        /**
         * Costructor for <code>Node</code>
         * Initialises the variables of this <code>Node</code>
         *
         * @param p    - point
         * @param val  - value associated with the point <code>p</code>
         * @param c    - hv value that indicates if the line associated is
         *             a vertical or horizontal line
         * @param rect - the axis-aligned rectangle associated with this node
         */
        public Node(Point2D p, Value val, int c, RectHV rect) {
            this.p = p;
            this.val = val;
            this.hv = c;
            this.rect = rect;
        }

        /**
         * Compares this Node's point to another Node's point
         *
         * @param that - point of another Node
         * @return 0 - if the points are the same
         * 1 - if this Node's point is further than <code>that</code>'s point
         * -1 - otherwise
         */
        public int compareTo(Point2D that) {
            if (p.x() == that.x() && p.y() == that.y()) return 0;
            else if (hv > 0) {
                if (p.x() > that.x()) return 1;
                return -1;
            }
            else {
                if (p.y() > that.y()) return 1;
                return -1;
            }
        }

    }

    /**
     * root: starting Node
     * size: the size of the symbol table
     */
    private Node root;
    private int size;

    /**
     * Constructor of the Kd Tree Symbol table
     */
    public KdTreeST() {
        root = null;
        size = 0;
    }


    /**
     * Checks if the symbol table is empty
     *
     * @return true: if it is empty
     * false: if it is not empty
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Number of points in the symbol table
     *
     * @return the number of points in the symbol table
     */
    public int size() {
        return size;
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
        root = put(p, val, root, -1, null);
        size++;
    }


    /**
     * Computes the rectangle for given point
     *
     * @param parent - Parent Node of point <code>p</code>
     * @param p      - point
     * @return - A Rectangle by the point <code>p</code>
     */
    private RectHV computeRect(Node parent, Point2D p) {
        RectHV rect;
        if (parent.hv > 0) {
            if (p.x() < parent.p.x())
                rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(),
                                  parent.rect.ymax());
            else rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(),
                                   parent.rect.ymax());
        }
        else {
            if (p.y() < parent.p.y())
                rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(),
                                  parent.p.y());
            else rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(),
                                   parent.rect.ymax());
        }
        return rect;
    }


    /**
     * A private method which is called recursively to put the key,value pair
     * in the right place
     *
     * @param p            - the point to put
     * @param val          - the value corresponding to the point
     * @param node         - the Node corresponding to the point
     * @param charOfParent - whether the parent is horizontally or vertically oriented
     * @param parent       - the parent Node
     * @return the node
     */
    private Node put(Point2D p, Value val, Node node, int charOfParent, Node parent) {
        if (node == null) {
            if (parent == null)
                return new Node(p, val, (-1 * charOfParent),
                                new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                                           Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
            return new Node(p, val, (-1 * charOfParent), computeRect(parent, p));
        }
        else {
            int charOfCurrent = node.hv;
            int cmp = node.compareTo(p);
            if (cmp > 0) node.lb = put(p, val, node.lb, charOfCurrent, node);
            else if (cmp < 0) node.rt = put(p, val, node.rt, charOfCurrent, node);
            else {
                node.val = val;
            }
            return node;
        }
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
        return get(p, root);
    }


    /**
     * A private method which is called recursively to reach to the Node in search
     *
     * @param p    - the point to search
     * @param node - the node
     * @return the value corresponding to the point <code>p</code>
     */
    private Value get(Point2D p, Node node) {
        if (node == null) return null;
        int cmp = node.compareTo(p);
        if (cmp > 0) return get(p, node.lb);
        else if (cmp < 0) return get(p, node.rt);
        else return node.val;
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
        return get(p) != null;
    }


    /**
     * All points in the symbol table
     *
     * @return all points in the symbol table
     */
    public Iterable<Point2D> points() {
        Queue<Node> queue = new Queue<Node>();
        Queue<Point2D> retQueue = new Queue<Point2D>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            retQueue.enqueue(node.p);
            if (node.lb != null) queue.enqueue(node.lb);
            if (node.rt != null) queue.enqueue(node.rt);
        }
        return retQueue;
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
        range(root, rect, stack);
        return stack;
    }


    /**
     * A private method that recursively traverses through the Kdtree symbol table
     * and adds the nodes that are enclosed in the rectangle into a stack
     *
     * @param node  - Node
     * @param rect  - rectangle
     * @param stack - stack to add the nodes enclosed in the rectangle
     */
    private void range(Node node, RectHV rect, Stack<Point2D> stack) {
        if (node == null) return;
        if (!node.rect.intersects(rect)) return;
        if (rect.contains(node.p)) stack.push(node.p);
        range(node.lb, rect, stack);
        range(node.rt, rect, stack);
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
        return nearest(p, root, root.p, Double.POSITIVE_INFINITY);
    }


    /**
     * A private recursive method to find the closest point to the <code>p</code>
     *
     * @param p            - point
     * @param node         - Node
     * @param closestPoint - closest point to <code>p</code> so far
     * @param dist         - dist between the point <code>p</code> and the closestpoint so far
     * @return the closest point
     */
    private Point2D nearest(Point2D p, Node node, Point2D closestPoint, double dist) {
        if (node == null) return closestPoint;
        if (dist < node.rect.distanceSquaredTo(p)) return closestPoint;
        if (dist > node.p.distanceSquaredTo(p)) {
            closestPoint = node.p;
            dist = node.p.distanceSquaredTo(p);
        }
        int cmp = node.compareTo(p);
        if (cmp > 0) {
            closestPoint = nearest(p, node.lb, closestPoint, dist);
            closestPoint = nearest(p, node.rt, closestPoint, closestPoint.distanceSquaredTo(p));
        }
        else if (cmp < 0) {
            closestPoint = nearest(p, node.rt, closestPoint, dist);
            closestPoint = nearest(p, node.lb, closestPoint, closestPoint.distanceSquaredTo(p));
        }
        return closestPoint;
    }


    /**
     * Unit testing of <code>KdTreeST</code> data type
     *
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        KdTreeST<Integer> kdTreeST = new KdTreeST<Integer>();
        StdOut.println("Is it Empty? - " + kdTreeST.isEmpty());
        StdOut.println(
                "Adding the points (2,3), (4,2), (4,5), (3,3), (1,5), (4,4) with values 1,2,3,4,5,6 respectively");
        kdTreeST.put(new Point2D(2, 3), 1);
        kdTreeST.put(new Point2D(4, 2), 2);
        kdTreeST.put(new Point2D(4, 5), 3);
        kdTreeST.put(new Point2D(3, 3), 4);
        kdTreeST.put(new Point2D(1, 5), 5);
        kdTreeST.put(new Point2D(4, 4), 6);
        StdOut.println("Is it Empty? - " + kdTreeST.isEmpty());
        StdOut.println("Size is - " + kdTreeST.size());
        StdOut.println("Get value corresponding to (3,3): " + kdTreeST.get(new Point2D(3, 3)));
        StdOut.println("Get value corresponding to (4,4): " + kdTreeST.get(new Point2D(4, 4)));
        StdOut.println("Get value corresponding to (4,9): " + kdTreeST.get(new Point2D(4, 9)));
        StdOut.println("Does it contain (1,1): " + kdTreeST.contains(new Point2D(1, 1)));
        StdOut.println("Does it contain (2,3): " + kdTreeST.contains(new Point2D(2, 3)));
        StdOut.println("All the points it contains are:-");
        for (Point2D p : kdTreeST.points())
            StdOut.println(p);
        StdOut.println();
        StdOut.println("All the points contained in the rectangle marked by (3,2) and (6,5) are:-");
        for (Point2D p : kdTreeST.range(new RectHV(3, 2, 6, 5)))
            StdOut.println(p);
        StdOut.println();
        StdOut.println("The point closest to (3,4) is: " + kdTreeST.nearest(new Point2D(3, 4)));
    }

}
