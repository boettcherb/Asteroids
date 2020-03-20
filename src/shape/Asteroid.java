package shape;

import game_info.Info;

import java.util.Random;

public class Asteroid extends Shape implements Info {
    private AsteroidType type;

    public enum AsteroidType {
        Large,
        Medium,
        Small
    }

    public Asteroid(float x, float y, AsteroidType type) {
        super(ASTEROID_POINTS[new Random().nextInt(4)], x, y);
        this.type = type;
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

    public AsteroidType getType() {
        return type;
    }
}
