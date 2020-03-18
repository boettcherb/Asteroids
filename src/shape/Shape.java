package shape;

import java.awt.Graphics;
import java.awt.Point;

public abstract class Shape {
    private Point[] points;
    private int X, Y;

    public Shape(Point[] points, int x, int y) {
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; ++i) {
            this.points[i] = new Point(points[i].x, points[i].y);
        }
        translate(x, y);
    }

    private void translate(int x, int y) {
        X += x;
        Y += y;
        for (Point point : points) {
            point.x += x;
            point.y += y;
        }
    }

    public abstract void tick();

    public void render(Graphics g) {
        for (int i = 0; i < points.length - 1; ++i) {
            g.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
        }
        int last = points.length - 1;
        g.drawLine(points[0].x, points[0].y, points[last].x, points[last].y);
    }

}
