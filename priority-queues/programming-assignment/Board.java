import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public final class Board {
    private final int n;
    private final int[][] boardGrid;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col) {
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.boardGrid = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                this.boardGrid[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", boardGrid[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // correct tile at the provided row and column
    private int getCorrectTile(int row, int col) {
        return row * n + col + 1;
    }

    // number of tiles out of place
    public int hamming() {
        int counter = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = boardGrid[i][j];
                int correctTile = getCorrectTile(i, j);
                if (tile != 0 && tile != correctTile)
                    counter++;
            }
        }

        return counter;
    }

    // correct cell of a tile
    private int[] getCorrectCell(int tile) {
        int row = (int) Math.ceil((double) tile / n) - 1;
        int col = tile - row * n - 1;
        return new int[] { row, col };
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sumDistances = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = boardGrid[i][j];
                if (tile != 0) {
                    int[] correctCell = getCorrectCell(tile);
                    sumDistances += Math.abs(i - correctCell[0]);
                    sumDistances += Math.abs(j - correctCell[1]);
                }
            }
        }

        return sumDistances;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (that.n == this.n) && (Arrays.deepEquals(that.boardGrid, this.boardGrid));
    }

    // return a new board with two exchanged tiles
    private Board getExchangedBoard(int i0, int j0, int i1, int j1) {
        Board neighbor = new Board(boardGrid);
        int temp = neighbor.boardGrid[i1][j1];
        neighbor.boardGrid[i1][j1] = neighbor.boardGrid[i0][j0];
        neighbor.boardGrid[i0][j0] = temp;
        return neighbor;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighborsQ = new Queue<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (boardGrid[i][j] == 0) {
                    if (j - 1 > -1) {
                        neighborsQ.enqueue(getExchangedBoard(i, j, i, j - 1));
                    }
                    if (i + 1 < n) {
                        neighborsQ.enqueue(getExchangedBoard(i, j, i + 1, j));
                    }
                    if (j + 1 < n) {
                        neighborsQ.enqueue(getExchangedBoard(i, j, i, j + 1));
                    }
                    if (i - 1 > -1) {
                        neighborsQ.enqueue(getExchangedBoard(i, j, i - 1, j));
                    }

                    return neighborsQ;
                }
            }
        }

        return neighborsQ;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int row = 0;
        int col1 = 0;
        int col2 = 1;

        for (int i = 0; i < boardGrid[row].length; i++) {
            if (boardGrid[row][i] == 0) {
                if (row - 1 > -1) {
                    row = row - 1;
                    col1 = 0;
                    col2 = 1;
                }
                else if (row + 1 < n) {
                    row = row + 1;
                    col1 = 0;
                    col2 = 1;
                }
            }
        }

        return getExchangedBoard(row, col1, row, col2);
    }

    // unit testing (not graded) {
    public static void main(String[] args) {
        int[][] tiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };
        Board b = new Board(tiles);
        StdOut.println("b: " + b);
        StdOut.println("is goal: " + b.isGoal());
        StdOut.println("hamming: " + b.hamming());
        StdOut.println("manhattan: " + b.manhattan());

        int[][] tiles1 = {
                { 1, 2, 3 },
                { 0, 4, 6 },
                { 7, 5, 8 }
        };
        Board b1 = new Board(tiles1);
        StdOut.println("b1: " + b1);
        StdOut.println("are b and b1 equal: " + b.equals(b1));
        StdOut.println("b1 twin: \n" + b1.twin());
        StdOut.println("b1 neighbors: \n" + b1.neighbors());
    }
}
