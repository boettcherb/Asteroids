package shape;

import game_info.Info;

public class Player extends Shape implements Info {
    private float theta;
    private boolean accelerate, turnRight, turnLeft;

    public Player(int x, int y) {
        super(PLAYER_POINTS, x, y);
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
        float x = getX() + (float) Math.sin(theta) * BULLET_STARTING_DIST;
        float y = getY() - (float) Math.cos(theta) * BULLET_STARTING_DIST;
        return new Bullet(x, y, theta);
    }

    public void setAccelerate(boolean accelerate) {
        this.accelerate = accelerate;
    }

    public void setTurnRight(boolean turnRight) {
        this.turnRight = turnRight;
    }

    public void setTurnLeft(boolean turnLeft) {
        this.turnLeft = turnLeft;
    }
}
