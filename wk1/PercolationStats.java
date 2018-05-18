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
