package asteroids.display;

import asteroids.Info;
import asteroids.objects.*;
import asteroids.util.Line;
import asteroids.util.Point;
import asteroids.util.Sound;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

/**
 * The Handler class does most of the heavy lifting for the game. It is responsible for spawning
 * and keeping track of all the objects and shapes in the game (the player, the asteroids, the
 * bullets, etc), collision detection, and for ending the game when the player runs out of lives.
 */
public class Handler implements Info {
    private LinkedList<Shape> shapes, added, removed;
    private Menu menu;
    private HUD hud;
    private Player player;
    private PlayerExplosion playerExplosion;
    private Random rand;
    private int playerDeathTimer, playerSaveTimer, newLevelTimer, musicTimer;
    private Sound beat1, beat2;
    private int ticks, asteroidsRemaining;
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

    /**
     * This method is called by the GUI whenever a new game begins. This method initializes the
     * game for the handler and hud by spawning a new player and starting the first level.
     */
    public void newGame() {
        clearAll();
        resetPlayer();
        hud.newGame();
        newLevel();
    }

    /**
     * A "level" in this game is a new set of large asteroids. When all the asteroids are destroyed,
     * new large asteroids spawn, each time one more than the previous level. This method is called
     * at the start of the game, and after all the asteroids of the current level are destroyed.
     */
    private void newLevel() {
        if (player == null) {
            resetPlayer();
        }
        // increase the level count
        hud.nextLevel();
        // spawn new large asteroids
        for (int i = 0; i < hud.getLevel() + STARTING_ASTEROIDS; ++i) {
            int x, y;
            do {
                x = rand.nextInt(CANVAS_WIDTH);
                y = rand.nextInt(CANVAS_HEIGHT);
            } while (new Point(x, y).distTo(new Point(player.getX(), player.getY())) < MIN_SPAWN_DIST_FROM_PLAYER);
            addShape(new Asteroid(x, y, Asteroid.AsteroidType.Large));
        }
        ticks = 0;
        // there must be 7 explosions to completely destroy a large asteroid
        asteroidsRemaining = (STARTING_ASTEROIDS + hud.getLevel()) * 7;
        // reset the music
        firstBeat = true;
        musicTimer = 0;
    }

    /**
     * This method is called at the start of the game and whenever the player dies. It creates
     * a new player in the center of the screen and sets the player save timer.
     */
    private void resetPlayer() {
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        playerExplosion = null;
        playerSaveTimer = PLAYER_SAVE_TIME;
    }

    /**
     * This method is called when the player loses all their lives. It reset all objects in the
     * game and calls the endGame() method in the menu to set the program state to !playing.
     */
    private void endGame() {
        clearAll();
        menu.endGame(hud.getScore());
    }

    /**
     * Called by the GUI when it is time to update the current state of the game. This
     * method calls the tick method for every shape and it checks lives and collisions.
     * Also, every tick there is the possibility of spawning a UFO.
     */
    public void tick() {
        ++ticks;
        shapes.forEach(Shape::tick);
        float ufo_chance = ticks * UFO_SPAWN_CHANCE;
        // only spawn a UFO if there is not one already in the
        // game and if a new level is not about to start
        if (!ufoInGame && newLevelTimer <= 0 && rand.nextFloat() < ufo_chance) {
            addShape(new UFO(rand.nextBoolean() ? 0 : CANVAS_WIDTH, rand.nextInt(CANVAS_HEIGHT), this));
            ufoInGame = true;
        }
        manageTimers();
        if (player == null) {
            if (playerExplosion != null) playerExplosion.tick();
        } else {
            player.tick();
        }
        checkLives();
        checkCollisions();
        resetLists();
    }

    /**
     * This method removes bullets and debris particles after a certain amount of time (determined
     * by that instance), and UFOs if they move off the edge of the screen.
     */
    private void checkLives() {
        for (Shape shape : shapes) {
            if (shape instanceof Bullet && ((Bullet) shape).dead()) {
                removeShape(shape, false, false);
            } else if (shape instanceof DebrisParticle && ((DebrisParticle) shape).dead()) {
                removeShape(shape, false, false);
            } else if (shape instanceof UFO && ((UFO) shape).dead()) {
                removeShape(shape, false, false);
            }
        }
    }

