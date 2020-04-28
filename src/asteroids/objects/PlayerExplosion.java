package asteroids.objects;

import asteroids.Info;
import asteroids.util.Line;
import asteroids.util.Point;
import java.awt.*;
import java.util.Random;

/**
 * A PlayerExplosion is the four lines that appear when the player spaceship is destroyed. Each
 * line consists of 2 random points around where the player died, and a random velocity. The
 * PlayerExplosion is removed when the player spawns again.
 */
public class PlayerExplosion implements Info {
    private final Line[] lines;
    private final float[][] speeds;

    public PlayerExplosion(float X, float Y) {
        Random rand = new Random();
        lines = new Line[NUM_LINES];
        int lineIndex = 0;
        while (lineIndex < NUM_LINES) {
            float x1 = X + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            float y1 = Y + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            float x2 = X + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            float y2 = Y + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            Line line = new Line(new Point(x1, y1), new Point(x2, y2));
            if (line.length() > MIN_LINE_LENGTH) {
                lines[lineIndex++] = line;
            }
        }
        speeds = new float[NUM_LINES][2];
        for (int i = 0; i < NUM_LINES; ++i) {
            speeds[i][0] = rand.nextFloat() * MAX_LINE_SPEED * (rand.nextBoolean() ? 1 : -1);
            speeds[i][1] = rand.nextFloat() * MAX_LINE_SPEED * (rand.nextBoolean() ? 1 : -1);
        }
    }

    /**
     * The tick method is called every frame by the Handler. Every frame, the new
     * positions of the points are calculated from the old positions and the velocity,
     * and a new line is created with those new points.
     */
    public void tick() {
        for (int i = 0; i < NUM_LINES; ++i) {
            Point p1 = lines[i].getP1();
            Point p2 = lines[i].getP2();
            p1.translate(speeds[i][0], speeds[i][1]);
            p2.translate(speeds[i][0], speeds[i][1]);
            lines[i] = new Line(p1, p2);
        }
    }

    /**
     * The render method is called by the render method in the handler when it
     * is time to draw to the canvas. Each time the render method is called, the
     * lines that make up the explosion are drawn with drawLine().
     * @param g The graphics object from the canvas.
     */
    public void render(Graphics g) {
        for (int i = 0; i < NUM_LINES; ++i) {
            Point p1 = lines[i].getP1();
            Point p2 = lines[i].getP2();
            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }
}
