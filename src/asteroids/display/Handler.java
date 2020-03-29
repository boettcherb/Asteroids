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
    private Random rand;
    private HUD hud;
    private Menu menu;
    private Player player;
    private int playerDeathTimer, playerSaveTimer, newLevelTimer, musicTimer;
    private PlayerExplosion playerExplosion;
    private Sound beat1, beat2;
    private int ticks;
    private boolean firstBeat, ufoInGame;

    public Handler(HUD hud, Menu menu) {
        this.hud = hud;
        this.menu = menu;
        rand = new Random();
        shapes = new LinkedList<>();
        added = new LinkedList<>();
        removed = new LinkedList<>();
        beat1 = new Sound(BEAT1_SOUND_FILE);
        beat2 = new Sound(BEAT2_SOUND_FILE);
    }

    public void newGame() {
        clearAll();
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        playerSaveTimer = PLAYER_SAVE_TIME;
        hud.newGame();
        newLevel();
    }

    private void newLevel() {
        if (player == null) {
            player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        }
        ticks = 0;
        hud.nextLevel();
        for (int i = 0; i < hud.getLevel() + 3; ++i) {
            int x, y;
            do {
                x = rand.nextInt(CANVAS_WIDTH);
                y = rand.nextInt(CANVAS_HEIGHT);
            } while (new Point(x, y).distTo(new Point(player.getX(), player.getY())) < MIN_SPAWN_DIST_FROM_PLAYER);
            addShape(new Asteroid(x, y, Asteroid.AsteroidType.Large));
        }
        musicTimer = 0;
        firstBeat = true;
    }

    public void tick() {
        ++ticks;
        shapes.forEach(shape -> shape.tick());
        float ufo_chance = ticks * UFO_SPAWN_CHANCE;
        if (!ufoInGame && rand.nextFloat() < ufo_chance) {
            addShape(new UFO(rand.nextBoolean() ? 0 : CANVAS_WIDTH, rand.nextInt(CANVAS_HEIGHT), this));
            ufoInGame = true;
        }
        manageTimers();
        if (player == null) {
            playerExplosion.tick();
        } else {
            player.tick();
        }
        checkLives();
        checkCollisions();
        resetLists();
    }

    private void checkCollisions() {
        // check player collisions
        if (player != null && playerSaveTimer <= 0) {
            for (Shape shape : shapes) {
                if (shape instanceof Bullet) {
                    Bullet bullet = (Bullet) shape;
                    if ((player.intersects(bullet) || player.intersects(bullet.getPath())) && bullet.isUFOBullet()) {
                        destroyPlayer();
                        removeShape(shape, false, false);
                    }
                } else if ((shape instanceof Asteroid || shape instanceof UFO) && player.intersects(shape)) {
                    destroyPlayer();
                    removeShape(shape, true, false);
                }
                if (player == null) break;
            }
        }
        // check bullet collisions
        for (Shape shapeI : shapes) if (shapeI instanceof Bullet) {
            Bullet bullet = (Bullet) shapeI;
            Line path = bullet.getPath();
            if (path.length() > MAX_BULLET_PATH_LENGTH) continue;
            for (Shape shapeJ : shapes) {
                if (shapeJ instanceof Asteroid) {
                    Asteroid asteroid = (Asteroid) shapeJ;
                    if (asteroid.intersects(bullet) || asteroid.intersects(path)) {
                        removeShape(bullet, false, false);
                        removeShape(asteroid, true, true);
                        break;
                    }
                }
                if (shapeJ instanceof UFO && !bullet.isUFOBullet()) {
                    UFO ufo = (UFO) shapeJ;
                    if (ufo.intersects(bullet) || ufo.intersects(path)) {
                        removeShape(bullet, false, false);
                        removeShape(ufo, true, true);
                        break;
                    }
                }
            }
        }
        // check UFO collisions with Asteroids
        for (Shape shapeI : shapes) if (shapeI instanceof UFO) {
            UFO ufo = (UFO) shapeI;
            for (Shape shapeJ : shapes) if (shapeJ instanceof Asteroid) {
                Asteroid asteroid = (Asteroid) shapeJ;
                if (ufo.intersects(asteroid)) {
                    removeShape(ufo, true, false);
                    removeShape(asteroid, true, false);
                }
            }
        }
    }

    private void checkLives() {
        for (Shape shape : shapes) {
            if (shape instanceof Bullet && ((Bullet) shape).dead()) {
                removeShape(shape, false, false);
            }
            if (shape instanceof DebrisParticle && ((DebrisParticle) shape).dead()) {
                removeShape(shape, false, false);
            }
            if (shape instanceof UFO && ((UFO) shape).dead()) {
                removeShape(shape, false, false);
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
                    clearAll();
                    menu.endGame(hud.getScore());
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
            // decrease the time between beats (increase the speed of the music) every INC_TIME ticks
            musicTimer = TIME_BETWEEN_BEATS - ticks / INC_TIME;
            // make sure the time between beats can't get too low
            musicTimer = Math.max(musicTimer, MIN_TIME_BETWEEN_BEATS);
        }
    }

    public void addShape(Shape shape) {
        added.add(shape);
    }

    public void removeShape(Shape shape, boolean particles, boolean score) {
        removed.add(shape);
        shape.destruct(true);
        if (particles) {
            for (int i = 0; i < NUM_DEBRIS_PARTICLES; ++i) {
                int life = rand.nextInt(MAX_DEBRIS_PARTICLE_LIFE);
                addShape(new DebrisParticle(shape, life));
            }
        }
        if (shape instanceof Asteroid) {
            Asteroid asteroid = (Asteroid) shape;
            if (asteroid.getType() == Asteroid.AsteroidType.Large) {
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Medium));
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Medium));
                hud.addToScore(score ? LARGE_SCORE : 0);
            } else if (asteroid.getType() == Asteroid.AsteroidType.Medium) {
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Small));
                addShape(new Asteroid(asteroid.getX(), asteroid.getY(), Asteroid.AsteroidType.Small));
                hud.addToScore(score ? MEDIUM_SCORE : 0);
            } else {
                hud.addToScore(score ? SMALL_SCORE : 0);
            }
        } else if (shape instanceof UFO) {
            UFO ufo = (UFO) shape;
            if (ufo.getType() == UFO.UFO_Type.LARGE) {
                hud.addToScore(score ? LARGE_UFO_SCORE : 0);
            } else {
                hud.addToScore(score ? SMALL_UFO_SCORE : 0);
            }
            ufoInGame = false;
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
        for (int i = 0; i < NUM_DEBRIS_PARTICLES; ++i) {
            int life = rand.nextInt(MAX_DEBRIS_PARTICLE_LIFE);
            addShape(new DebrisParticle(player, life));
        }
        playerExplosion = new PlayerExplosion(player.getX(), player.getY());
        player.destruct(false);
        player = null;
        playerDeathTimer = PLAYER_RESPAWN_TIME;
        hud.loseALife();
    }

    public Player getPlayer() {
        return player;
    }

    private void clearAll() {
        for (Shape shape : shapes) {
            shape.destruct(false);
        }
        for (Shape shape : added) {
            shape.destruct(false);
        }
        for (Shape shape : removed) {
            shape.destruct(false);
        }
        shapes.clear();
        added.clear();
        removed.clear();
    }
}
