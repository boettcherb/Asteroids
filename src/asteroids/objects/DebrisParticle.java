package asteroids.objects;

import asteroids.Info;
import java.util.Random;

public class DebrisParticle extends Shape implements Info {
    int life;

    public DebrisParticle(Shape shape, int life) {
        super(DEBRIS_PARTICLE_POINTS, shape.getX(), shape.getY());
        Random rand = new Random();
        setVelX(rand.nextFloat() * MAX_DEBRIS_PARTICLE_SPEED - MAX_DEBRIS_PARTICLE_SPEED / 2);
        setVelY(rand.nextFloat() * MAX_DEBRIS_PARTICLE_SPEED - MAX_DEBRIS_PARTICLE_SPEED / 2);
        this.life = life;
    }

    public void tick() {
        --life;
        translate(getVelX(), getVelY());
    }

    public boolean dead() {
        return life <= 0;
    }
}
