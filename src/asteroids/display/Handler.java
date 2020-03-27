package asteroids.display;

import asteroids.Info;
import asteroids.objects.*;
import asteroids.util.Line;
import asteroids.util.Point;
import asteroids.util.Sound;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Handler implements Info {
    private LinkedList<Shape> shapes, added, removed;
    private HUD hud;
    private Menu menu;
    private Player player;
    private int playerDeathTimer, playerSaveTimer, newLevelTimer, musicTimer;
    private PlayerExplosion playerExplosion;
    private Sound beat1, beat2;
    private long levelStartTime;
    private boolean firstBeat;

    public Handler(HUD hud, Menu menu) {
        this.hud = hud;
        this.menu = menu;
        shapes = new LinkedList<>();
        added = new LinkedList<>();
        removed = new LinkedList<>();
        beat1 = new Sound(BEAT1_SOUND_FILE);
        beat2 = new Sound(BEAT2_SOUND_FILE);
        firstBeat = true;
    }

    public void newGame() {
        shapes.clear();
        added.clear();
        removed.clear();
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        playerSaveTimer = PLAYER_SAVE_TIME;
        hud.newGame();
        newLevel();
    }

    private void newLevel() {
        levelStartTime = System.currentTimeMillis();
        hud.nextLevel();
        Random rand = new Random();
        for (int i = 0; i < hud.getLevel() + 3; ++i) {
            int x, y;
            do {
                x = rand.nextInt(CANVAS_WIDTH);
                y = rand.nextInt(CANVAS_HEIGHT);
            } while (new Point(x, y).distTo(new Point(player.getX(), player.getY())) < MIN_SPAWN_DIST_FROM_PLAYER);
            addShape(new Asteroid(x, y, Asteroid.AsteroidType.Large));
        }
        musicTimer = 0;
    }

    public void tick() {
        shapes.forEach(shape -> shape.tick());
        manageTimers();
        if (player == null) {
            playerExplosion.tick();
        } else {
            player.tick();
            checkPlayerCollisions();
        }
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

    private void checkBulletCollisions() {
        for (Shape shape : shapes) {
            if (shape instanceof Bullet && ((Bullet) shape).dead()) {
                removeShape(shape);
            }
        }
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
        shapes.forEach(shape -> shape.render(g));
        if (player == null) {
            playerExplosion.render(g);
        } else {
            if (playerSaveTimer == 0 || (playerSaveTimer / PLAYER_FLASH_TIME) % 2 == 0) {
                player.render(g);
            }
        }
    }

    private void manageTimers() {
        if (player == null) {
            if (--playerDeathTimer <= 0) {
                if (hud.getNumLives() == 0) {
                    menu.endGame();
                } else {
                    player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
                    playerSaveTimer = PLAYER_SAVE_TIME;
                }
            }
        } else {
            if (playerSaveTimer > 0) {
                --playerSaveTimer;
            }
        }
        if (newLevelTimer > 0) {
            if (--newLevelTimer <= 0) {
                newLevel();
            }
        }
        // play music when the timer runs out, but not when a new level is about to start
        if (--musicTimer <= 0 && newLevelTimer <= 0) {
            if (firstBeat) {
                beat1.playSound(false);
            } else {
                beat2.playSound(false);
            }
            firstBeat = !firstBeat;
            // decrease the time between beats (increase the speed of the music) every INC_TIME milliseconds
            musicTimer = TIME_BETWEEN_BEATS - (int) ((System.currentTimeMillis() - levelStartTime) / INC_TIME);
            // make sure the time between beats can't get too low
            musicTimer = Math.max(musicTimer, MIN_TIME_BETWEEN_BEATS);
        }
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
        if (noAsteroids() && newLevelTimer == 0) {
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
        playerExplosion = new PlayerExplosion(player.getX(), player.getY());
        player.destruct();
        player = null;
        playerDeathTimer = PLAYER_RESPAWN_TIME;
        hud.loseALife();
    }

    public Player getPlayer() {
        return player;
    }
}
