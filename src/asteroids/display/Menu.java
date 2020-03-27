package asteroids.display;

import asteroids.Info;
import asteroids.util.Button;
import asteroids.util.InputReader;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Menu implements Info {
    private GUI gui;
    private MenuState menuState;
    private final Particle[] particles;
    private final Button play, help, quit, back;

    private enum MenuState {
        Start,
        Help,
        None
    }

    public Menu(GUI gui) {
        this.gui = gui;
        menuState = MenuState.Start;
        particles = new Particle[NUM_PARTICLES];
        for (int i = 0; i < particles.length; ++i) {
            particles[i] = new Particle();
        }
        play = new Button(PLAY_BUTTON_TEXT, PLAY_BUTTON_HEIGHT);
        help = new Button(HELP_BUTTON_TEXT, HELP_BUTTON_HEIGHT);
        quit = new Button(QUIT_BUTTON_TEXT, QUIT_BUTTON_HEIGHT);
        back = new Button(BACK_BUTTON_TEXT, BACK_BUTTON_HEIGHT);
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
            }
        } else if (menuState == MenuState.Help) {
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
        g.setColor(MENU_PARTICLE_COLOR);
        for (Particle p : particles) {
            p.draw(g);
        }
        g.setColor(FOREGROUND_COlOR);
        if (menuState == MenuState.Start) {
            renderStart(g);
        } else {
            renderHelp(g);
        }
    }

    private void renderStart(Graphics g) {
        Button.drawCenteredString(g, GAME_TITLE, TITLE_RECT, TITLE_FONT);
        play.draw(g);
        help.draw(g);
        quit.draw(g);
    }

    private void renderHelp(Graphics g) {
        g.setFont(HELP_TEXT_FONT);
        InputReader inputReader = new InputReader(HELP_TEXT_FILE);
        int line = 0;
        while (inputReader.hasNextLine()) {
            g.drawString(inputReader.nextLine(), HELP_TEXT_X, HELP_TEXT_Y + line * LINE_SPACE);
            ++line;
        }
        back.draw(g);
    }

    public void endGame() {
        gui.setPlaying(false);
    }

    public void setStartMenu() {
        menuState = MenuState.Start;
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
            depth -= PARTICLE_SPEED;
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
                double size = map(depth, 0, CANVAS_WIDTH / 2.0, MAX_PARTICLE_SIZE, 0);
                g.fillOval((int) sx, (int) sy, (int) size, (int) size);
            }
        }

        private static double map(double val, double in_min, double in_max, double out_min, double out_max) {
            return (val - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
        }
    }
}