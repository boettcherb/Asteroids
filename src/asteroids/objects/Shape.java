package asteroids.objects;

import asteroids.Info;
import asteroids.util.Line;
import asteroids.util.Point;
import java.awt.Graphics;

public abstract class Shape implements Info {
    private Point[] points;
    private float X, Y;
    private float velX, velY;
    private float bufferX, bufferY;

    public Shape(Point[] points, float x, float y) {
        this.points = new Point[points.length + 1];
        for (int i = 0; i < points.length; ++i) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
        this.points[points.length] = new Point(points[0].getX(), points[0].getY());
        translate(x, y);
    }

    public void translate(float x, float y) {
        calculateDimensions();
        X += x;
        Y += y;
        float SHIFT_X = CANVAS_WIDTH + 2 * bufferX;
        float SHIFT_Y = CANVAS_HEIGHT + 2 * bufferY;
        translatePoints(x, y);
        if (X > CANVAS_HEIGHT + bufferY) {
            X -= SHIFT_X;
            translatePoints(-SHIFT_X, 0);
        }
        if (X < -bufferX) {
            X += SHIFT_X;
            translatePoints(SHIFT_X, 0);
        }
        if (Y > CANVAS_HEIGHT + bufferY) {
            Y -= SHIFT_Y;
            translatePoints(0, -SHIFT_Y);
        }
        if (Y < -bufferY) {
            Y += SHIFT_Y;
            translatePoints(0, SHIFT_Y);
        }
    }

    private void calculateDimensions() {
        float minX = Integer.MAX_VALUE;
        float maxX = Integer.MIN_VALUE;
        float minY = Integer.MAX_VALUE;
        float maxY = Integer.MIN_VALUE;
        for (Point point : points) {
            minX = Math.min(minX, point.getX());
            maxX = Math.max(maxX, point.getX());
            minY = Math.min(minY, point.getY());
            maxY = Math.max(maxY, point.getY());
        }
        bufferX = (maxX - minX) / 2;
        bufferY = (maxY - minY) / 2;
    }

    private void translatePoints(float dx, float dy) {
        for (Point point : points) {
            point.translate(dx, dy);
        }
    }

    public abstract void tick();

    public void render(Graphics g) {
        for (int i = 0; i < points.length - 1; ++i) {
            if (this instanceof Player) {
                Player player = (Player) this;
                if (i >= FIRST_AC_POINT && i <= LAST_AC_POINT) {
                    if (!player.isAccelerating() || !player.showAcceleration()) {
                        continue;
                    }
                }
            }
            int x1 = (int) points[i].getX();
            int y1 = (int) points[i].getY();
            int x2 = (int) points[i + 1].getX();
            int y2 = (int) points[i + 1].getY();
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public void rotate(float d_theta) {
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

    public boolean intersects(Shape other) {
        for (int i = 0; i < points.length - 1; ++i) {
            Line line = new Line(points[i], points[i + 1]);
            for (int j = 0; j < other.points.length - 1; ++j) {
                Line otherLine = new Line(other.points[j], other.points[j + 1]);
                if (line.intersects(otherLine)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean intersects(Line line) {
        for (int i = 0; i < points.length - 1; ++i) {
            if (line.intersects(new Line(points[i], points[i + 1]))) {
                return true;
            }
        }
        return false;
    }

    public void scale(float scaleValue) {
        for (Point point : points) {
            point.translate(-X, -Y);
            point.scale(scaleValue);
            point.translate(X, Y);
        }
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
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
