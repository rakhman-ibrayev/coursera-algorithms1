import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final WeightedQuickUnionUF perc;
    private final int size;
    private final int topVirtualNode;
    private final int bottomVirtualNode;
    private int numOpenSites;

    /**
     * Constructor: creates an n by n grid
     *
     * @param n - sets the size of the grid
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Negative size");
        }
        size = n;
        perc = new WeightedQuickUnionUF(size * size + 2);
        grid = new boolean[size][size];
        topVirtualNode = 0;
        bottomVirtualNode = size * size + 1;
        numOpenSites = 0;
    }

    /**
     * Changes two-dimensional array indices so that
     * they can be used in a one-dimensional array
     *
     * @param row - row index
     * @param col - column index
     * @return one-dimensional index
     */
    private int changeDimension(int row, int col) {
        return row * size + col;
    }

    /**
     * Checks if indices are in correct bounds
     *
     * @param row - row index
     * @param col - column index
     */
    private void checkBounds(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    /**
     * Connects adjacent elements in the WeightedQuickUnion
     *
     * @param row - row index of an element
     * @param col - column index of an element
     */
    private void connectAdjacent(int row, int col) {
        int crow = row - 1;
        int ccol = col - 1;
        int index = changeDimension(crow, ccol);

        if (crow == 0) {
            perc.union(topVirtualNode, index);
        }
        if (crow == size - 1) {
            perc.union(bottomVirtualNode, index);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            perc.union(changeDimension(crow - 1, ccol), index);
        }
        if (col < size && isOpen(row, col + 1)) {
            perc.union(changeDimension(crow, ccol + 1), index);
        }
        if (row < size && isOpen(row + 1, col)) {
            perc.union(changeDimension(crow + 1, ccol), index);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            perc.union(changeDimension(crow, ccol - 1), index);
        }
    }

    /**
     * Opens an element
     *
     * @param row - row index of an element
     * @param col - column index of an element
     */
    public void open(int row, int col) {
        checkBounds(row, col);

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            connectAdjacent(row, col);
            numOpenSites++;
        }
    }

    /**
     * Checks if element is open
     *
     * @param row - row index of an element
     * @param col - column index of an element
     * @return {@code true} if open and {@code false} if blocked
     */
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return grid[row - 1][col - 1];
    }

    /**
     * Checks if element is connected with the top
     *
     * @param row - row index of an element
     * @param col - column index of an element
     * @return {@code true} if connected and {@code false} if not
     */
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return isOpen(row, col) && perc.find(topVirtualNode) == perc
                .find(changeDimension(row - 1, col - 1));
    }

    /**
     * @return number of open elements
     */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
     * Checks whether the top and bottom virtual nodes are connected
     *
     * @return {@code true} if connected and {@code false} if not
     */
    public boolean percolates() {
        return perc.find(topVirtualNode) == perc.find(bottomVirtualNode);
    }
}