    /**
     * This method does all the collision detection for this game. It checks player collisions with
     * asteroids, UFOs, and UFO bullets, it checks bullet collisions with asteroids and UFOs, and
     * it checks collisions between UFOs and asteroids. Every shape has an intersects(Shape) method
     * and an intersects(Line) method that are called to check collisions.
     */
    private void checkCollisions() {
        // check player collisions (make sure the player save timer is not on)
        if (player != null && playerSaveTimer <= 0) {
            for (Shape shape : shapes) {
                if (shape instanceof Bullet) {
                    Bullet bullet = (Bullet) shape;
                    if (bullet.isUFOBullet() && (player.intersects(bullet) || player.intersects(bullet.getPath()))) {
                        destroyPlayer();
                        removeShape(shape, false, false);
                        break;
                    }
                } else if ((shape instanceof Asteroid || shape instanceof UFO) && player.intersects(shape)) {
                    destroyPlayer();
                    removeShape(shape, true, false);
                    break;
                }
            }
        }
        // check bullet collisions
        for (Shape shapeI : shapes)
            if (shapeI instanceof Bullet) {
                Bullet bullet = (Bullet) shapeI;
                Line path = bullet.getPath();
                if (path.length() > MAX_BULLET_PATH_LENGTH) continue;
                for (Shape shapeJ : shapes) {
                    // check bullet collisions with asteroids
                    if (shapeJ instanceof Asteroid) {
                        Asteroid asteroid = (Asteroid) shapeJ;
                        if (asteroid.intersects(bullet) || asteroid.intersects(path)) {
                            removeShape(bullet, false, false);
                            removeShape(asteroid, true, true);
                            break;
                        }
                    }
                    // check bullet collisions with UFOs (only if they came from the player)
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

    /**
     * This method is called every tick. It removes the shapes that are in the removed list
     * and adds the shapes that are in the added list.
     */
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

    /**
     * Called by the GUI when it is time to render the current state of the game to the
     * screen. This method calls the render method of each shape in the game, the player,
     * and the player explosion.
     * @param g The graphics object from the canvas.
     */
    public void render(Graphics g) {
        shapes.forEach(shape -> shape.render(g));
        if (player == null) {
            playerExplosion.render(g);
        } else {
            // flashes the player when the playerSaveTimer is set
            // when the timer is set, only render the player for every
            // other sequence of PLAYER_FLASH_TIME ticks
            if (playerSaveTimer == 0 || (playerSaveTimer / PLAYER_FLASH_TIME) % 2 == 0) {
                player.render(g);
            }
        }
    }

    /**
     * The handler class has 4 timers. When a timer is "set", it is set to its initial value,
     * and every tick the timer is decremented until it "ends", or reaches 0.
     * The playerDeathTimer is set whenever the player dies, and when it ends the player
     * respawns. The playerSaveTimer is set when the player respawns, and while it is set, the
     * player cannot die. The new level timer is set when the last asteroid and ufo have been
     * destroyed, and when it ends a new level starts. The music timer is set when a beat is
     * played, and when it ends the next beat is played and it is set again.
     */
    private void manageTimers() {
        if (player == null) {
            if (--playerDeathTimer <= 0) {
                if (hud.getNumLives() == 0) {
                    endGame();
                } else {
                    resetPlayer();
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
        // play music when the music timer runs out, but not when a new level is about to start
        if (--musicTimer <= 0 && newLevelTimer <= 0) {
            if (firstBeat) {
                beat1.playSound(false);
            } else {
                beat2.playSound(false);
            }
            firstBeat = !firstBeat;
            // decrease the time between beats (increase the speed of the music) every INC_TIME ticks
            // make sure it can't get too low though
            musicTimer = Math.max(TIME_BETWEEN_BEATS - ticks / INC_TIME, MIN_TIME_BETWEEN_BEATS);
        }
    }

    /**
     * Adds a new shape to the game. New shapes go into the added list. (Not the shapes
     * list because we don't want to be modifying that if other threads are using it).
     *
     * @param shape The shape to be added
     */
    public void addShape(Shape shape) {
        added.add(shape);
    }

    /**
     * Removes a shape from the game. Adds the shape to the removed list (again, we don't
     * want to be modifying the shapes list while other threads are using it), and carries
     * out any other tasks that happen when a shape is removed, such as spawing debris
     * particles, adding to the score, and checking to see if there are any asteroids
     * remaining in the game.
     *
     * @param shape     The shape to be removed
     * @param particles true if the destruction of this shape should spawn debris particles
     * @param score     true if the player receives points for this shape's destruction
     */
    public void removeShape(Shape shape, boolean particles, boolean score) {
        removed.add(shape);
        shape.destruct(true);
        if (shape instanceof Asteroid) {
            --asteroidsRemaining;
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
        if (particles) {
            for (int i = 0; i < NUM_DEBRIS_PARTICLES; ++i) {
                int life = rand.nextInt(MAX_DEBRIS_PARTICLE_LIFE);
                addShape(new DebrisParticle(shape, life));
            }
        }
        // reset the new level timer if there are no asteroids or
        // UFOs remaining and if it has not already been reset
        if (asteroidsRemaining <= 0 && !ufoInGame && newLevelTimer <= 0) {
            newLevelTimer = TIME_BETWEEN_LEVELS;
        }
    }

    /**
     * This method does all the things that happen when the play dies. It sets the player
     * to null, decreases the lives count, spawns some debris particles, and creates a
     * new player explosion object (the lines that appear when the player dies).
     */
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

    /**
     * Used by the KeyInput class (so that when a key, such as the spacebar, is pressed, the
     * state of the player will be changed), and also by the UFO class (so that the UFO can
     * find the position of the player to shoot towards them).
     *
     * @return The current instance of the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Resets the variables and objects of this class to their default value
     * in preparation for a new game
     */
    private void clearAll() {
        player = null;
        playerExplosion = null;
        ufoInGame = false;
        playerDeathTimer = playerSaveTimer = newLevelTimer = musicTimer = 0;
        ticks = asteroidsRemaining = 0;
        // must call destruct on every shape before removing them
        shapes.forEach(shape -> shape.destruct(false));
        added.forEach(shape -> shape.destruct(false));
        removed.forEach(shape -> shape.destruct(false));
        shapes.clear();
        added.clear();
        removed.clear();
    }
}
