/******************************************************************************
 *  Compilation:  javac-algs4 Percolation.java
 *  Execution:    java-algs4 Percolation
 *  Dependencies: StdRandom, WeightedQuickUnionUF
 *  
 *  Description: Percolation.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private static final int BLOCK = 0;
    private static final int OPEN = 1;
    private static final int FULL = 2;
    private static final int TOP = 0;
    private final int bottom;
    private final WeightedQuickUnionUF wqu;
    private final int size;
    private int[][] grid;
    private int openedNum = 0;
    
    public Percolation(int n) {
        size = n;
        bottom = n*n+1;
        grid = new int[n][n];
        wqu = new WeightedQuickUnionUF(n*n+2);
        for (int i = 0; i < n; i++) {
            wqu.union(TOP, getWquIndex(1, i+1));
            wqu.union(bottom, getWquIndex(n, i+1));
            for (int j = 0; j < n; j++) {
                grid[i][j] = BLOCK;
            }
        }
    }
    
    public void open(int row, int col) {
        checkBounds(row, col);
        if (isOpen(row, col)) return;
        
        grid[row-1][col-1] = OPEN;
        openedNum += 1;
        
        if (row < size) {
            if (isOpen(row+1, col)) {
                wqu.union(getWquIndex(row, col), getWquIndex(row+1, col));
                grid[row-1][col-1] = FULL;
                grid[row][col-1] = FULL;
            }
        }
        if (row-1 > 0) {
            if (isOpen(row-1, col)) {
                wqu.union(getWquIndex(row, col), getWquIndex(row-1, col));
                grid[row-1][col-1] = FULL;
                grid[row-2][col-1] = FULL;
            }
        }
        if (col < size) {
            if (isOpen(row, col+1)) {
                wqu.union(getWquIndex(row, col), getWquIndex(row, col+1));
                grid[row-1][col-1] = FULL;
                grid[row-1][col] = FULL;
            }
        }
        if (col-1 > 0) {
            if (isOpen(row, col-1)) {
                wqu.union(getWquIndex(row, col), getWquIndex(row, col-1));
                grid[row-1][col-1] = FULL;
                grid[row-1][col-2] = FULL;
            }
        }
    }
    
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return grid[row-1][col-1] > BLOCK;
    }
    
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        if (grid[row-1][col-1] == FULL) return true;
        return false;
    } 
    
    public int numberOfOpenSites() {
        return openedNum;
    }
    
    public boolean percolates() {
        return wqu.connected(TOP, bottom);
    }
        
    private int getWquIndex(int i, int j) {
        return size * (i-1) + j;
    }
    
    private void checkBounds(int i, int j) {
        if (i < 1 || i > size) {
            throw new IllegalArgumentException("row index i out of bounds");
        }
        if (j < 1 || j > size) {
            throw new IllegalArgumentException("column index j out of bounds");
        }
    }
    
    public static void main(String[] args) {
        final int TESTS = 100;
        final int GRID_SIZE = 3;
         
        System.out.println("\n***Percolation: Monte Carlo Simulation ***\n");
         
        Percolation perc = new Percolation(GRID_SIZE);
        System.out.println("SuccessFULLy created Percolation object.");
        System.out.println("N: " + perc.numberOfOpenSites());
        System.out.println();
         
        System.out.println("Starting to open random sites...");
        int row, col, ct;
        double sum = 0.0;
        for (int i = 0; i < TESTS; i++) {
            ct = 0;
            perc = new Percolation(GRID_SIZE);
            while (!perc.percolates()) {
                row = StdRandom.uniform(GRID_SIZE) + 1;
                col = StdRandom.uniform(GRID_SIZE) + 1;
                perc.open(row, col);
                ct++;
                System.out.println("Open(" + row + ", " + col + ") " + "Count: " + ct);
            }
            sum += ct;
        }
        System.out.println("After " + TESTS + " attempts, the average number of sites");
        System.out.printf("opened was %.2f", sum/TESTS);
        System.out.printf(" or %.2f", ((sum/TESTS)/(GRID_SIZE * GRID_SIZE)) * 100);
        System.out.println("%.");
        System.out.println("\nDone.\n");
    }
}