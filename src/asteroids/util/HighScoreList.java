package asteroids.util;

import asteroids.Info;

import java.awt.Graphics;
import java.util.prefs.Preferences;

public class HighScoreList implements Info {
    private Preferences prefs;
    private String[] names;
    private int newScore;

    public HighScoreList() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        getNames();
        newScore = 0;
    }

    private void getNames() {
        names = new String[NUM_HIGH_SCORES];
        for (int i = 0; i < NUM_HIGH_SCORES; ++i) {
            names[i] = prefs.get(HIGH_SCORE_KEYS[i], "");
        }
    }

    public void render(Graphics g) {
        g.setFont(HS_LIST_FONT);
        int height = HS_LIST_Y;
        for (int i = 0; i < NUM_HIGH_SCORES; ++i) {
            height += HS_LIST_LINE_SPACING;
            int score = prefs.getInt(names[i], 0);
            if (score > 0) {
                g.drawString(names[i] + getSpaces(names[i].length()) + prefs.getInt(names[i], 0), HS_LIST_X, height);
            } else {
                break;
            }
        }
    }

    private String getSpaces(int nameLength) {
        if (nameLength >= 20) {
            throw new IllegalArgumentException("HighScoreList: getSpaces(): name too long");
        }
        return " ".repeat(20 - nameLength);
    }

    public boolean newPossibleHighScore(int score) {
        if (prefs.getInt(names[NUM_HIGH_SCORES - 1], 0) < score) {
            newScore = score;
            return true;
        }
        return false;
    }

    public void addNewScore(String name) {
        int newIndex = 0;
        while (newIndex < NUM_HIGH_SCORES && prefs.getInt(names[newIndex], 0) >= newScore) {
            ++newIndex;
        }
        if (newIndex < NUM_HIGH_SCORES) {
            for (int i = NUM_HIGH_SCORES - 1; i > newIndex; --i) {
                prefs.put(HIGH_SCORE_KEYS[i], prefs.get(HIGH_SCORE_KEYS[i - 1], ""));
            }
            prefs.put(HIGH_SCORE_KEYS[newIndex], name);
            prefs.putInt(name, newScore);
        }
        getNames();
        newScore = 0;
    }
}
