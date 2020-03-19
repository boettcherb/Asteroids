package display;

import game_info.Info;
import shape.*;

import java.awt.Graphics;
import java.util.ListIterator;
import java.util.ArrayList;

public class Handler implements Info {
    private ArrayList<Shape> shapes;
    private Player player;

    public Handler() {
        shapes = new ArrayList<>();
        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
    }

    public void tick() {
        player.tick();
        for (int i = 0; i < shapes.size(); ++i) {
            shapes.get(i).tick();
        }
        checkBulletLives();
    }

    private void checkBulletLives() {
        ListIterator<Shape> itr = shapes.listIterator();
        while (itr.hasNext()) {
            Shape shape = itr.next();
            if (shape instanceof Bullet && ((Bullet) shape).dead()) {
                itr.remove();
            }
        }
    }

    public void render(Graphics g) {
        player.render(g);
        for (int i = 0; i < shapes.size(); ++i) {
            shapes.get(i).render(g);
        }
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }

    public Player getPlayer() {
        return player;
    }

    public void clearAll() {
        shapes.clear();
    }
}
