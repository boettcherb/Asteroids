package display;

import game_info.Info;
import shape.Player;

import java.awt.Graphics;

public class HUD implements Info {
    private int numLives;
    private int score;
    private int level;
    private Player playerImage;

    public HUD() {
        playerImage = new Player(LIVES_X, LIVES_Y);
        playerImage.scale(LIVES_SCALE);
    }

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

    public void newGame() {
        score = 0;
        numLives = STARTING_LIVES;
        level = 0;
    }

    public int getLevel() {
        return level;
    }

    public void nextLevel() {
        ++level;
    }

    public void addToScore(int val) {
        score += val;
    }

    public void loseALife() {
        --numLives;
    }

    public int getNumLives() {
        return numLives;
    }
}
