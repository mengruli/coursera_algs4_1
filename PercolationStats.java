import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] results;
    private final int t;


    public static void main(String[] args)        // test client (described below)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStat = new PercolationStats(n, trials);

        System.out.println("mean\t= "+ percStat.mean());
        System.out.println("stddev\t= "+ percStat.stddev());
        System.out.println("95% confidence interval\t= "+ "["+percStat.confidenceLo()
        +", "+percStat.confidenceHi() + "]");
    }
    
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0)
        {
            throw new IllegalArgumentException();
        }

        this.results = new double[trials];
        this.t = trials;

        for (int i = 0; i < trials; i++)
        {
            this.results[i] = performTest(n);
           
        }
    }

    private double performTest(int n)
    {
        Percolation perc = new Percolation(n);
        double xi = 0d;
        int row = StdRandom.uniform(1, n+1);
        int col = StdRandom.uniform(1, n+1);


        while (!perc.percolates())
        {
            perc.open(row, col);
            row = StdRandom.uniform(1, n+1);
            col = StdRandom.uniform(1, n+1);
        }

        xi = perc.numberOfOpenSites() * 1.0 / (n * n);
      
        return xi;
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(this.results);
    }
    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(this.results);
    }
    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double ret = this.mean() - (1.96*stddev() / Math.sqrt(t));
        return ret;
    }
    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double ret = this.mean() + (1.96*stddev() / Math.sqrt(t));
        return ret;
    }
 
 }

//  class Percolation {
//     // public static void main(String[] args)   // test client (optional)
//     // {
        
//     // }

//     // private int[] allNodes;
//     private int[] nodeStatus;// 0 -> closed; 1 -> open
//     private int matrix_size;
//     private WeightedQuickUnionUF unionFound;

//     public Percolation(int n)                // create n-by-n grid, with all sites blocked
//     {
//         if(n <= 0)
//         {
//             throw new IllegalArgumentException("Node size must be > 0");
//         }

//         matrix_size = n;
//         // allNodes = new int[n*n+2];
//         nodeStatus = new int[n*n+2];
//         unionFound = new WeightedQuickUnionUF(n*n+2);

//         for(int i = 0; i < n*n+2; i++)
//         {
//             //the last element is the psyudo parent of all buttom line itmes
//             if(i >= getIndex(matrix_size, 1))
//             {
//                 //allNodes[i] = n*n+1;
//                 unionFound.union(i, n*n+1);
//             }
//             // else
//             // {
//             //     allNodes[i] = i;
//             // }
//         }
//     }

//     private void checkInput(int row, int col)
//     {
//         if(row < 1 || row > matrix_size || col < 1 || col > matrix_size)
//         {
//             throw new IllegalArgumentException("Node size must be > 0");
//         }
//     }

//     private int getIndex(int row, int col)
//     {
//         int index = (row - 1) * matrix_size + col;
//         return index;
//     }

//     public void open(int row, int col)    // open site (row, col) if it is not open already
//     {
//         checkInput(row, col);

//         if(!isOpen(row, col))
//         {
//             nodeStatus[getIndex(row, col)] = 1;
//         }

//         if(row == 1 && !unionFound.connected(getIndex(row, col), 0))// manually set the parent to the top psyudo node
//         {
//             // allNodes[getIndex(row, col)] = 0;
//             unionFound.union(getIndex(row, col), 0);
//         }

//         // System.out.println("Opening row: " + row + " col: " + col + " Index: " +  getIndex(row, col));
//         // check up
//         if(row > 1)
//         {
//             if(isOpen(row-1, col))
//             {
//                 unionFound.union(getIndex(row, col), getIndex(row-1, col));
//                 // System.out.println("Unioned to Upper site: " + getIndex(row-1, col));
//                 // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
//                 // getIndex(row-1, col)+ "? "
//                 // + unionFound.connected(getIndex(row, col), getIndex(row-1, col)));
//             }
//         }

//         // check left
//         if(col > 1)
//         {
//             if(isOpen(row, col-1))
//             {
//                 unionFound.union(getIndex(row, col), getIndex(row, col-1));
//                 // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
//                 // getIndex(row, col-1)+ "? "
//                 // + unionFound.connected(getIndex(row, col), getIndex(row, col-1)));
//             }
//         }

//         // check down
//         if(row < matrix_size)
//         {
//             if(isOpen(row+1, col))
//             {
//                 unionFound.union(getIndex(row, col), getIndex(row+1, col));
//                 // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
//                 // getIndex(row+1, col)+ "? "
//                 // + unionFound.connected(getIndex(row, col), getIndex(row+1, col)));
//             }
//         }

//         // check right
//         if(col < matrix_size)
//         {
//             if(isOpen(row, col+1))
//             {
//                 unionFound.union(getIndex(row, col), getIndex(row, col+1));
//                 // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
//                 // getIndex(row, col+1)+ "? "
//                 // + unionFound.connected(getIndex(row, col), getIndex(row, col+1)));
//             }
//         }
//     }

//     public boolean isOpen(int row, int col)  // is site (row, col) open?
//     {
//         checkInput(row, col);

//         return nodeStatus[getIndex(row, col)] == 1;
//     }

//     public boolean isFull(int row, int col)  // is site (row, col) full?
//     {
//         checkInput(row, col);

//         if(matrix_size == 1)
//         {
//             return isOpen(row, col);
//         }
//         else
//         {
//             return unionFound.connected(getIndex(row, col), 0);
//         }
        
//     }

//     public int numberOfOpenSites()       // number of open sites
//     {
//         int count = 0;
//         for(int i = 0; i < nodeStatus.length; i++)
//         {
//             if(nodeStatus[i] == 1)
//             {
//                 count++;
//             }
//         }
//         return count;
//     }

//     public boolean percolates()              // does the system percolate?
//     {

//         if(matrix_size == 1)
//         {
//             return nodeStatus[1] == 1;
//         }
        
//         return unionFound.connected(matrix_size * matrix_size + 1, 0);
    
//     }
 
//  }