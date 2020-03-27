package asteroids.objects;

import asteroids.Info;
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
        Random rand = new Random();
        float theta = rand.nextFloat() * (float) Math.PI * 2;
        float speed;
        if (type == AsteroidType.Large) {
            speed = LARGE_ASTEROID_SPEED;
            scale(0.8f);
        } else if (type == AsteroidType.Medium) {
            speed = rand.nextFloat() * MAX_MEDIUM_ASTEROID_SPEED + MIN_ASTEROID_SPEED;
            scale(0.4f);
        } else {
            speed = rand.nextFloat() * MAX_SMALL_ASTEROID_SPEED + MIN_ASTEROID_SPEED;
            scale(0.20f);
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
