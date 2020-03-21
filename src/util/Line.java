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
        if (o1 != o2 && o3 != o4) {
            return true;
        }
        if (o1 == 0 && onSegment(p1, other.p1, p2)) return true;
        if (o2 == 0 && onSegment(p1, other.p2, p2)) return true;
        if (o3 == 0 && onSegment(other.p1, p1, other.p2)) return true;
        return o4 == 0 && onSegment(other.p1, p2, other.p2);
    }

    private int orientation(Point p, Point q, Point r) {
        int px = (int) (p.getX() + 0.5);
        int py = (int) (p.getY() + 0.5);
        int qx = (int) (q.getX() + 0.5);
        int qy = (int) (q.getY() + 0.5);
        int rx = (int) (r.getX() + 0.5);
        int ry = (int) (r.getY() + 0.5);
        int val = (qy - py) * (rx - qx) - (qx - px) * (ry - qy);
        if (val == 0) return 0;
        return val > 0 ? 1 : 2;
    }

    private boolean onSegment(Point p, Point q, Point r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
            q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());
    }

    public float length() {
        return p1.distTo(p2);
    }
}