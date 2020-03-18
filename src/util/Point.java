package util;

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

    public float getX() {
        return X;
    }

    public float getY() {
        return Y;
    }
}