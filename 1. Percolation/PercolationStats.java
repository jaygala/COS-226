import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private double[] open_sites;
    private double trials;
    private double mean_var;
    private double sttdev_var;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        open_sites = new double[trials];
        this.trials = trials;
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            // System.out.println(i);
            while (!perc.percolates()) {
                int row;
                int col;
                do {
                    row = StdRandom.uniform(0, n);
                    col = StdRandom.uniform(0, n);
                } while (perc.isOpen(row, col));
                perc.open(row, col);
                open_sites[i]++;

            }
            open_sites[i] /= (n * n);
            // System.out.println("i = " + i + "opensites = " + open_sites[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean_var = StdStats.mean(open_sites);
        return mean_var;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        sttdev_var = StdStats.stddev(open_sites);
        return sttdev_var;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        // double my_mean = mean();
        // double my_stddev = stddev();
        double ans = mean_var - (1.96 * sttdev_var) / Math.sqrt(trials);
        return ans;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        // double my_mean = mean();
        // double my_stddev = stddev();
        double ans = mean_var + (1.96 * sttdev_var) / Math.sqrt(trials);
        return ans;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        Stopwatch time = new Stopwatch();
        PercolationStats pstats = new PercolationStats(n, trials);

        double x_mean = pstats.mean();
        double x_stddev = pstats.stddev();
        double conf_low = pstats.confidenceLow();
        double conf_high = pstats.confidenceHigh();
        double elapsed_time = time.elapsedTime();
        System.out.println("mean() = " + x_mean);
        System.out.println("stddev() = " + x_stddev);
        System.out.println("confidenceLow() = " + conf_low);
        System.out.println("confidenceHigh() = " + conf_high);
        System.out.println("elapsedTime() = " + elapsed_time);

    }

}
