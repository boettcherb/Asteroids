package display;

import game_info.Info;
import shape.*;

import java.awt.Graphics;
import java.util.ListIterator;
import java.util.ArrayList;

public class Handler implements Info {
    private ArrayList<Shape> shapes;
    private Player player;
    private int playerDeathTimer;

    public Handler() {
        shapes = new ArrayList<>();
        shapes.add(new Asteroid(100, 300));
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
    }

    public void tick() {
        if (player == null) {
            if (playerDeathTimer-- < 0) {
                player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
            }
        } else {
            player.tick();
        }
        for (int i = 0; i < shapes.size(); ++i) {
            shapes.get(i).tick();
        }
        checkBulletLives();
        checkPlayerCollisions();
    }

    private void checkBulletLives() {
        ListIterator<Shape> itr = shapes.listIterator();
        while (itr.hasNext()) {
            Shape shape = itr.next();
            if (shape instanceof Bullet && ((Bullet) shape).dead()) {
                itr.remove();
            }
        }
    }

    private void checkPlayerCollisions() {
        if (player != null) {
            for (int i = 0; i < shapes.size(); ++i) {
                if (shapes.get(i) instanceof Asteroid && player.intersects(shapes.get(i))) {
                    destroyPlayer();
                    return;
                }
            }
        }
    }

    public void render(Graphics g) {
        if (player != null) {
            player.render(g);
        }
        for (int i = 0; i < shapes.size(); ++i) {
            shapes.get(i).render(g);
        }
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public void destroyPlayer() {
        player = null;
        playerDeathTimer = PLAYER_LIFE;
    }

    public Player getPlayer() {
        return player;
    }

    public void clearAll() {
        shapes.clear();
    }
}
