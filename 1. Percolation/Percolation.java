import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int n;
    private int no_of_open;
    private int[][] grid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        this.n = n;
        no_of_open = 0;
        grid = new int[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n * n + 1);
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
            uf2.union(0, i);
        }
        for (int i = 0; i < n; i++) {
            int value = convert_coordinates(n - 1, i);
            uf.union(n * n + 1, value);
        }
    }

    private int convert_coordinates(int row, int col) {
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) throw new IllegalArgumentException();
        return n * row + col + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) throw new IllegalArgumentException();
        int one_d = convert_coordinates(row, col);
        if (grid[row][col] == 0) {
            grid[row][col] = 1;
            no_of_open++;
            try {
                if (isOpen(row, col + 1)) {
                    int other_d = convert_coordinates(row, col + 1);
                    uf.union(one_d, other_d);
                    uf2.union(one_d, other_d);
                }
            }
            catch (IllegalArgumentException ignored) {
            }

            try {
                if (isOpen(row, col - 1)) {
                    int other_d = convert_coordinates(row, col - 1);
                    uf.union(one_d, other_d);
                    uf2.union(one_d, other_d);
                }
            }
            catch (IllegalArgumentException ignored) {
            }

            try {
                if (isOpen(row + 1, col)) {
                    int other_d = convert_coordinates(row + 1, col);
                    uf.union(one_d, other_d);
                    uf2.union(one_d, other_d);
                }
            }
            catch (IllegalArgumentException ignored) {
            }

            try {
                if (isOpen(row - 1, col)) {
                    int other_d = convert_coordinates(row - 1, col);
                    uf.union(one_d, other_d);
                    uf2.union(one_d, other_d);
                }
            }
            catch (IllegalArgumentException ignored) {
            }

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) throw new IllegalArgumentException();
        if (grid[row][col] == 0) {
            return false;
        }
        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1) throw new IllegalArgumentException();
        int single_coordinate = convert_coordinates(row, col);
        if (isOpen(row, col)) {
            return (uf2.find(0) == uf2.find(single_coordinate));
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return no_of_open;
    }

    // does the system percolate?
    public boolean percolates() {
        // System.out.println(uf.find(0));
        // System.out.println(uf.find(n * n + 1));
        return (uf.find(0) == uf.find(n * n + 1));
    }

    // unit testing (required)
    public static void main(String[] args) {
        // int n = Integer.parseInt(args[0]);
        int n = 5;
        Percolation perc = new Percolation(5);
        perc.open(1, 3);
        System.out.println("Is (1,3) Full? = " + perc.isFull(1, 3));
        perc.open(0, 3);
        perc.open(2, 3);
        System.out.println("Is (1,3) Full? = " + perc.isFull(1, 3));
        perc.open(2, 4);
        perc.open(3, 4);
        System.out.println("Percolates? = " + perc.percolates());
        perc.open(4, 4);
        System.out.println("Percolates? = " + perc.percolates());

    }

}
