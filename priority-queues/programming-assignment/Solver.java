import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {
    private final SearchNode minSN;
    private final SearchNode minTwinSN;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        MinPQ<SearchNode> minTwinPQ = new MinPQ<SearchNode>();

        minPQ.insert(new SearchNode(initial, 0, null));
        minTwinPQ.insert(new SearchNode(initial.twin(), 0, null));

        SearchNode min = minPQ.delMin();
        SearchNode minTwin = minTwinPQ.delMin();

        while (!min.isGoal() && !minTwin.isGoal()) {
            for (Board b : min.board.neighbors()) {
                if (min.moves == 0 || !b.equals(min.prev.board)) {
                    minPQ.insert(new SearchNode(b, min.moves + 1, min));
                }
            }
            for (Board b : minTwin.board.neighbors()) {
                if (minTwin.moves == 0 || !b.equals(minTwin.prev.board)) {
                    minTwinPQ.insert(new SearchNode(b, minTwin.moves + 1, minTwin));
                }
            }

            min = minPQ.delMin();
            minTwin = minTwinPQ.delMin();
        }

        minSN = min;
        minTwinSN = minTwin;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int priority;
        private int moves;
        private SearchNode prev;

        public SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.priority = moves + board.manhattan();
            this.moves = moves;
            this.prev = prev;
        }

        public boolean isGoal() {
            return priority - moves == 0;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority - that.priority;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (minSN.isGoal()) return true;
        else if (minTwinSN.isGoal()) return false;
        return false;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return minSN.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            Stack<Board> moves = new Stack<>();
            SearchNode min = minSN;

            while (min != null) {
                moves.push(min.board);
                min = min.prev;
            }

            return moves;
        }
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
