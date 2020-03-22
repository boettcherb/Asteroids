package display;

import game_info.Info;
import shape.*;
import util.Line;
import util.Point;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Handler implements Info {
    private LinkedList<Shape> shapes;
    private LinkedList<Shape> added, removed;
    private HUD hud;
    private Menu menu;
    private Player player;
    private int playerDeathTimer, playerSaveTimer, newLevelTimer;
    private int level;

    public Handler(HUD hud, Menu menu) {
        this.hud = hud;
        this.menu = menu;
        shapes = new LinkedList<>();
        added = new LinkedList<>();
        removed = new LinkedList<>();
    }

    public void newGame() {
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        playerSaveTimer = PLAYER_SAVE_TIME;
        hud.newGame();
        level = 1;
        newLevel(level);
    }

    public void newLevel(int level) {
        shapes.clear();
        added.clear();
        removed.clear();
        Random rand = new Random();
        for (int i = 0; i < level + 3; ++i) {
            int x, y;
            do {
                x = rand.nextInt(CANVAS_WIDTH);
                y = rand.nextInt(CANVAS_HEIGHT);
            } while (new Point(x, y).distTo(new Point(player.getX(), player.getY())) < 200);
            addShape(new Asteroid(x, y, Asteroid.AsteroidType.Large));
        }
    }

    public void tick() {
        if (player == null) {
            if (playerDeathTimer-- < 0) {
                player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
                playerSaveTimer = PLAYER_SAVE_TIME;
            }
        } else {
            player.tick();
            checkPlayerCollisions();
        }
        if (newLevelTimer > 0) {
            if (--newLevelTimer == 0) {
                newLevel(++level);
            }
        }
        shapes.forEach(shape -> shape.tick());
        checkBulletLives();
        checkBulletCollisions();
        resetLists();
    }

    private void checkPlayerCollisions() {
        if (playerSaveTimer <= 0) {
            for (Shape shape : shapes) {
                if (shape instanceof Asteroid && player.intersects(shape)) {
                    destroyPlayer();
                    removeShape(shape);
                    break;
                }
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
                if (asteroid.intersects(bullet) || (path.length() < MAX_BULLET_PATH_LENGTH && asteroid.intersects(path))) {
                    removeShape(bullet);
                    removeShape(asteroid);
                    break;
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
            if (playerSaveTimer > 0) {
                --playerSaveTimer;
                if ((playerSaveTimer / PLAYER_FLASH_TIME) % 2 == 1) {
                    player.render(g);
                }
            } else {
                player.render(g);
            }
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
                hud.addToScore(LARGE_SCORE);
            } else if (asteroid.getType() == Asteroid.AsteroidType.Medium) {
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Small));
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Small));
                hud.addToScore(MEDIUM_SCORE);
            } else {
                hud.addToScore(SMALL_SCORE);
            }
        }
        if (noAsteroids()) {
            newLevelTimer = TIME_BETWEEN_LEVELS;
        }
    }

    private boolean noAsteroids() {
        for (Shape shape : shapes) {
            if (shape instanceof Asteroid && !removed.contains(shape)) {
                return false;
            }
        }
        for (Shape shape : added) {
            if (shape instanceof Asteroid) {
                return false;
            }
        }
        return true;
    }

    private void destroyPlayer() {
        player = null;
        playerDeathTimer = PLAYER_RESPAWN_TIME;
        if (hud.loseALife()) {
            menu.endGame();
        }
    }

    public Player getPlayer() {
        return player;
    }
}
