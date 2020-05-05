package asteroids.util;

import asteroids.Info;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;

/**
 * A button in this project is essentially just a rectangle on the screen. Buttons are only used in
 * the menus. A button is "clicked" when the mouse listener detects a click whose point is contained
 * inside the button's rectangle. The position and size of each button are constants that are defined
 * in the Info interface. Each button has a black background and a string of text at its center.
 */
public class Button implements Info {
    private final String buttonText;
    private final int X, Y, width, height;
    private final Font font;

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

    /**
     * Draw the button to the canvas. This method is called for each button by the render method of
     * the menus. This method draws the button with the drawRect() and drawString() methods of the
     * canvas's graphics object.
     * @param g the graphics object of the canvas
     */
    public void draw(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(X, Y, width, height);
        g.setColor(FOREGROUND_COlOR);
        g.drawRect(X, Y, width, height);
        drawCenteredString(g, buttonText, getRectangle(), font);
    }

    /**
     * Given a rectangle, a string, and a font, use a graphics object to draw the string
     * at the center of the rectangle in the given font.
     * @param g The graphics object of the canvas
     * @param text The text to draw at the center of the rectangle
     * @param rect The rectangle
     * @param font The font of the text
     */
    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    // Getters and Setters

    public Rectangle getRectangle() {
        return new Rectangle(X, Y, width, height);
    }
}