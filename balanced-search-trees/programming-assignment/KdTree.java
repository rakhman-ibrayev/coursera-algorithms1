import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private Node root;
    private int n;

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    public KdTree() {
        this.n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private int compare(Point2D p1, Point2D p2, boolean orientation) {
        double cmp1;
        double cmp2;

        if (orientation) {
            cmp1 = p1.x();
            cmp2 = p2.x();
            if (cmp1 == cmp2) {
                cmp1 = p1.y();
                cmp2 = p2.y();
            }
        }
        else {
            cmp1 = p1.y();
            cmp2 = p2.y();
            if (cmp1 == cmp2) {
                cmp1 = p1.x();
                cmp2 = p2.x();
            }
        }

        return Double.compare(cmp1, cmp2);
    }

    private Node insert(Node h, Point2D p, double x0, double y0, double x1, double y1,
                        boolean orientation) {
        if (h == null) {
            n++;
            return new Node(p, new RectHV(x0, y0, x1, y1));
        }

        int cmp = compare(p, h.p, orientation);

        if (cmp < 0) {
            if (orientation) {
                h.left = insert(h.left, p, x0, y0, h.p.x(), y1, false);
            }
            else {
                h.left = insert(h.left, p, x0, y0, x1, h.p.y(), true);
            }
        }
        else if (cmp > 0) {
            if (orientation) {
                h.right = insert(h.right, p, h.p.x(), y0, x1, y1, false);
            }
            else {
                h.right = insert(h.right, p, x0, h.p.y(), x1, y1, true);
            }
        }

        return h;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    private Node get(Node h, Point2D p) {
        boolean orientation = true;

        while (h != null) {
            int cmp = compare(p, h.p, orientation);

            if (cmp < 0) h = h.left;
            else if (cmp > 0) h = h.right;
            else return h;

            orientation = !orientation;
        }

        return null;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(root, p) != null;
    }

    private void draw(Node h, boolean orientation) {
        if (h == null) return;

        draw(h.left, !orientation);

        StdDraw.setPenRadius();
        if (orientation) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(h.p.x(), h.rect.ymin(), h.p.x(), h.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(h.rect.xmin(), h.p.y(), h.rect.xmax(), h.p.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        h.p.draw();

        draw(h.right, !orientation);
    }

    public void draw() {
        StdDraw.clear();
        draw(root, true);
    }

    private void populateRange(Node h, RectHV rect, Queue<Point2D> range) {
        if (h == null || !h.rect.intersects(rect)) return;
        populateRange(h.left, rect, range);
        if (rect.contains(h.p)) range.enqueue(h.p);
        populateRange(h.right, rect, range);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> pointsInRect = new Queue<>();
        populateRange(root, rect, pointsInRect);
        return pointsInRect;
    }

    private Point2D getNearest(Node h, Point2D p, Point2D nearest) {
        if (h == null) return nearest;

        if (nearest.distanceSquaredTo(p) >= h.rect.distanceSquaredTo(p)) {
            if (h.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
                nearest = h.p;
            }

            if (h.right != null && h.right.rect.contains(p)) {
                nearest = getNearest(h.right, p, nearest);
                nearest = getNearest(h.left, p, nearest);
            }
            else {
                nearest = getNearest(h.left, p, nearest);
                nearest = getNearest(h.right, p, nearest);
            }
        }

        return nearest;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return getNearest(root, p, root.p);
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
        KdTree kdtree = new KdTree();

        for (int i = 0; i < ps.length; i++) {
            Point2D p = new Point2D(ps[i][0], ps[i][1]);
            kdtree.insert(p);
        }
        for (int i = 0; i < ps.length; i++) {
            Point2D p = new Point2D(ps[i][0], ps[i][1]);
            StdOut.println(kdtree.contains(p) + " kd has: " + p);
        }

        Point2D duplicate = new Point2D(0.975528, 0.345492);
        StdOut.println("POINT: " + duplicate + " IS DUPLICATE? " + kdtree.contains(duplicate));
        StdOut.println("SIZE BEFORE INSERTING A DUPLICATE: " + kdtree.size());
        kdtree.insert(duplicate);
        StdOut.println("SIZE AFTER INSERTING A DUPLICATE: " + kdtree.size());

        kdtree.draw();
    }
}
