package util;

import game_info.Info;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Button implements Info {
    private final int X, Y;

    public Button(double percentHeight) {
        Y = (int) (percentHeight * CANVAS_HEIGHT);
        X = (CANVAS_WIDTH / 2) - (BUTTON_WIDTH / 2);
    }

    public void draw(Graphics g) {
        g.drawRect(X, Y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public Rectangle getRectangle() {
        return new Rectangle(X, Y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }
}