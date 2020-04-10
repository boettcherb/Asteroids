package asteroids.display;

import asteroids.Info;
import asteroids.util.Button;
import asteroids.util.HighScoreList;
import asteroids.input.InputReader;
import asteroids.input.Name;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

/**
 * The Menu class is used to display the menus. There are 4 different menu states: the
 * start menu, the help menu, the high score list, and the add_high_score menu. The
 * menus are displayed whenever the player is not in a game. Each menu state has buttons
 * that allow the player to move between menu states and to start a new game.
 */
public class Menu implements Info {
    private final GUI gui;
    private MenuState menuState;
    private final Particle[] particles;
    private final Button play, help, quit, back, add, hsList;
    private final HighScoreList highScoreList;
    private Name name;

    public enum MenuState {
        Start,
        Help,
        High_Score_List,
        Add_High_Score,
        None // game in play, not in menus
    }

    public Menu(GUI gui) {
        this.gui = gui;
        menuState = MenuState.Start;
        particles = new Particle[NUM_MENU_PARTICLES];
        for (int i = 0; i < particles.length; ++i) {
            particles[i] = new Particle();
        }
        play = new Button(PLAY_BUTTON, "PLAY", BUTTON_FONT);
        help = new Button(HELP_BUTTON, "HELP", BUTTON_FONT);
        quit = new Button(QUIT_BUTTON, "QUIT", BUTTON_FONT);
        back = new Button(BACK_BUTTON, "BACK", BUTTON_FONT);
        add = new Button(ADD_BUTTON, "ADD SCORE", BUTTON_FONT);
        hsList = new Button(HS_LIST_BUTTON, "View High Scores", HS_BUTTON_FONT);
        highScoreList = new HighScoreList();
    }

    /**
     * The click method is called by the GUI mouse listener whenever the player clicks on the
     * canvas. The mouse listener sends the x and y coordinates of the click relative to the
     * canvas as a point. Then, depending on the menu state and which buttons are active,
     * this method checks if the coordinates of the click are inside the button. Each button
     * is essentially just a rectangle with its own coordinates, and it is easy to see if the
     * coordinates of the click are contained inside the rectangle.
     * @param point contains the x and y coordinates of the click
     */
    public void click(Point point) {
        // if in the start menu, check the play, help, quit, and hsList buttons
        if (menuState == MenuState.Start) {
            if (play.getRectangle().contains(point)) {
                gui.setPlaying(true);
                menuState = MenuState.None;
            } else if (help.getRectangle().contains(point)) {
                menuState = MenuState.Help;
            } else if (quit.getRectangle().contains(point)) {
                gui.stop();
            } else if (hsList.getRectangle().contains(point)) {
                menuState = MenuState.High_Score_List;
            }
        }
        // if in the help or high score list menus, check the back back button
        else if (menuState == MenuState.Help || menuState == MenuState.High_Score_List) {
            if (back.getRectangle().contains(point)) {
                menuState = MenuState.Start;
            }
        }
        // if in the add_high_score menu, check the add and back buttons
        else if (menuState == MenuState.Add_High_Score) {
            if (add.getRectangle().contains(point)) {
                if (!name.getName().equals(INITIAL_NAME)) {
                    highScoreList.addNewScore(name.getName());
                    menuState = MenuState.High_Score_List;
                }
            } else if (back.getRectangle().contains(point)) {
                menuState = MenuState.Start;
            }
        }
    }

    /**
     * Called every frame by the GUI. In the menu, the only things that
     * need to be ticked are the menu particles.
     */
    public void tick() {
        for (Particle p : particles) {
            p.tick();
        }
    }

    /**
     * The main render method of the menus, called by the GUI. This method
     * renders the menu particles, then, depending on the menu state, renders
     * the foreground of the menus.
     * @param g The graphics object used to draw to the canvas
     */
    public void render(Graphics g) {
        // render the menu particles
        g.setColor(MENU_PARTICLE_COLOR);
        for (Particle p : particles) {
            p.draw(g);
        }
        // render foreground based on the menu state
        g.setColor(FOREGROUND_COlOR);
        if (menuState == MenuState.Start) {
            renderStart(g);
        } else if (menuState == MenuState.Help) {
            renderHelp(g);
        } else if (menuState == MenuState.High_Score_List) {
            renderHighScoreList(g);
        } else if (menuState == MenuState.Add_High_Score) {
            renderAddHighScore(g);
        }
    }

    /**
     * Called by the main render method if the menu state is set to the start menu.
     * This method draws the game title and the 4 buttons on the start menu: the play,
     * help, quit, and hsList buttons.
     * @param g The graphics object used to draw to the canvas
     */
    private void renderStart(Graphics g) {
        Button.drawCenteredString(g, GAME_TITLE, TITLE_RECT, TITLE_FONT);
        play.draw(g);
        help.draw(g);
        quit.draw(g);
        hsList.draw(g);
    }

