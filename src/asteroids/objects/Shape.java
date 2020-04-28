package asteroids.objects;

import asteroids.Info;
import asteroids.util.Line;
import asteroids.util.Point;
import java.awt.Graphics;

/**
 * The Shape class is an abstract class that is the superclass of all the objects in the
 * game. Each object (the player, the asteroids, the UFOs, etc) is a shape that contains
 * an array of points, a position, and a velocity.
 */
public abstract class Shape implements Info {
    private final Point[] points;
    private float X, Y;
    private float velX, velY;
    private float bufferX, bufferY;

    public Shape(Point[] points, float x, float y) {
        this.points = new Point[points.length];
        for (int i = 0; i < points.length; ++i) {
            this.points[i] = new Point(points[i].getX(), points[i].getY());
        }
        translate(x, y);
    }

    /**
     * Move the shape x units along the x-axis and y units along the y axis. For
     * each point in the shape, x is added to the point's x value and y is added
     * to the point's y value. If the resulting position of the shape is off the
     * canvas, the shape wraps around and appears on the other side.
     * @param x the distance to move in the x direction
     * @param y the distance to move in the y direction
     */
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

    /**
     * This method calculates the size of a shape (it calculates the x distance
     * across a shape and the y distance across a shape). This value is used
     * for wrap-around, so that as soon as the entire shape is off the screen
     * it appears on the other side.
     */
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

    /**
     * This method calls the translate method for every point in the shape,
     * which moves every point a distance of dx along the x axis and dy
     * along the y axis.
     * @param dx the distance to move in the x direction
     * @param dy the distance to move in the y direction
     */
    private void translatePoints(float dx, float dy) {
        for (Point point : points) {
            point.translate(dx, dy);
        }
    }

    // methods that are different for every shape
    public abstract void destruct(boolean play);
    public abstract void tick();

    /**
     * The render method is called when it is time to draw to the canvas. Drawing a shape
     * involves drawing between each consecutive pair of points. For example, the first
     * and second points have a line between them, the second and third points have a
     * line between them, and so on.
     * @param g the graphics object of the canvas.
     */
    public void render(Graphics g) {
        for (int i = 0; i < points.length; ++i) {
            if (this instanceof Player) {
                Player player = (Player) this;
                if (i >= FIRST_AC_POINT && i <= LAST_AC_POINT) {
                    if (!player.isAccelerating() || !player.showAcceleration()) {
                        continue;
                    }
                }
            }
            // allow the shape to close by connecting a line from the last point to the first point.
            int next = i + 1 == points.length ? 0 : i + 1;
            int x1 = (int) points[i].getX();
            int y1 = (int) points[i].getY();
            int x2 = (int) points[next].getX();
            int y2 = (int) points[next].getY();
            g.drawLine(x1, y1, x2, y2);
        }
    }

    /**
     * Rotate the shape by an angle of d_theta radians. A shape is rotated by completing
     * the following process for all of its points: first find the
     * @param d_theta the angle to rotate the shape, in radians.
     */
    public void rotate(float d_theta) {
        Point center = new Point(X, Y);
        for (Point point : points) {
            float dx = point.getX() - X; // x-dist from center
            float dy = point.getY() - Y; // y-dist from center
            if (dx == 0) {
                dx = EPSILON; // prevent divide by zero
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

    /**
     * This method checks for an intersection between this shape and another shape.
     * To detect an intersection, each line of the shape is compared to each line of
     * the other shape with the intersects() method of the Line class. That method can
     * detect intersections between two lines segments.
     * @param other the other shape with which we are checking for an intersection
     * @return true if there is an intersection with the other shape, false otherwise
     */
    public boolean intersects(Shape other) {
        for (int i = 0; i < points.length; ++i) {
            int nextI = i + 1 == points.length ? 0 : i + 1;
            Line line = new Line(points[i], points[nextI]);
            for (int j = 0; j < other.points.length; ++j) {
                int nextJ = j + 1 == other.points.length ? 0 : j + 1;
                Line otherLine = new Line(other.points[j], other.points[nextJ]);
                if (line.intersects(otherLine)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method checks for an intersection between this shape an a line. Each line of this
     * shape is compared to the line using the intersects() method of the Line class.
     * @param line the line with which we are shcking for an intersection
     * @return true if there is an intersection with the line, false otherwise
     */
    public boolean intersects(Line line) {
        for (int i = 0; i < points.length; ++i) {
            int next = i + 1 == points.length ? 0 : i + 1;
            if (line.intersects(new Line(points[i], points[next]))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Scale a shape in size by a factor of scaleValue. (If scaleValue is 2 then the shape
     * will double in size). To scale a shape, the center of the shape is moved to the
     * origin (0, 0), the x and y values of the shape are multiplied by scaleValue, then
     * the shape is moved back to its original position
     * @param scaleValue the scale factor
     */
    public void scale(float scaleValue) {
        for (Point point : points) {
            point.translate(-X, -Y);
            point.scale(scaleValue);
            point.translate(X, Y);
        }
    }

    // getters and setters

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
