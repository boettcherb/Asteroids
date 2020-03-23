package shape;

import game_info.Info;
import util.Line;
import util.Point;

import java.awt.*;
import java.util.Random;

public class DestroyedPlayer implements Info {
    private Line[] lines;
    private float[][] speeds;

    public DestroyedPlayer(float X, float Y) {
        Random rand = new Random();
        lines = new Line[NUM_LINES];
        int lineIndex = 0;
        while (lineIndex < NUM_LINES) {
            float x1 = X + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            float y1 = Y + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            float x2 = X + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            float y2 = Y + rand.nextFloat() * MAX_LINE_LENGTH * (rand.nextBoolean() ? 1 : -1);
            Line line = new Line(new Point(x1, y1), new Point(x2, y2));
            if (line.length() > 5) {
                lines[lineIndex++] = line;
            }
        }
        speeds = new float[NUM_LINES][2];
        for (int i = 0; i < NUM_LINES; ++i) {
            speeds[i][0] = rand.nextFloat() * MAX_LINE_SPEED * (rand.nextBoolean() ? 1 : -1);
            speeds[i][1] = rand.nextFloat() * MAX_LINE_SPEED * (rand.nextBoolean() ? 1 : -1);
        }
    }

    public void tick() {
        for (int i = 0; i < NUM_LINES; ++i) {
            Point p1 = lines[i].getP1();
            Point p2 = lines[i].getP2();
            p1.translate(speeds[i][0], speeds[i][1]);
            p2.translate(speeds[i][0], speeds[i][1]);
            lines[i] = new Line(p1, p2);
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < NUM_LINES; ++i) {
            Point p1 = lines[i].getP1();
            Point p2 = lines[i].getP2();
            g.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
        }
    }
}
