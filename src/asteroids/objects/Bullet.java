package asteroids.objects;

import asteroids.Info;
import asteroids.util.Point;
import asteroids.util.Line;

public class Bullet extends Shape implements Info {
    private int life;
    private Point previousLocation;

    public Bullet(float x, float y, float theta) {
        super(BULLET_POINTS, x, y);
        life = BULLET_LIFE;
        setVelX((float) Math.sin(theta) * BULLET_SPEED);
        setVelY((float) -Math.cos(theta) * BULLET_SPEED);
        previousLocation = new Point(x, y);
    }

    public void tick() {
        previousLocation = new Point(getX(), getY());
        translate(getVelX(), getVelY());
        --life;
    }

    public Line getPath() {
        Point currentLocation = new Point(getX(), getY());
        return new Line(previousLocation, currentLocation);
    }

    public boolean dead() {
        return life <= 0;
    }

    public void destruct() {}
}
