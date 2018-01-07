/******************************************************************************
 *  Compilation:  javac-algs4 Permutation.java
 *  Execution:    java-algs4 Permutation
 *  Dependencies: StdIn, StdOut
 *  
 *  Description: A client program Permutation.java takes an integer k as
 *  a command-line argument.
 * 
 ******************************************************************************/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}