package shape;

import game_info.Info;

import java.util.Random;

public class Asteroid extends Shape implements Info {

    public Asteroid(int x, int y) {
        super(ASTEROID_POINTS[new Random().nextInt(4)], x, y);
        Random rand = new Random();
        float theta = rand.nextFloat() * 2 * (float) Math.PI;
        setVelX((float) Math.cos(theta) * LARGE_ASTEROID_SPEED);
        setVelY((float) -Math.sin(theta) * LARGE_ASTEROID_SPEED);
    }

    public void tick() {
        translate(getVelX(), getVelY());
    }
}
