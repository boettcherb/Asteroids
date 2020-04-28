package asteroids.objects;

import asteroids.Info;
import asteroids.display.Handler;
import asteroids.util.Sound;
import java.util.Random;

/**
 * A UFO is a shape consisting of an array of points (that are defined in the Info class).
 * Each UFO has a type, either small or large, that is set in the constructor and used
 * to determine its speed and the sounds it makes.
 */
public class UFO extends Shape implements Info {
    private final UFO_Type type;
    private final Random rand;
    private final Handler handler;
    private int turnTimer, shootTimer;
    private final Sound explosion, music;

    public enum UFO_Type {
        LARGE,
        SMALL
    }

    public UFO(int x, int y, Handler handler) {
        super(UFO_POINTS, x, y);
        this.handler = handler;
        rand = new Random();
        if (rand.nextBoolean()) {
            type = UFO_Type.SMALL;
            scale(SMALL_UFO_SCALE);
        } else {
            type = UFO_Type.LARGE;
        }
        explosion = new Sound(type == UFO_Type.LARGE ? LARGE_EXPLOSION_SOUND_FILE : SMALL_EXPLOSION_SOUND_FILE);
        music = new Sound(type == UFO_Type.LARGE ? LARGE_UFO_SOUND_FILE : SMALL_UFO_SOUND_FILE);
        music.playSound(true);
        shootTimer = UFO_SHOOT_TIMER;
        setVelX(x == 0 ? UFO_SPEED : -UFO_SPEED);
    }

    /**
     * The tick method is called every frame by the Handler. Every tick, the timers for
     * turning and shooting are checked. If the turnTimer has reached 0, a new direction
     * is determined for the velocity of the UFO. If the shootTimer has reached 0, the
     * UFO calculates the direction of the player relative to itself and shoots in that
     * general direction.
     */
    public void tick() {
        if (--turnTimer <= 0) {
            float val = rand.nextFloat();
            setVelY(val < 0.25 ? -UFO_SPEED : val >= 0.75 ? UFO_SPEED : 0);
            turnTimer = UFO_TURN_TIMER;
        }
        if (--shootTimer <= 0) {
            float theta;
            // find the direction of the player relative to this UFO
            Player player = handler.getPlayer();
            if (player != null) {
                float diffX = player.getX() - getX();
                float diffY = player.getY() - getY();
                if (diffX == 0) {
                    diffX = EPSILON;
                }
                theta = (float) (Math.atan(Math.abs(diffY / diffX)) + Math.PI / 2.0);
                if (diffX < 0 && diffY < 0) {
                    theta += (float) Math.PI;
                } else if (diffY < 0) {
                    theta = (float) Math.PI - theta;
                } else if (diffX < 0) {
                    theta = 2 * (float) Math.PI - theta;
                }
            } else {
                theta = rand.nextFloat() * 2 * (float) Math.PI;
            }
            // shoot in the player's general direction
            theta += rand.nextFloat() * 0.4f - 0.2f;
            handler.addShape(new Bullet(getX(), getY(), theta, Bullet.BulletType.UFO_BULLET));
            shootTimer = UFO_SHOOT_TIMER;
        }
        translate(getVelX(), getVelY());
    }

    /**
     * Determines if the UFO has moved off the screen. If so, the UFO is removed from the game.
     * @return true if the UFO should be removed, false otherwise
     */
    public boolean dead() {
        return getX() < 0 || getY() > CANVAS_WIDTH;
    }

    /**
     * This is an abstract method in the shape class and is called whenever this shape is destroyed
     * or removed from the game. If the UFO is destroyed by the player or an asteroid, we want to
     * play the explosion sound, so the play boolean is true. If the UFO is removed from the game
     * because it made it all the way across the screen, no explosion sound should play. In either
     * case we want to end the annoying sound that tells us that a UFO is in the game.
     * @param play true if an explosion sound should play, false otherwise.
     */
    public void destruct(boolean play) {
        if (play) {
            explosion.playSound(false);
        }
        music.endSound();
    }

    // getter and setter methods

    public UFO_Type getType() {
        return type;
    }
}
