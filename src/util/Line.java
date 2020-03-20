package util;

public class Line {
    private final Point p1, p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public boolean intersects(Line other) {
        int o1 = orientation(p1, p2, other.p1);
        int o2 = orientation(p1, p2, other.p2);
        int o3 = orientation(other.p1, other.p2, p1);
        int o4 = orientation(other.p1, other.p2, p2);
        return (o1 != o2 && o3 != o4);
    }

    private int orientation(Point p, Point q, Point r) {
        int val = (int) (q.getY() - p.getY()) * (int) (r.getX() - q.getX()) -
            (int) (q.getX() - p.getX()) * (int) (r.getY() - q.getY());
        if (val == 0) return 0;
        return val > 0 ? 1 : 2;
    }
}