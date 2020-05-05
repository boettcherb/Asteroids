package asteroids.util;

/**
 * A line is made up of 2 points. In this project, each shape is made up of an array of points
 * and collisions are detected by finding intersections of the line segments between 2 points.
 */
public class Line {
    private final Point p1, p2;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * Determines if this line segment intersects with another line segment. This method
     * detects intersections by looking at the orientations of 3 of the 4 points that
     * define the 2 lines, and compares those orientations.
     * @param other The line to compare to this line
     * @return true if the other line intersects this line, false otherwise
     */
    public boolean intersects(Line other) {
        int o1 = orientation(p1, p2, other.p1);
        int o2 = orientation(p1, p2, other.p2);
        int o3 = orientation(other.p1, other.p2, p1);
        int o4 = orientation(other.p1, other.p2, p2);
        if (o1 != o2 && o3 != o4) {
            return true;
        }
        if (o1 == 0 && onSegment(p1, other.p1, p2)) {
            return true;
        }
        if (o2 == 0 && onSegment(p1, other.p2, p2)) {
            return true;
        }
        if (o3 == 0 && onSegment(other.p1, p1, other.p2)) {
            return true;
        }
        return o4 == 0 && onSegment(other.p1, p2, other.p2);
    }

    /**
     * Helper method to the intersects method, determines the orientation of 3 points
     * @param p point 1
     * @param q point 2
     * @param r point 3
     * @return 0, 1, or 2 depending on the orientation (clockwise, counterclockwise, or parallel)
     */
    private int orientation(Point p, Point q, Point r) {
        int px = (int) (p.getX() + 0.5);
        int py = (int) (p.getY() + 0.5);
        int qx = (int) (q.getX() + 0.5);
        int qy = (int) (q.getY() + 0.5);
        int rx = (int) (r.getX() + 0.5);
        int ry = (int) (r.getY() + 0.5);
        int val = (qy - py) * (rx - qx) - (qx - px) * (ry - qy);
        return val == 0 ? 0 : val > 0 ? 1 : 2;
    }

    /**
     * Helper method to the intersects method, finds if any triplet of points is collinear
     * @param p point 1
     * @param q point 2
     * @param r point 3
     * @return true if the 3 points are collinear, false otherwise
     */
    private boolean onSegment(Point p, Point q, Point r) {
        return q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
            q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY());
    }

    /**
     * Find and return the length of this line segment
     * @return the length of this line segment
     */
    public float length() {
        return p1.distTo(p2);
    }

    // Getters and Setters

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }
}