package display;

import game_info.Info;
import shape.Shape;
import shape.Player;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler implements Info {
    private LinkedList<Shape> shapes;

    public Handler() {
        shapes = new LinkedList<>();
        shapes.add(new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2));
    }

    public synchronized void tick() {
        for (Shape shape : shapes) {
            shape.tick();
        }
    }

    public synchronized void render(Graphics g) {
        for (Shape shape : shapes) {
            shape.render(g);
        }
    }

    public synchronized void addShape(Shape shape) {
        shapes.add(shape);
    }

    public synchronized Player getPlayer() {
        for (Shape shape : shapes) {
            if (shape instanceof Player) {
                return (Player) shape;
            }
        }
        return null;
    }

    public void clearAll() {
        shapes.clear();
    }
}
