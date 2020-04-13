package asteroids.objects;

import asteroids.Info;
import java.util.Random;

/**
 * Debris Particles are Shapes that consist of an array of points (defined in the Info
 * class. Debris particles spawn in when other shapes (the player, asteroids, and UFOs)
 * are destroyed. They only last for about a second, but they create a nice explosion
 * effect.
 */
public class DebrisParticle extends Shape implements Info {
    int life;

    public DebrisParticle(Shape shape, int life) {
        super(DEBRIS_PARTICLE_POINTS, shape.getX(), shape.getY());
        Random rand = new Random();
        setVelX(rand.nextFloat() * MAX_DEBRIS_PARTICLE_SPEED - MAX_DEBRIS_PARTICLE_SPEED / 2);
        setVelY(rand.nextFloat() * MAX_DEBRIS_PARTICLE_SPEED - MAX_DEBRIS_PARTICLE_SPEED / 2);
        this.life = life;
    }

    /**
     * This method is called by the Handler every frame. Each tick, the debris
     * particle is moved to a new location (based on its velocity) and its life
     * is decreased.
     */
    public void tick() {
        --life;
        translate(getVelX(), getVelY());
    }

    /**
     * In the constructor, the life of the debris particle is given an initial value.
     * Every tick, the life of the debris particle decreases until 0. At that point,
     * it is destroyed.
     * @return true if the debris particle has no more life, false otherwise
     */
    public boolean dead() {
        return life <= 0;
    }

    // unused abstract method from the shape class
    public void destruct(boolean play) {}
}
