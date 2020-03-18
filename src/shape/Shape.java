package shape;

import util.Point;
import game_info.Info;

import java.awt.Graphics;

public abstract class Shape implements Info {
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
        translatePoints(x, y);
        if (X > MAX_X) {
            X -= SHIFT_X;
            translatePoints(-SHIFT_X, 0);
        }
        if (X < MIN_X) {
            X += SHIFT_X;
            translatePoints(SHIFT_X, 0);
        }
        if (Y > MAX_Y) {
            Y -= SHIFT_Y;
            translatePoints(0, -SHIFT_Y);
        }
        if (Y < MIN_Y) {
            Y += SHIFT_Y;
            translatePoints(0, SHIFT_Y);
        }
    }

    private void translatePoints(float dx, float dy) {
        for (Point point : points) {
            point.translate(dx, dy);
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

    void rotate(float d_theta) {
        Point center = new Point(X, Y);
        for (Point point : points) {
            float dx = point.getX() - X; // x-dist from center
            float dy = point.getY() - Y; // y-dist from center
            if (dx == 0) {
                dx += 1e-6; // prevent divide by zero
            }
            double theta;
            if (dy >= 0) {
                if (dx > 0) { // first quadrant
                    theta = Math.atan(dy / dx);
                } else { // second quadrant
                    theta = Math.PI - Math.atan(dy / -dx);
                }
            } else {
                if (dx < 0) { // third quadrant
                    theta = Math.PI + Math.atan(-dy / -dx);
                } else { // fourth quadrant
                    theta = -Math.atan(-dy / dx);
                }
            }
            theta += d_theta;
            float radius = point.distTo(center);
            point.translate(-dx, -dy);
            float x = radius * (float) Math.cos(theta);
            float y = radius * (float) Math.sin(theta);
            point.translate(x, y);
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
