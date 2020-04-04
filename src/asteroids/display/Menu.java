package asteroids.display;

import asteroids.Info;
import asteroids.util.Button;
import asteroids.util.HighScoreList;
import asteroids.util.InputReader;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Menu implements Info {
    private GUI gui;
    private MenuState menuState;
    private final Particle[] particles;
    private final Button play, help, quit, back, add, hsList;
    private HighScoreList highScoreList;

    public enum MenuState {
        Start,
        Help,
        High_Score_List,
        Add_High_Score,
        None // game in play
    }

    public Menu(GUI gui) {
        this.gui = gui;
        menuState = MenuState.Start;
        particles = new Particle[NUM_MENU_PARTICLES];
        for (int i = 0; i < particles.length; ++i) {
            particles[i] = new Particle();
        }
        play = new Button(PLAY_BUTTON, PLAY_BUTTON_TEXT, BUTTON_FONT);
        help = new Button(HELP_BUTTON, HELP_BUTTON_TEXT, BUTTON_FONT);
        quit = new Button(QUIT_BUTTON, QUIT_BUTTON_TEXT, BUTTON_FONT);
        back = new Button(BACK_BUTTON, BACK_BUTTON_TEXT, BUTTON_FONT);
        add = new Button(ADD_BUTTON, ADD_BUTTON_TEXT, BUTTON_FONT);
        hsList = new Button(HS_LIST_BUTTON, HS_LIST_BUTTON_TEXT, HS_BUTTON_FONT);
        highScoreList = new HighScoreList();
    }

    public void click(Point point) {
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
        } else if (menuState == MenuState.Help || menuState == MenuState.High_Score_List
            || menuState == MenuState.Add_High_Score) {
            if (back.getRectangle().contains(point)) {
                menuState = MenuState.Start;
            }
        }
    }

    public void tick() {
        for (Particle p : particles) {
            p.tick();
        }
    }

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

        }
    }

    private void renderStart(Graphics g) {
        Button.drawCenteredString(g, GAME_TITLE, TITLE_RECT, TITLE_FONT);
        play.draw(g);
        help.draw(g);
        quit.draw(g);
        hsList.draw(g);
    }

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

    private void renderHighScoreList(Graphics g) {
        highScoreList.render(g);
        back.draw(g);
    }

    public void endGame(int finalScore) {
        gui.setPlaying(false);
//        if (highScoreList.newPossibleHighScore(finalScore)) {
//            menuState = MenuState.Add_High_Score;
//        }
    }

    public void setMenuState(MenuState state) {
        menuState = state;
    }

    private static class Particle {
        private double x, y, depth;
        private final Random rand = new Random();

        private Particle() {
            reset();
        }

        private void reset() {
            x = rand.nextInt(CANVAS_WIDTH) - CANVAS_WIDTH / 2.0;
            y = rand.nextInt(CANVAS_HEIGHT) - CANVAS_HEIGHT / 2.0;
            depth = CANVAS_WIDTH / 2.0;
        }

        private void tick() {
            depth -= MENU_PARTICLE_SPEED;
            if (depth < 1) {
                reset();
            }
        }

        private void draw(Graphics g) {
            double sx = map(x / depth, -1, 1, 0, CANVAS_WIDTH);
            double sy = map(y / depth, -1, 1, 0, CANVAS_HEIGHT);
            if (sx < 0 || sx > CANVAS_WIDTH || sy < 0 || sy > CANVAS_HEIGHT) {
                reset();
            } else {
                double size = map(depth, 0, CANVAS_WIDTH / 2.0, MAX_MENU_PARTICLE_SIZE, 0);
                g.fillOval((int) sx, (int) sy, (int) size, (int) size);
            }
        }

        private static double map(double val, double in_min, double in_max, double out_min, double out_max) {
            return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        }
    }
}