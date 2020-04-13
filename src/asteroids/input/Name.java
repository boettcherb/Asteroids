package asteroids.input;

import asteroids.Info;

/**
 * The Name class is used to create a string (a name) based on the player's input
 * in the add_high_score menu state. When the player achieves a high score, they
 * can type their name into a text box so that it can be stored and displayed on
 * the high score list. This builds the name based on method calls from the key
 * listener. When the user's name is of length 0 (they have not typed anything yet
 * or they deleted what they had), the name is set to INITIAL_NAME, which is a
 * prompt for the user to start typing their name.
 */
public class Name implements Info {
    private final StringBuilder name;

    public Name() {
        name = new StringBuilder(INITIAL_NAME);
    }

    /**
     * When the user types a letter into the text box, this method is called.
     * This method checks to make sure that the letter is a valid letter, and
     * that the length of their name does not exceed MAX_NAME_CHARACTERS letters.
     * @param c the character that the user typed into their name
     */
    public void addLetter(char c) {
        boolean valid = Character.isAlphabetic(c);
        for (char validSymbol : VALID_SYMBOLS) {
            if (validSymbol == c) {
                valid = true;
                break;
            }
        }
        if (valid) {
            if (name.toString().equals(INITIAL_NAME)) {
                clear();
            }
            if (name.length() < MAX_NAME_CHARACTERS) {
                name.append(c);
            }
        }
    }

    /**
     * If the user pressed the backspace, remove the last character of the current name.
     * If the name is set to length 0, set it instead to INITIAL_NAME, which is a prompt
     * for the user to start typing.
     */
    public void backspace() {
        if (name.length() == 1) {
            clear();
            name.append(INITIAL_NAME);
        } else if (name.length() > 1 && !name.toString().equals(INITIAL_NAME)) {
            name.deleteCharAt(name.length() - 1);
        }
    }

    /**
     * Reset the name to an empty string.
     */
    private void clear() {
        name.delete(0, name.length());
    }

    /**
     * Get the current name that is contained in the StringBuilder.
     * @return the current name.
     */
    public String getName() {
        return name.toString();
    }
}
