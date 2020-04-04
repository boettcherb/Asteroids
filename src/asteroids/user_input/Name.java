package asteroids.user_input;

import asteroids.Info;

public class Name implements Info {
    private StringBuilder name;

    public Name() {
        name = new StringBuilder(INITIAL_NAME);
    }

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

    public void backspace() {
        if (name.length() == 1) {
            clear();
            name.append(INITIAL_NAME);
        } else if (name.length() > 1 && !name.toString().equals(INITIAL_NAME)) {
            name.deleteCharAt(name.length() - 1);
        }
    }

    private void clear() {
        name.delete(0, name.length());
    }

    public String getName() {
        return name.toString();
    }
}
