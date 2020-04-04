package asteroids.util;

import asteroids.Info;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;

public class Button implements Info {
    private final String buttonText;
    private final int X, Y, width, height;
    private Font font;

    public Button(int[] buttonAttributes, String text, Font font) {
        if (buttonAttributes == null || buttonAttributes.length != 4) {
            throw new IllegalArgumentException("Button: Button(): buttonAttributes must be an array of 4 ints");
        }
        buttonText = text;
        X = buttonAttributes[0];
        Y = buttonAttributes[1];
        width = buttonAttributes[2];
        height = buttonAttributes[3];
        this.font = font;
    }

    public void draw(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(X, Y, width, height);
        g.setColor(FOREGROUND_COlOR);
        g.drawRect(X, Y, width, height);
        drawCenteredString(g, buttonText, getRectangle(), font);
    }

    public Rectangle getRectangle() {
        return new Rectangle(X, Y, width, height);
    }

    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }
}