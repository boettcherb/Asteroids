package asteroids.util;

public class Point {
    private float X, Y;

    public Point(float x, float y) {
        X = x;
        Y = y;
    }

    public void translate(float x, float y) {
        X += x;
        Y += y;
    }

    public void scale(float scaleValue) {
        X *= scaleValue;
        Y *= scaleValue;
    }

    public float distTo(Point other) {
        float Xdist = X - other.getX();
        float Ydist = Y - other.getY();
        return (float) Math.sqrt(Xdist * Xdist + Ydist * Ydist);
    }

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }
}