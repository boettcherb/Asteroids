package asteroids.util;

import asteroids.Info;

import java.awt.Graphics;
import java.util.prefs.Preferences;

/**
 * The high score list is a part of the Menus that displays the name and high score of the best
 * players. This information is kept by the JVM across program runs using java preferences.
 */
public class HighScoreList implements Info {
    private final Preferences prefs;
    private String[] names;
    private int newScore;

    public HighScoreList() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
        getNames();
    }

    /**
     * Create an array of strings and put all the high score names
     * in the array for easy access.
     */
    private void getNames() {
        names = new String[NUM_HIGH_SCORES];
        for (int i = 0; i < NUM_HIGH_SCORES; ++i) {
            names[i] = prefs.get(HIGH_SCORE_KEYS[i], "");
        }
    }

    /**
     * Render the high score list to the canvas. This method is called by the render method
     * of the Menu class.
     * @param g The graphics object of the canvas.
     */
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

    /**
     * The High Score List screen has a list of names and scores. The names are on the left and the scores
     * are on the right. This method determines the number of spaces in between the name and the score and
     * creates a string of periods of that length. The length of this string will depend on the name and the
     * score, and will ensure that each line of the list is exactly the same length (name length + spaces +
     * digits in score = HS_LIST_SPACES).
     * @param name The name to be added to the high score list
     * @param score The high score that the player achieved (placed to the right of the name on the list)
     * @return A string of periods ('.') to separate the name and the score.
     */
    private String getSpaces(String name, int score) {
        return ".".repeat(HS_LIST_SPACES - name.length() - Integer.toString(score).length());
    }

    /**
     * This method is called when a game ends. The player's score is passed in and this method checks
     * if that score is a high score. The new score is a high score if it is greater than the lowest
     * current high score in the list.
     * @param score The player's score
     * @return True if the player's score is a high score, false otherwise.
     */
    public boolean newPossibleHighScore(int score) {
        if (prefs.getInt(names[NUM_HIGH_SCORES - 1], 0) < score) {
            newScore = score;
            return true;
        }
        return false;
    }

    /**
     * This method adds the name of the player that got a new high score to the list. The name
     * is first formatted by the getValidName() method to prevent duplicates. Then it is inserted
     * into the list from the back, shifting the lower high scores further down the list (just like
     * insertion sort)
     * @param newName The name of the player who will be added to the list
     */
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

    /**
     * A number is added to the end of the name to prevent duplicates. If the name
     * "Bob" is the new name but it is already in the list, this method will change
     * this name to "Bob0". This is necessary because recording a score to a duplicate
     * name will override the original score. This number is not visible on the list.
     * @param newName The name of the player who will be added to the list
     * @return The new formatted name with a number at the end
     */
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

    /**
     * This method checks if the new name is already in the list. This method is used by the
     * getValidName() method to see if there is a naming conflict.
     * @param newName The name of the player who will be added to the list
     * @return True if the new name is already in the list, false otherwise
     */
    private boolean invalidName(String newName) {
        for (String name : names) {
            if (name.equals(newName)) {
                return true;
            }
        }
        return false;
    }

    // Getters and Setters

    public int getNewScore() {
        return newScore;
    }
}
