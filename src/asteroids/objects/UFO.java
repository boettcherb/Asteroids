package asteroids.objects;

import asteroids.Info;
import asteroids.util.Sound;
import java.util.Random;

public class UFO extends Shape implements Info {
    private UFO_Type type;
    private Random rand;
    private int turnTimer;
    Sound ufoSound;

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
        ufoSound = new Sound(type == UFO_Type.LARGE ? LARGE_UFO_SOUND_FILE : SMALL_UFO_SOUND_FILE);
        ufoSound.playSound(true);
        setVelX(x == 0 ? UFO_SPEED : -UFO_SPEED);
    }

    public void tick() {
        if (--turnTimer <= 0) {
            float val = rand.nextFloat();
            setVelY(val < 0.25 ? -UFO_SPEED : val >= 0.75 ? UFO_SPEED : 0);
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

    public void destruct() {
        ufoSound.endSound();
    }
}
