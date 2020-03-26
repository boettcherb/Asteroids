package shape;

import game_info.Info;
import util.Sound;

public class Player extends Shape implements Info {
    private float theta;
    private boolean accelerate, turnRight, turnLeft;
    private Sound fire, thrust;

    public Player(int x, int y) {
        super(PLAYER_POINTS, x, y);
        fire = new Sound(FIRE_SOUND_FILE);
        thrust = new Sound(THRUST_SOUND_FILE);
    }

    public void tick() {
        if (accelerate) {
            setVelX(getVelX() + (float) Math.sin(theta) * PLAYER_ACCELERATION);
            setVelY(getVelY() - (float) Math.cos(theta) * PLAYER_ACCELERATION);
        } else {
            setVelX(Math.abs(getVelX()) < MIN_VELOCITY ? 0 : getVelX() * PLAYER_DECELERATION);
            setVelY(Math.abs(getVelY()) < MIN_VELOCITY ? 0 : getVelY() * PLAYER_DECELERATION);
        }
        if (turnRight) {
            theta += PLAYER_TURN_RATE;
            rotate(PLAYER_TURN_RATE);
        }
        if (turnLeft) {
            theta -= PLAYER_TURN_RATE;
            rotate(-PLAYER_TURN_RATE);
        }
        translate(getVelX(), getVelY());
    }

    public Bullet shoot() {
        fire.playSound(false);
        float x = getX() + (float) Math.sin(theta) * BULLET_STARTING_DIST;
        float y = getY() - (float) Math.cos(theta) * BULLET_STARTING_DIST;
        return new Bullet(x, y, theta);
    }

    public boolean isAccelerating() {
        return accelerate;
    }

    public void setAccelerate(boolean accelerate) {
        this.accelerate = accelerate;
        if (accelerate) {
            thrust.playSound(true);
        } else {
            thrust.endSound();
        }
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }

    public void setTurnLeft(boolean turnLeft) {
        this.turnLeft = turnLeft;
    }

    public void destruct() {
        thrust.endSound();
        fire.endSound();
    }
}
