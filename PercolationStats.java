/******************************************************************************
 *  Compilation:  javac-algs4 PercolationStats.java
 *  Execution:    java-algs4 PercolationStats
 *  Dependencies: StdRandom, StdStats, StdOut
 *  
 *  Description: Percolation.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] tries;
    private final int trials;
    
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("N is lower or equal than 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials is lower or equal than 0");
        }

        this.trials = trials;
        tries = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            double threshold = 0;
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int column = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, column)) {
                    threshold++;
                    percolation.open(row, column);
                }
            }
            tries[i] = threshold / (n * n);
        }
    }
    
    public double mean() {
        return StdStats.mean(tries);
    }
    public double stddev() {
        return StdStats.stddev(tries);
    }
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }

        try {
            int n = Integer.parseInt(args[0]);
            int t = Integer.parseInt(args[1]);

            PercolationStats percolationStats = new PercolationStats(n, t);
            StdOut.println("mean                    = "
                    + percolationStats.mean());
            StdOut.println("stddev                  = "
                    + percolationStats.stddev());
            StdOut.println("95% confidence interval = ["
                    + percolationStats.confidenceLo() + ", "
                    + percolationStats.confidenceHi() + "]");
        }
        catch (NumberFormatException e) {
            StdOut.println("Argument must be an integer");
            return;
        }
    }
}