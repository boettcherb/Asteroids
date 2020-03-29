package asteroids.objects;

import asteroids.Info;
import asteroids.display.Handler;
import asteroids.util.Sound;
import java.util.Random;

public class UFO extends Shape implements Info {
    private UFO_Type type;
    private Random rand;
    private Handler handler;
    private int turnTimer, shootTimer;
    private Sound explosion, music;

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

    public void tick() {
        if (--turnTimer <= 0) {
            float val = rand.nextFloat();
            setVelY(val < 0.25 ? -UFO_SPEED : val >= 0.75 ? UFO_SPEED : 0);
            turnTimer = UFO_TURN_TIMER;
        }
        if (--shootTimer <= 0) {
            float theta;
            if (handler.getPlayer() != null) {
                float diffX = handler.getPlayer().getX() - getX();
                float diffY = handler.getPlayer().getY() - getY();
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
            theta += rand.nextFloat() * 0.4f - 0.2f;
            handler.addShape(new Bullet(getX(), getY(), theta, Bullet.BulletType.UFO_BULLET));
            shootTimer = UFO_SHOOT_TIMER;
        }
        translate(getVelX(), getVelY());
    }

    public boolean dead() {
        return getX() < 0 || getY() > CANVAS_WIDTH;
    }

    public UFO_Type getType() {
        return type;
    }

    public void destruct(boolean play) {
        if (play) {
            explosion.playSound(false);
        }
        music.endSound();
    }
}
