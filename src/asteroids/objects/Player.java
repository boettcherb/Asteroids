package asteroids.objects;

import asteroids.Info;
import asteroids.util.Sound;
import java.util.Random;

/**
 * The Player is a shape consisting of an array of points (defined in the
 * Info class). The player has a velocity and direction which change based
 * on inputs to the GUI's key listener.
 */
public class Player extends Shape implements Info {
    private float theta;
    private boolean accelerate, turnRight, turnLeft;
    private final Sound fire, thrust;

    public Player(int x, int y) {
        super(PLAYER_POINTS, x, y);
        fire = new Sound(FIRE_SOUND_FILE);
        thrust = new Sound(THRUST_SOUND_FILE);
    }

    /**
     * The tick method is called every frame by the Handler. Every frame,
     * the player determines if it should accelerate or turn based on
     * booleans set by the GUI's key listener.
     */
    public void tick() {
        if (accelerate) {
            setVelX(getVelX() + (float) Math.sin(theta) * PLAYER_ACCELERATION);
            setVelY(getVelY() - (float) Math.cos(theta) * PLAYER_ACCELERATION);
        } else {
            setVelX(Math.abs(getVelX()) < MIN_VELOCITY ? 0 : getVelX() * PLAYER_DECELERATION);
            setVelY(Math.abs(getVelY()) < MIN_VELOCITY ? 0 : getVelY() * PLAYER_DECELERATION);
        }
        if (!turnRight || !turnLeft) {
            if (turnRight) {
                theta += PLAYER_TURN_RATE;
                rotate(PLAYER_TURN_RATE);
            } else if (turnLeft) {
                theta -= PLAYER_TURN_RATE;
                rotate(-PLAYER_TURN_RATE);
            }
        }
        translate(getVelX(), getVelY());
    }

    /**
     * Called by the Handler whenever the key listener of the GUI detects that
     * the space bar has been pressed. This method plays the fire sound effect
     * and creates a bullet that has the player's current position and direction.
     * @return a bullet to be shot for the current player's position
     */
    public Bullet shoot() {
        fire.playSound(false);
        float x = getX() + (float) Math.sin(theta) * BULLET_STARTING_DIST;
        float y = getY() - (float) Math.cos(theta) * BULLET_STARTING_DIST;
        return new Bullet(x, y, theta, Bullet.BulletType.PLAYER_BULLET);
    }

    /**
     * When the player is accelerating, there is a thruster effect behind the
     * player that flickers on and off. The chance that the effect is on is
     * defined in the Info class (SHOW_THRUST_CHANCE).
     * @return true if the acceleration effect should show, false otherwise
     */
    public boolean showAcceleration() {
        return accelerate && new Random().nextFloat() < SHOW_THRUST_CHANCE;
    }

    /**
     * This method is called whenever the player is destroyed. If the player
     * is accelerating when it is destroyed, we want the thrust sound effect
     * to stop.
     * @param play unused in this method
     */
    public void destruct(boolean play) {
        thrust.endSound();
    }

    // Getters and Setters

    public boolean isAccelerating() {
        return accelerate;
    }

    public void setAccelerate(boolean accelerate) {
        this.accelerate = accelerate;
        if (accelerate) {
            // play the thrust sound if the player is accelerating
            thrust.playSound(true);
        } else {
            // when the player stops accelerating, stop playing the thrust sound
            thrust.endSound();
        }
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }

    public void setTurnLeft(boolean turnLeft) {
        this.turnLeft = turnLeft;
    }
}
