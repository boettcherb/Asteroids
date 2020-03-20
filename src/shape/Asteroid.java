package shape;

import game_info.Info;

import java.util.Random;

public class Asteroid extends Shape implements Info {

    public enum AsteroidType {
        Large,
        Medium,
        Small
    }

    public Asteroid(int x, int y, AsteroidType type) {
        super(ASTEROID_POINTS[new Random().nextInt(4)], x, y);
        float theta = (new Random()).nextFloat() * (float) Math.PI * 2;
        float speed;
        if (type == AsteroidType.Large) {
            speed = LARGE_ASTEROID_SPEED;
        } else if (type == AsteroidType.Medium) {
            speed = MEDIUM_ASTEROID_SPEED;
            scale(0.5f);
        } else {
            speed = SMALL_ASTEROID_SPEED;
            scale(0.25f);
        }
        setVelX((float) Math.cos(theta) * speed);
        setVelY((float) -Math.sin(theta) * speed);
    }

    public void tick() {
        translate(getVelX(), getVelY());
    }
}
