package util;

import game_info.Info;

import java.awt.*;

public class Button implements Info {
    private final String buttonText;
    private final int X, Y;

    public Button(String text, float percentHeight) {
        buttonText = text;
        Y = (int) (percentHeight * CANVAS_HEIGHT);
        X = (CANVAS_WIDTH / 2) - (BUTTON_WIDTH / 2);
    }

    public void draw(Graphics g) {
        g.drawRect(X, Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        drawCenteredString(g, buttonText, getRectangle(), BUTTON_FONT);
    }

    public Rectangle getRectangle() {
        return new Rectangle(X, Y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
}