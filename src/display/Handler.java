package display;

import game_info.Info;
import shape.*;
import util.Line;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler implements Info {
    private LinkedList<Shape> shapes;
    private LinkedList<Shape> added, removed;
    private Player player;
    private int playerDeathTimer;

    public Handler() {
        shapes = new LinkedList<>();
        added = new LinkedList<>();
        removed = new LinkedList<>();
        addShape(new Asteroid(100, 300, Asteroid.AsteroidType.Large));
        addShape(new Asteroid(100, 300, Asteroid.AsteroidType.Small));
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
    }

    public void tick() {
        if (player == null) {
            if (playerDeathTimer-- < 0) {
                player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
            }
        } else {
            player.tick();
            checkPlayerCollisions();
        }
        shapes.forEach(shape -> shape.tick());
        checkBulletLives();
        checkBulletCollisions();
        resetLists();
    }

    private void checkPlayerCollisions() {
        for (Shape shape : shapes) {
            if (shape instanceof Asteroid && player.intersects(shape)) {
                destroyPlayer();
                removeShape(shape);
                break;
            }
        }
    }

    private void checkBulletLives() {
        for (Shape shape : shapes) {
            if (shape instanceof Bullet && ((Bullet) shape).dead()) {
                removeShape(shape);
            }
        }
    }

    private void checkBulletCollisions() {
        for (Shape shapeI : shapes) if (shapeI instanceof Bullet) {
            Bullet bullet = (Bullet) shapeI;
            for (Shape shapeJ : shapes) if (shapeJ instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) shapeJ;
                Line path = bullet.getPath();
                if (asteroid.intersects(bullet) || asteroid.intersects(path)) {
                    removeShape(bullet);
                    removeShape(asteroid);
                }
            }
        }
    }

    private void resetLists() {
        for (Shape shape : shapes) {
            if (!removed.contains(shape)) {
                added.add(shape);
            }
        }
        shapes = added;
        added = new LinkedList<>();
        removed.clear();
    }

    public void render(Graphics g) {
        if (player != null) {
            player.render(g);
        }
        shapes.forEach(shape -> shape.render(g));
    }

    public void addShape(Shape shape) {
        added.add(shape);
    }

    public void removeShape(Shape shape) {
        removed.add(shape);
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

    private void destroyPlayer() {
        player = null;
        playerDeathTimer = PLAYER_LIFE;
    }

    public Player getPlayer() {
        return player;
    }

    public void clearAll() {
        shapes.clear();
        added.clear();
        removed.clear();
    }
}
