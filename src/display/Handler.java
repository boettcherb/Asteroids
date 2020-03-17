package display;

import shape.Shape;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
    private LinkedList<Shape> shapes;

    public Handler() {
        shapes = new LinkedList<>();
    }

    public void tick() {
        for (Shape shape : shapes) {
            shape.tick();
        }
    }

    public void render(Graphics g) {
        for (Shape shape : shapes) {
            shape.render(g);
        }
    }

    public void clearAll() {
        shapes.clear();
    }
}
