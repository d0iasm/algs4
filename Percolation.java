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
    private static final boolean BLOCK = false;
    private static final boolean OPEN = true;
    private static final int TOP = 0;
    private final int bottom;
    private final WeightedQuickUnionUF wqu;
    private final int size;
    private boolean[][] grid;
    private int openedNum = 0;
    
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("constructor size is out of bounds");
        }
        size = n;
        bottom = n*n+1;
        grid = new boolean[n][n];
        wqu = new WeightedQuickUnionUF(n*n+2);
    }
    
    public void open(int row, int col) {
        checkBounds(row, col);
        if (isOpen(row, col)) return;
        
        grid[row-1][col-1] = OPEN;
        openedNum += 1;
        
        int index = getWquIndex(row, col);
        
        if (row == 1) {
            wqu.union(index, TOP);
        }
        if (row == size) {
            wqu.union(index, bottom);
        }
        
        if (row < size) {
            if (isOpen(row+1, col)) {
                wqu.union(index, getWquIndex(row+1, col));
            }
        }
        if (row-1 > 0) {
            if (isOpen(row-1, col)) {
                wqu.union(index, getWquIndex(row-1, col));
            }
        }
        if (col < size) {
            if (isOpen(row, col+1)) {
                wqu.union(index, getWquIndex(row, col+1));
            }
        }
        if (col-1 > 0) {
            if (isOpen(row, col-1)) {
                wqu.union(index, getWquIndex(row, col-1));
            }
        }
    }
    
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return grid[row-1][col-1];
    }
    
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return wqu.connected(TOP, getWquIndex(row, col));
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