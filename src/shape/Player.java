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
        }
        translate(getVelX(), getVelY());
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
