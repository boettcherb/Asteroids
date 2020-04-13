package asteroids.display;

import asteroids.Info;
import asteroids.objects.Player;
import java.awt.Graphics;

/**
 * The HUD class is responsible for displaying information to the player, such as the score
 * and the number of lives remaining.
 */
public class HUD implements Info {
    private int numLives;
    private int score;
    private int level;
    private final Player playerImage;

    public HUD() {
        playerImage = new Player(LIVES_X, LIVES_Y);
        playerImage.scale(LIVES_SCALE);
    }

    /**
     * Render the HUD elements to the screen. The playerImage is a player object that has
     * been scaled down and is displayed once for every life the player has.
     * @param g The Canvas's graphics object that we are writing to
     */
    public void render(Graphics g) {
        for (int i = 0; i < numLives; ++i) {
            playerImage.render(g);
            playerImage.translate(LIVES_DIST, 0);
        }
        playerImage.translate(-LIVES_DIST * numLives, 0);
        String str = "Score: " + score;
        g.setFont(SCORE_FONT);
        g.drawString(str, SCORE_X, SCORE_Y);
    }

    /**
     * This method is called when the Handler initiates a new game. It resets the score,
     * level, and number of lives for the new game.
     */
    public void newGame() {
        score = 0;
        numLives = STARTING_LIVES;
        level = 0;
    }

    /**
     * Go to the next level. Called by the Handler when all the asteroids of the current
     * level have been destroyed.
     */
    public void nextLevel() {
        ++level;
    }

    /**
     * Add points to the player's score. This method is called by the handler when the player
     * shoots another shape (an asteroid or a UFO). This method is also responsible for giving
     * the player another life if the player's score hits a multiple of 10,000
     * @param val the points to add to the player's score
     */
    public void addToScore(int val) {
        // if the score surpasses a multiple of EXTRA_LIFE_SCORE, add an extra life
        if (score % EXTRA_LIFE_SCORE > (score + val) % EXTRA_LIFE_SCORE) {
            ++numLives;
        }
        score += val;
    }

    /**
     * Decrease the number of lives by 1. Called when the player collides with another object
     * (an asteroid, a UFO, a UFO bullet)
     */
    public void loseALife() {
        --numLives;
    }

    // Getter methods:

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getNumLives() {
        return numLives;
    }
}
