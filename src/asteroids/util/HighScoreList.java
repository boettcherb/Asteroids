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
                String name = names[i];
                if (Character.isDigit(name.charAt(name.length() - 1))) {
                    name = name.substring(0, name.length() - 1);
                }
                int curScore = prefs.getInt(names[i], 0);
                g.drawString(name + getSpaces(name, curScore) + curScore, HS_LIST_X, height);
            } else {
                break;
            }
        }
    }

    private String getSpaces(String name, int score) {
        return ".".repeat(HS_LIST_SPACES - name.length() - Integer.toString(score).length());
    }

    public boolean newPossibleHighScore(int score) {
        if (prefs.getInt(names[NUM_HIGH_SCORES - 1], 0) < score) {
            newScore = score;
            return true;
        }
        return false;
    }

    public void addNewScore(String newName) {
        String name = getValidName(newName);
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

    private String getValidName(String newName) {
        StringBuilder sb = new StringBuilder(newName);
        if (invalidName(newName)) {
            sb.append('1');
            while (invalidName(sb.toString())) {
                char num = sb.charAt(sb.length() - 1);
                sb.deleteCharAt(sb.length() - 1);
                sb.append((char) (num + 1));
            }
        }
        return sb.toString();
    }

    private boolean invalidName(String newName) {
        for (String name : names) {
            if (name.equals(newName)) {
                return true;
            }
        }
        return false;
    }

    public int getNewScore() {
        return newScore;
    }
}