    /**
     * Called by the main render method if the menu state is set to the help menu.
     * This method reads text from the file helpText.txt, which contains the text
     * to be displayed on the help screen. It also displays the back button.
     * @param g The graphics object used to draw to the canvas
     */
    private void renderHelp(Graphics g) {
        g.setFont(HELP_TEXT_FONT);
        InputReader inputReader = new InputReader(HELP_TEXT_FILE);
        int line = 0;
        while (inputReader.hasNext()) {
            g.drawString(inputReader.nextLine(), HELP_TEXT_X, HELP_TEXT_Y + line * LINE_SPACE);
            ++line;
        }
        back.draw(g);
    }

    /**
     * Called by the main render method if the menu state is set to the high score list
     * menu. This method renders the high score list and the back button.
     * @param g The graphics object used to draw to the canvas
     */
    private void renderHighScoreList(Graphics g) {
        highScoreList.render(g);
        back.draw(g);
    }

    /**
     * Called by the main render method if the menu state is set to the add high
     * score menu. This method renders the box that is used to type in the user's
     * name if they get a high score, the user's high score, and the add and back
     * buttons
     * @param g The graphics object used to draw to the canvas
     */
    private void renderAddHighScore(Graphics g) {
        Button.drawCenteredString(g, "Your Score: " + highScoreList.getNewScore(), HS_RECT, HS_FONT);
        g.drawRect(NAME_RECT.x, NAME_RECT.y, NAME_RECT.width, NAME_RECT.height);
        Button.drawCenteredString(g, name.getName(), NAME_RECT, NAME_FONT);
        add.draw(g);
        back.draw(g);
    }

    /**
     * This method is called when the user loses all of their lives. It sets the
     * 'playing' boolean in the GUI to false, then it checks to see if the user
     * has a new high score. If so, the menu state is set to the add_high_score
     * menu. If not, the menu state is set to the start menu.
     * @param finalScore The player's final score for the game that just ended
     */
    public void endGame(int finalScore) {
        gui.setPlaying(false);
        if (highScoreList.newPossibleHighScore(finalScore)) {
            name = new Name();
            menuState = MenuState.Add_High_Score;
        } else {
            menuState = MenuState.Start;
        }
    }

    /**
     * Called by the GUI's KeyInput. When the user is in the add_high_score menu,
     * the GUI's key listener needs to read text input for the player's name.
     * @return true if the menu is in the add_high_score state.
     */
    public boolean readingName() {
        return menuState == MenuState.Add_High_Score;
    }

    // getters;

    public Name getName() {
        return name;
    }

    /**
     * A Particle is one of the moving dots in the menus that create the effect of moving
     * through space. Whenever the player is in the menus, the particles are moving in the
     * background.
     */
    private static class Particle {
        private double x, y, depth;
        private final Random rand = new Random();

        private Particle() {
            reset();
        }

        /**
         * The reset method gives the particles a new location on the screen and resets
         * their depth to the default value. This method is called to initialize the
         * particles and whenever they move off of the screen.
         */
        private void reset() {
            x = rand.nextInt(CANVAS_WIDTH) - CANVAS_WIDTH / 2.0;
            y = rand.nextInt(CANVAS_HEIGHT) - CANVAS_HEIGHT / 2.0;
            depth = CANVAS_WIDTH / 2.0;
        }

        /**
         * This method is called every frame by the tick menu in the Menu class. Every frame,
         * the particles move closer to the screen (their depth decreases).
         */
        private void tick() {
            depth -= MENU_PARTICLE_SPEED;
            if (depth < 1) {
                reset();
            }
        }

        /**
         * Draw the particle based on its depth and initial position.
         * @param g the graphics object used to draw to the canvas
         */
        private void draw(Graphics g) {
            // calculate the position of the particle based on its depth and initial position
            double sx = map(x / depth, -1, 1, 0, CANVAS_WIDTH);
            double sy = map(y / depth, -1, 1, 0, CANVAS_HEIGHT);
            // if the particle is off the screen, reset it
            if (sx < 0 || sx > CANVAS_WIDTH || sy < 0 || sy > CANVAS_HEIGHT) {
                reset();
            } else {
                // reverse mapping: the closer the particle gets, the bigger it gets
                double size = map(depth, 0, CANVAS_WIDTH / 2.0, MAX_MENU_PARTICLE_SIZE, 0);
                g.fillOval((int) sx, (int) sy, (int) size, (int) size);
            }
        }

        /**
         * This method takes in a value and 2 ranges (a min and max value). The value is mapped
         * from the first range to the second. For example, consider the case where the first range
         * is [10, 20], the second range is [0, 100], and the value is 13. The value 13 is mapped
         * from the first range to the second and becomes 30.
         * @param val the value to be mapped
         * @param in_min the minimum of the input range
         * @param in_max the maximum of the input range
         * @param out_min the minimum of the output range
         * @param out_max the maximum of the output range
         * @return the new mapped value
         */
        private static double map(double val, double in_min, double in_max, double out_min, double out_max) {
            return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        }
    }
}