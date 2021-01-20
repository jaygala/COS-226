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

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    /**
     * picture: the input picture
     * w: the width of the picture
     * h: the height of the picture
     * pq: minimum priority queue (with Integer index and keys are double)
     */
    private Picture picture;
    private int w;
    private int h;
    private IndexMinPQ<Double> pq;

    /**
     * Constructor of SeamCarver
     * Reads the input picture
     * Initialises <code>w</code>, <code>h</code>, and <code>pq</code>
     *
     * @param picture - the input picture
     */
    public SeamCarver(Picture picture) {
        if (picture == null)
            throw new IllegalArgumentException("Invalid parameter in SeamCarver constructor()");
        this.picture = new Picture(picture);
        this.w = picture.width();
        this.h = picture.height();
        // the size is (w*h)+2 because there is an additional node on the
        // top/left (depends on vertical seam or horizontal seam) of the picture
        // and an additional node on the bottom/right of the picture (imagine like the shape of octahedron)
        this.pq = new IndexMinPQ<Double>(w * h + 2);
    }


    /**
     * Returns the current picture
     *
     * @return the current picture
     */
    public Picture picture() {
        return picture;
    }


    /**
     * Returns the width of the current picture
     *
     * @return the width of the current picture
     */
    public int width() {
        return w;
    }


    /**
     * Returns the height of the current picture
     *
     * @return the height of the current picture
     */
    public int height() {
        return h;
    }


    /**
     * Returns the energy of the pixel at column x and row y
     *
     * @param x - column value of the pixel
     * @param y - row value of the pixel
     * @return energy of the pixel at x,y
     */
    public double energy(int x, int y) {
        if (x >= w || y >= h || x < 0 || y < 0)
            throw new IllegalArgumentException("Invalid parameters in energy()");
        double xdelta = deltaX(x, y);
        double ydelta = deltaY(x, y);
        return Math.sqrt(xdelta + ydelta);
    }


    /**
     * Computes the entire energy matrix of the picture
     *
     * @return the energy value of each pixel in the matrix
     */
    private double[][] computeEnergyMatrix() {
        double[][] energy = new double[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                energy[i][j] = energy(i, j);
            }
        }
        return energy;
    }

    /**
     * Computes the Delta X value for the energy calculation
     *
     * @param x - column value of the pixel
     * @param y - row value of the pixel
     * @return calculates the delta X value of the particular pixel
     */
    private double deltaX(int x, int y) {
        if (x >= w || y >= h || x < 0 || y < 0)
            throw new IllegalArgumentException("Invalid parameters in deltaX()");
        int xleft, xright;
        if (x == 0) {
            xleft = w - 1;
            xright = x + 1;
        }
        else if (x == w - 1) {
            xleft = x - 1;
            xright = 0;
        }
        else {
            xleft = x - 1;
            xright = x + 1;
        }
        int colourleft = picture.getRGB(xleft, y);
        int colourright = picture.getRGB(xright, y);
        return calcDelta(colourleft, colourright);
    }

    /**
     * Computes the Delta Y value for the energy calculation
     *
     * @param x - column value of the pixel
     * @param y - row value of the pixel
     * @return calculates the delta Y value of the particular pixel
     */
    private double deltaY(int x, int y) {
        if (x >= w || y >= h || x < 0 || y < 0)
            throw new IllegalArgumentException("Invalid parameters in deltaY()");
        int ytop, ybottom;
        if (y == 0) {
            ytop = h - 1;
            ybottom = y + 1;
        }
        else if (y == h - 1) {
            ytop = y - 1;
            ybottom = 0;
        }
        else {
            ytop = y - 1;
            ybottom = y + 1;
        }
        int colourleft = picture.getRGB(x, ytop);
        int colourright = picture.getRGB(x, ybottom);
        return calcDelta(colourleft, colourright);
    }

    /**
     * Helper function used to compute the delta by finding R^2, G^2, B^2
     *
     * @param colourleft  - left/top neighbour
     * @param colourright - right/bottom neighbour
     * @return the delta value given the left/top neighbour and right/bottom neighbour
     */
    private double calcDelta(int colourleft, int colourright) {
        // 255, 65280, 16711680 when converted to binary give us the ..11111.. needed
        int blueLeft = colourleft & 255;
        int greenLeft = (colourleft & 65280) >> 8;
        int redLeft = (colourleft & 16711680) >> 16;
        int blueRight = colourright & 255;
        int greenRight = (colourright & 65280) >> 8;
        int redRight = (colourright & 16711680) >> 16;
        double delta = Math.pow(blueLeft - blueRight, 2) + Math.pow(greenLeft - greenRight, 2)
                + Math.pow(redLeft - redRight, 2);
        return delta;
    }

    /**
     * Converts the column and row of the pixel into an integer value
     * for a single dimensional representation of picture pixel values
     *
     * @param x - column value of the pixel
     * @param y - row value of the pixel
     * @return returns a single integer corresponding to the position of (x,y) in the picture
     */
    private int encode(int x, int y) {
        if (x >= w || y >= h || x < 0 || y < 0)
            throw new IllegalArgumentException("Invalid parameters in encode()");
        return y * w + x + 1;
    }

    /**
     * Converts the integer to its respective column and row of the picture pixel
     *
     * @param i - integral value of a pixel
     * @return an array of the coordinates of the true of pixel (size of array = 2)
     */
    private int[] decode(int i) {
        if (i < 0 || i > w * h + 2)
            throw new IllegalArgumentException("Invalid parameters in deltaX()");
        int[] coordinates = new int[2];
        coordinates[0] = (i - 1) % w;
        coordinates[1] = (i - 1) / w;
        return coordinates;
    }

    /**
     * Creates and initialises an array for distTo the particular pixel
     * Initialises the dist to infinite initially
     *
     * @return an array of double initialised to infinity
     */
    private double[] createDistToMatrix() {
        double[] distTo = new double[w * h + 2];
        for (int i = 0; i < w * h + 2; i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        return distTo;
    }


    /**
     * Updates the distTo and edgeTo arrays based on a pair of neighbouring pixel values
     *
     * @param xold   - x value of pixel
     * @param yold   - y value of pixel
     * @param xnew   - neighbouring x value of pixel
     * @param ynew   - neighbouring y value of pixel
     * @param distTo - distance to the pixel value
     * @param edgeTo - indicates the pixel value which has an edge (with minimum dist) to the
     *               particular pixel
     * @param energy - the energy matrix
     */
    private void relax(int xold, int yold, int xnew, int ynew, double[] distTo, int[] edgeTo,
                       double[][] energy) {
        int iold = encode(xold, yold);
        if (xnew == w && ynew == h) {
            int inew = w * h + 1;
            if (distTo[inew] > distTo[iold]) {
                distTo[inew] = distTo[iold];
                edgeTo[inew] = iold;
                if (pq.contains(inew))
                    pq.decreaseKey(inew, distTo[inew]);
                else
                    pq.insert(inew, distTo[inew]);
            }
            return;
        }

        int inew = encode(xnew, ynew);
        if (distTo[inew] > distTo[iold] + energy[xnew][ynew]) {
            distTo[inew] = distTo[iold] + energy[xnew][ynew];
            edgeTo[inew] = iold;
            if (pq.contains(inew))
                pq.decreaseKey(inew, distTo[inew]);
            else
                pq.insert(inew, distTo[inew]);
        }
    }


    /**
     * Finds a vertical seam with the least energy (or dist)
     *
     * @return an array of column values containing the vertical seam
     */
    public int[] findVerticalSeam() {
        double[][] energy = computeEnergyMatrix();
        double[] distTo = createDistToMatrix();
        int[] edgeTo = new int[w * h + 2];

        distTo[0] = 0.0;
        edgeTo[0] = -1;
        // initialises the energy, distTo and edgeTo of the first row of the picture
        for (int i = 0; i < w; i++) {
            energy[i][0] = energy(i, 0);
            pq.insert(encode(i, 0), distTo[0] + energy[i][0]);
            distTo[encode(i, 0)] = energy[i][0];
            edgeTo[encode(i, 0)] = 0;
        }
        // uses min priority queue to get the least energy path to the bottomest pixel
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            int[] coordinates = decode(v);
            int xold = coordinates[0];
            int yold = coordinates[1];
            if (xold > w - 1 || yold > h - 1) {
                continue;
            }
            else if (yold == h - 1) {
                relax(xold, yold, w, h, distTo, edgeTo, energy);
            }
            else if (xold == 0) {
                relax(xold, yold, xold, yold + 1, distTo, edgeTo, energy);
                relax(xold, yold, xold + 1, yold + 1, distTo, edgeTo, energy);
            }
            else if (xold == w - 1) {
                relax(xold, yold, xold, yold + 1, distTo, edgeTo, energy);
                relax(xold, yold, xold - 1, yold + 1, distTo, edgeTo, energy);
            }
            else {
                relax(xold, yold, xold, yold + 1, distTo, edgeTo, energy);
                relax(xold, yold, xold - 1, yold + 1, distTo, edgeTo, energy);
                relax(xold, yold, xold + 1, yold + 1, distTo, edgeTo, energy);
            }
        }
        // stores the seam by following the edgeTo
        int[] seam = new int[h];
        int e = edgeTo[w * h + 1];
        int i = h - 1;
        while (i >= 0) {
            seam[i] = decode(e)[0];
            i--;
            e = edgeTo[e];
        }
        return seam;
    }


    /**
     * Finds a horizontal seam with the least energy (or dist)
     *
     * @return an array of row values containing the horizontal seam
     */
    public int[] findHorizontalSeam() {
        double[][] energy = computeEnergyMatrix();
        double[] distTo = createDistToMatrix();
        int[] edgeTo = new int[w * h + 2];

        distTo[0] = 0.0;
        edgeTo[0] = -1;
        // initialises the energy, distTo and edgeTo of the first column of the picture
        for (int i = 0; i < h; i++) {
            energy[0][i] = energy(0, i);
            pq.insert(encode(0, i), distTo[0] + energy[0][i]);
            distTo[encode(0, i)] = energy[0][i];
            edgeTo[encode(0, i)] = 0;
        }
        // uses min priority queue to get the least energy path to the rightmost pixel
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            int[] coordinates = decode(v);
            int xold = coordinates[0];
            int yold = coordinates[1];
            if (xold > w - 1 || yold > h - 1) {
                continue;
            }
            else if (xold == w - 1) {
                relax(xold, yold, w, h, distTo, edgeTo, energy);
            }
            else if (yold == 0) {
                relax(xold, yold, xold + 1, yold, distTo, edgeTo, energy);
                relax(xold, yold, xold + 1, yold + 1, distTo, edgeTo, energy);
            }
            else if (yold == h - 1) {
                relax(xold, yold, xold + 1, yold, distTo, edgeTo, energy);
                relax(xold, yold, xold + 1, yold - 1, distTo, edgeTo, energy);
            }
            else {
                relax(xold, yold, xold + 1, yold, distTo, edgeTo, energy);
                relax(xold, yold, xold + 1, yold - 1, distTo, edgeTo, energy);
                relax(xold, yold, xold + 1, yold + 1, distTo, edgeTo, energy);
            }
        }
        // stores the seam by following the edgeTo
        int[] seam = new int[w];
        int e = edgeTo[w * h + 1];
        int i = w - 1;
        while (i >= 0) {
            seam[i] = decode(e)[1];
            i--;
            e = edgeTo[e];
        }
        return seam;
    }

    /**
     * Validates the vertical seam
     *
     * @param seam - a vertical seam
     * @return True: if its a valid vertical seam
     * False: if its not a valid vertical seam
     */
    private boolean validateVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("Invalid parameter in validateVerticalSeam()");
        if (seam.length != h)
            return false;
        for (int i = 0; i < h; i++) {
            if (seam[i] < 0 || seam[i] > w - 1)
                return false;
            else if ((i != h - 1) && (Math.abs(seam[i] - seam[i + 1]) > 1))
                return false;
        }
        return true;
    }

    /**
     * Validates the horizontal seam
     *
     * @param seam - a horizontal seam
     * @return True: if its a valid horizontal seam
     * False: if its not a valid horizontal seam
     */
    private boolean validateHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("Invalid parameter in validateHorizontalSeam()");
        if (seam.length != w)
            return false;
        for (int i = 0; i < w; i++) {
            if (seam[i] < 0 || seam[i] > h - 1)
                return false;
            else if ((i != w - 1) && (Math.abs(seam[i] - seam[i + 1]) > 1))
                return false;
        }
        return true;
    }


    /**
     * Removes the horizontal seam from the picture
     * Updates the picture
     *
     * @param seam - the horizontal seam
     */
    public void removeHorizontalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("Invalid parameter in removeHorizontalSeam()");
        if (!validateHorizontalSeam(seam))
            throw new IllegalArgumentException("The seam is not a valid horizontal seam");
        Picture p = new Picture(w, h - 1);
        for (int i = 0; i < seam.length; i++) {
            int j;
            for (j = 0; j < seam[i]; j++) {
                p.setRGB(i, j, picture.getRGB(i, j));
            }
            j++;
            for (; j < h; j++) {
                p.setRGB(i, j - 1, picture.getRGB(i, j));
            }
        }
        this.picture = p;
        h--;
    }


    /**
     * Removes the vertical seam from the picture
     * Updates the picture
     *
     * @param seam - the vertical seam
     */
    public void removeVerticalSeam(int[] seam) {
        if (seam == null)
            throw new IllegalArgumentException("Invalid parameter in removeVerticalSeam()");
        if (!validateVerticalSeam(seam))
            throw new IllegalArgumentException("The seam is not a valid vertical seam");
        Picture p = new Picture(w - 1, h);
        for (int i = 0; i < seam.length; i++) {
            int j;
            for (j = 0; j < seam[i]; j++) {
                p.setRGB(j, i, picture.getRGB(j, i));
            }
            j++;
            for (; j < w; j++) {
                p.setRGB(j - 1, i, picture.getRGB(j, i));
            }
        }
        this.picture = p;
        w--;
    }


    /**
     * Unit testing of <code>SeamCarver</code>
     *
     * @param args - the command line arguments
     */
    public static void main(String[] args) {
        Picture p = new Picture("HJocean.png");
        SeamCarver seamCarver = new SeamCarver(p);
        int noOfVerticalSeamsToRemove = 50;
        int noOfHorizontalSeamsToRemove = 50;
        System.out.println("Removing " + noOfVerticalSeamsToRemove + " vertical seams and "
                                   + noOfHorizontalSeamsToRemove + " horizontal seams");
        for (int i = 0; i < noOfVerticalSeamsToRemove; i++) {
            int[] seam = seamCarver.findVerticalSeam();
            seamCarver.removeVerticalSeam(seam);
        }
        for (int i = 0; i < noOfHorizontalSeamsToRemove; i++) {
            int[] seam = seamCarver.findHorizontalSeam();
            seamCarver.removeHorizontalSeam(seam);
        }
        seamCarver.picture().show();
    }

}
