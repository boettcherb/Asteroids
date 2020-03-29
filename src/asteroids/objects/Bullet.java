package asteroids.objects;

import asteroids.Info;
import asteroids.util.Point;
import asteroids.util.Line;

public class Bullet extends Shape implements Info {
    private BulletType type;
    private int life;
    private Point previousLocation;

    public enum BulletType {
        PLAYER_BULLET,
        UFO_BULLET
    }

    public Bullet(float x, float y, float theta, BulletType type) {
        super(BULLET_POINTS, x, y);
        this.type = type;
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

    public boolean isUFOBullet() {
        return type == BulletType.UFO_BULLET;
    }

    public boolean dead() {
        return life <= 0;
    }

    public void destruct(boolean play) {}
}
