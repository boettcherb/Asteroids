package display;

import game_info.Info;
import shape.Player;

import java.awt.Graphics;

public class HUD implements Info {
    private int numLives;
    private int score;
    private Player playerImage;

    public HUD() {
        numLives = STARTING_LIVES;
        score = 0;
        playerImage = new Player(LIVES_X, LIVES_Y);
        playerImage.scale(LIVES_SCALE);
    }

    public void render(Graphics g) {
        for (int i = 0; i < numLives; ++i) {
            playerImage.render(g);
            playerImage.translate(LIVES_DIST, 0);
        }
        playerImage.translate(-LIVES_DIST * numLives, 0);
        String str = "Score: " + Integer.toString(score);
        g.setFont(SCORE_FONT);
        g.drawString(str, SCORE_X, SCORE_Y);
    }

    public void addToScore(int val) {
        score += val;
    }

    public boolean loseALife() {
        return --numLives == 0;
    }
}