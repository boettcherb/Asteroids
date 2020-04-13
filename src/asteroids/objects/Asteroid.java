package asteroids.objects;

import asteroids.Info;
import asteroids.util.Sound;
import java.util.Random;

/**
 * An asteroid is a shape consisting of an array of points (which are defined
 * in the Info class. Each asteroid has a type (large, medium, or small) which
 * defines its size, velocity, and explosion sound.
 */
public class Asteroid extends Shape implements Info {
    private final AsteroidType type;
    private final Sound explosion;

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
            explosion = new Sound(LARGE_EXPLOSION_SOUND_FILE);
        } else if (type == AsteroidType.Medium) {
            speed = rand.nextFloat() * MAX_MEDIUM_ASTEROID_SPEED + MIN_ASTEROID_SPEED;
            scale(0.4f);
            explosion = new Sound(MEDIUM_EXPLOSION_SOUND_FILE);
        } else {
            speed = rand.nextFloat() * MAX_SMALL_ASTEROID_SPEED + MIN_ASTEROID_SPEED;
            scale(0.20f);
            explosion = new Sound(SMALL_EXPLOSION_SOUND_FILE);
        }
        setVelX((float) Math.cos(theta) * speed);
        setVelY((float) -Math.sin(theta) * speed);
    }

    /**
     * The tick method is called by the Handler every frame for every asteroid.
     * Each tick, the asteroid only changes its position by its velocity.
     */
    public void tick() {
        translate(getVelX(), getVelY());
    }

    /**
     * This method is called by the Handler before an asteroid is destroyed.
     * When an asteroid is destroyed, an explosion sound is played. However,
     * when the game ends, the asteroids are also "destroyed" but we do not
     * want to play the explosion sound.
     * @param play true if we want to play the sound, false otherwise
     */
    public void destruct(boolean play) {
        if (play) {
            explosion.playSound(false);
        }
    }

    // Getter methods

    public AsteroidType getType() {
        return type;
    }
}
