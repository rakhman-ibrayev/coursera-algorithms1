import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        this.points = new SET<>();
    }

    public boolean isEmpty() {
        return points.size() == 0;
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> pointsInRect = new Queue<>();

        for (Point2D p : points) {
            if (rect.contains(p)) pointsInRect.enqueue(p);
        }

        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        Point2D nearestPoint = null;

        for (Point2D c : points) {
            if (nearestPoint == null || p.distanceSquaredTo(c) < p
                    .distanceSquaredTo(nearestPoint)) {
                nearestPoint = c;
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
        double[][] ps = {
                { 0.206107, 0.095492 },
                { 0.975528, 0.654508 },
                { 0.024472, 0.345492 },
                { 0.793893, 0.095492 },
                { 0.793893, 0.904508 },
                { 0.975528, 0.345492 },
                { 0.206107, 0.904508 },
                { 0.500000, 0.000000 },
                { 0.024472, 0.654508 },
                { 0.500000, 1.000000 },
                };
        PointSET pset = new PointSET();

        for (int i = 0; i < ps.length; i++) {
            Point2D p = new Point2D(ps[i][0], ps[i][1]);
            pset.insert(p);
        }
        for (int i = 0; i < ps.length; i++) {
            Point2D p = new Point2D(ps[i][0], ps[i][1]);
            StdOut.println(pset.contains(p) + " kd has: " + p);
        }

        pset.draw();
    }
}
