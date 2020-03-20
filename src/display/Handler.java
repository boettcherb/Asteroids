package display;

import game_info.Info;
import shape.*;
import util.Line;

import java.awt.Graphics;
import java.util.ArrayList;

public class Handler implements Info {
    private ArrayList<Shape> shapes;
    private Player player;
    private int playerDeathTimer;

    public Handler() {
        shapes = new ArrayList<>();
        shapes.add(new Asteroid(100, 300, Asteroid.AsteroidType.Large));
        shapes.add(new Asteroid(100, 300, Asteroid.AsteroidType.Small));
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
        checkPlayerCollisions();
        checkBulletLives();
        checkBulletCollisions();
    }

    private void checkPlayerCollisions() {
        for (int i = 0; i < shapes.size() && player != null; ++i) {
            if (shapes.get(i) instanceof Asteroid && player.intersects(shapes.get(i))) {
                destroyPlayer();
                removeShape(shapes.get(i));
            }
        }
    }

    private void checkBulletLives() {
        for (int i = 0; i < shapes.size(); ++i) {
            if (shapes.get(i) instanceof Bullet && ((Bullet) shapes.get(i)).dead()) {
                removeShape(shapes.get(i));
            }
        }
    }

    private void checkBulletCollisions() {
        for (int i = 0; i < shapes.size(); ++i) {
            if (shapes.get(i) instanceof Bullet) {
                Bullet bullet = (Bullet) shapes.get(i);
                for (int j = 0; j < shapes.size(); ++j) {
                    if (shapes.get(j) instanceof Asteroid) {
                        Asteroid asteroid = (Asteroid) shapes.get(j);
                        Line path = bullet.getPath();
                        if (asteroid.intersects(bullet) || asteroid.intersects(path)) {
                            removeShape(bullet);
                            removeShape(asteroid);
                            break;
                        }
                    }
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

    public void removeShape(Shape shape) {
        shapes.remove(shape);
        if (shape instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) shape;
            if (asteroid.getType() == Asteroid.AsteroidType.Large) {
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Medium));
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Medium));
            } else if (asteroid.getType() == Asteroid.AsteroidType.Medium) {
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Small));
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Small));
            }
        }
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
