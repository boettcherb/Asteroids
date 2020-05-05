package asteroids.util;

/**
 * A point is made up of 2 floats, which are coordinates on the screen. Each shape in the
 * game (the player, the asteroids, etc) is made up of an array of points.
 */
public class Point {
    private float X, Y;

    public Point(float x, float y) {
        X = x;
        Y = y;
    }

    /**
     * Move this point x units along the X-axis and y units along the y-axis
     * @param x the movement along the x-axis
     * @param y the movement along the y-axis
     */
    public void translate(float x, float y) {
        X += x;
        Y += y;
    }

    /**
     * Scale this point's coordinates by a certain factor. The x and y values of this point
     * are multiplied by the scale factor.
     * @param scaleValue the scale factor
     */
    public void scale(float scaleValue) {
        X *= scaleValue;
        Y *= scaleValue;
    }

    /**
     * Find the distance from this point to another point using the distance formula.
     * @param other the other point
     * @return the distance from this point to the other point
     */
    public float distTo(Point other) {
        float Xdist = X - other.getX();
        float Ydist = Y - other.getY();
        return (float) Math.sqrt(Xdist * Xdist + Ydist * Ydist);
    }

    // Getters and Setters

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }
}