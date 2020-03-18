package shape;

import util.Point;

import java.awt.Graphics;

public abstract class Shape {
    private Point[] points;
    private float X, Y;
    private float velX, velY;

    public Shape(Point[] points, int x, int y) {
        this.points = new Point[points.length + 1];
        for (int i = 0; i < points.length; ++i) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
        this.points[points.length] = new Point(points[0].getX(), points[0].getY());
        translate(x, y);
    }

    public void translate(float x, float y) {
        X += x;
        Y += y;
        for (Point point : points) {
            point.translate(x, y);
        }
    }

    public abstract void tick();

    public void render(Graphics g) {
        for (int i = 0; i < points.length - 1; ++i) {
            int x1 = (int) points[i].getX();
            int y1 = (int) points[i].getY();
            int x2 = (int) points[i + 1].getX();
            int y2 = (int) points[i + 1].getY();
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float val) {
        velX = val;
    }

    public void setVelY(float val) {
        velY = val;
    }
}
