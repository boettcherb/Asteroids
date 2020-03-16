package display;

import game_info.Info;
import util.Button;

import java.awt.Graphics;
import java.util.Random;

public class Menu implements Info {
    private final Particle[] particles;
    private Button play, help, quit;

    public Menu() {
        particles = new Particle[NUM_PARTICLES];
        for (int i = 0; i < particles.length; ++i) {
            particles[i] = new Particle();
        }
        play = new Button(PLAY_BUTTON_TEXT, PLAY_BUTTON_HEIGHT);
        help = new Button(HELP_BUTTON_TEXT, HELP_BUTTON_HEIGHT);
        quit = new Button(QUIT_BUTTON_TEXT, QUIT_BUTTON_HEIGHT);
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
        Button.drawCenteredString(g, GAME_TITLE, TITLE_RECT, TITLE_FONT);
        play.draw(g);
        help.draw(g);
        quit.draw(g);
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