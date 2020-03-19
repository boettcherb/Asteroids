package shape;

import game_info.Info;

public class Bullet extends Shape implements Info {

    public Bullet(float x, float y, float theta) {
        super(BULLET_POINTS, x, y);
        setVelX((float) Math.sin(theta) * BULLET_SPEED);
        setVelY((float) -Math.cos(theta) * BULLET_SPEED);
    }

    public void tick() {
        translate(getVelX(), getVelY());
    }
}
