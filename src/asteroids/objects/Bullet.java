package asteroids.objects;

import asteroids.Info;
import asteroids.util.Point;
import asteroids.util.Line;

/**
 * A Bullet is a shape consisting of an array of points (defined in the Info class) Each
 * bullet has a type (either a player bullet or a UFO bullet) that is set in the
 * constructor and used by the Handler to determine collisions.
 */
public class Bullet extends Shape implements Info {
    private final BulletType type;
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

    /**
     * This method is called every frame by the handler. Each frame, the bullet
     * travels to a new position based on its velocity, resets its previous
     * location for the getLine() method, and decreases its life.
     */
    public void tick() {
        previousLocation = new Point(getX(), getY());
        translate(getVelX(), getVelY());
        --life;
    }

    /**
     * This method retrieves the line segment connecting the bullet's current
     * position to its previous position. This method is used in collision
     * detection, since the bullet will often pass over the lines of other
     * shapes between each frame.
     * @return a line connecting the bullet's current and previous points
     */
    public Line getPath() {
        Point currentLocation = new Point(getX(), getY());
        return new Line(previousLocation, currentLocation);
    }

    /**
     * This method tells the caller if this bullet came from the
     * player or from a UFO.
     * @return true if this is a UFO bullet, false otherwise
     */
    public boolean isUFOBullet() {
        return type == BulletType.UFO_BULLET;
    }

    /**
     * The life of a bullet is how many ticks it stays alive. The life
     * of a bullet is given an initial value in the constructor and every
     * tick, the life is decreased. When the life reaches 0, the bullet is
     * destroyed.
     * @return true if the bullet has no more life, false otherwise
     */
    public boolean dead() {
        return life <= 0;
    }

    // unused abstract method from the Shape class
    public void destruct(boolean play) {}
}
