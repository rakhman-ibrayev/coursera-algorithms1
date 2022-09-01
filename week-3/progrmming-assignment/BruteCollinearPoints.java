import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final LineSegment[] segmentsArray;

    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("null argument passed to constructor");
        checkNullPoint(points);

        Point[] sortedPoints = Arrays.copyOf(points, points.length);
        Arrays.sort(sortedPoints);
        checkDuplicatePoints(sortedPoints);

        ArrayList<LineSegment> segmentsList = new ArrayList<>();

        for (int i = 0; i < sortedPoints.length - 3; i++) {
            for (int j = i + 1; j < sortedPoints.length - 2; j++) {
                for (int k = j + 1; k < sortedPoints.length - 1; k++) {
                    for (int z = k + 1; z < sortedPoints.length; z++) {
                        if (slopesEqual(sortedPoints[i], sortedPoints[j], sortedPoints[k],
                                        sortedPoints[z])) {
                            LineSegment segment = new LineSegment(sortedPoints[i], sortedPoints[z]);
                            segmentsList.add(segment);
                        }
                    }
                }
            }
        }

        segmentsArray = new LineSegment[segmentsList.size()];

        for (int i = 0; i < segmentsList.size(); i++) {
            segmentsArray[i] = segmentsList.get(i);
        }
    }

    public int numberOfSegments() {
        return segmentsArray.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segmentsArray, numberOfSegments());
    }

    private void checkNullPoint(Point[] points) {
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("null point entry");
            }
        }
    }

    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("duplicate points");
            }
        }
    }

    private boolean slopesEqual(Point a, Point b, Point c, Point d) {
        return a.slopeTo(b) == a.slopeTo(c) && a.slopeTo(b) == a.slopeTo(d);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param p one endpoint
     * @param q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *                              is <tt>null</tt>
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException(
                    "both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
    }


    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this line segment
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Throws an exception if called. The hashCode() method is not supported because
     * hashing has not yet been introduced in this course. Moreover, hashing does not
     * typically lead to good *worst-case* performance guarantees, as required on this
     * assignment.
     *
     * @throws UnsupportedOperationException if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}
