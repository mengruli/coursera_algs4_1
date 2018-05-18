import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private int[] nodeStatus;// 0 -> closed; 1 -> open
    private final int matrix_size; 
    private final WeightedQuickUnionUF unionFound; 

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0)
        {
            throw new IllegalArgumentException("Node size must be > 0");
        }

        matrix_size = n; 
       
        nodeStatus = new int[n*n+2]; 
        unionFound = new WeightedQuickUnionUF(n*n+2); 

        for (int i = 0; i < n*n+2; i++)
        {
            // the last element is the psyudo parent of all buttom line itmes
            if (i >= getIndex(matrix_size, 1))
            {
                
                unionFound.union(i, n*n+1);
            }
            
        }
    }

    private void checkInput(int row, int col)
    {
        if (row < 1 || row > matrix_size || col < 1 || col > matrix_size)
        {
            throw new IllegalArgumentException("Node size must be > 0");
        }
    }

    private int getIndex(int row, int col)
    {
        int index = (row - 1) * matrix_size + col;
        return index;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        checkInput(row, col);

        if (!isOpen(row, col))
        {
            nodeStatus[getIndex(row, col)] = 1;
        }

        if (row == 1 && !unionFound.connected(getIndex(row, col), 0))// manually set the parent to the top psyudo node
        {
            // allNodes[getIndex(row, col)] = 0;
            unionFound.union(getIndex(row, col), 0);
        }

        // System.out.println("Opening row: " + row + " col: " + col + " Index: " +  getIndex(row, col));
        // check up
        if (row > 1)
        {
            if(isOpen(row-1, col))
            {
                unionFound.union(getIndex(row, col), getIndex(row-1, col));
                // System.out.println("Unioned to Upper site: " + getIndex(row-1, col));
                // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
                // getIndex(row-1, col)+ "? "
                // + unionFound.connected(getIndex(row, col), getIndex(row-1, col)));
            }
        }

        // check left
        if (col > 1)
        {
            if (isOpen(row, col-1))
            {
                unionFound.union(getIndex(row, col), getIndex(row, col-1));
                // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
                // getIndex(row, col-1)+ "? "
                // + unionFound.connected(getIndex(row, col), getIndex(row, col-1)));
            }
        }

        // check down
        if (row < matrix_size)
        {
            if (isOpen(row+1, col))
            {
                unionFound.union(getIndex(row, col), getIndex(row+1, col));
                // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
                // getIndex(row+1, col)+ "? "
                // + unionFound.connected(getIndex(row, col), getIndex(row+1, col)));
            }
        }

        // check right
        if (col < matrix_size)
        {
            if (isOpen(row, col+1))
            {
                unionFound.union(getIndex(row, col), getIndex(row, col+1));
                // System.out.println("Site: " + getIndex(row, col) + "  is connected to"+
                // getIndex(row, col+1)+ "? "
                // + unionFound.connected(getIndex(row, col), getIndex(row, col+1)));
            }
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        checkInput(row, col);

        return nodeStatus[getIndex(row, col)] == 1;
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        checkInput(row, col);

        if (matrix_size == 1)
        {
            return isOpen(row, col);
        }
        else
        {
            return unionFound.connected(getIndex(row, col), 0);
        }
        
    }

    public int numberOfOpenSites()       // number of open sites
    {
        int count = 0;
        for(int i = 0; i < nodeStatus.length; i++)
        {
            if(nodeStatus[i] == 1)
            {
                count++;
            }
        }
        return count;
    }

    public boolean percolates()              // does the system percolate?
    {

        if (matrix_size == 1)
        {
            return nodeStatus[1] == 1;
        }
        
        return unionFound.connected(matrix_size * matrix_size + 1, 0);
    
    }
 
 }