package asteroids.objects;

import asteroids.Info;
import java.util.Random;

public class UFO extends Shape implements Info {
    private UFO_Type type;
    private Random rand;
    private int turnTimer = 0;

    public enum UFO_Type {
        LARGE,
        SMALL
    }

    public UFO(int x, int y) {
        super(UFO_POINTS, x, y);
        rand = new Random();
        if (rand.nextBoolean()) {
            type = UFO_Type.SMALL;
            scale(SMALL_UFO_SCALE);
        } else {
            type = UFO_Type.LARGE;
        }
        setVelX(x == 0 ? UFO_SPEED : -UFO_SPEED);
    }

    public void tick() {
        if (--turnTimer <= 0) {
            float Y = getY() / CANVAS_HEIGHT;
            if (rand.nextFloat() > 1 / ((1 - Y) * (1 - Y) / (Y * Y))) {
                setVelY(UFO_SPEED);
            } else {
                setVelY(-UFO_SPEED);
            }
            turnTimer = UFO_TURN_TIMER;
        }
        translate(getVelX(), getVelY());
    }

    public boolean dead() {
        return getX() < 0 || getY() > CANVAS_WIDTH;
    }

    public UFO_Type getType() {
        return type;
    }
}
