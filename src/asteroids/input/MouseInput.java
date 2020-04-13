package asteroids.input;

import asteroids.display.Menu;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The MouseInput class is used to read input from the user's mouse.
 * In this project, the mouse is only used to click buttons in the menu.
 */
public class MouseInput implements MouseListener {
    private final Menu menu;

    public MouseInput(Menu menu) {
        this.menu = menu;
    }

    /**
     * The mouseClicked method is called whenever the mouse is clicked (the mouse
     * is pressed double and released without being moved).
     * @param mouseEvent contains information about the mouse clicked event
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        menu.click(mouseEvent.getPoint());
    }

    // unused methods from the MouseListener interface.
    public void mousePressed(MouseEvent mouseEvent) {}
    public void mouseReleased(MouseEvent mouseEvent) {}
    public void mouseEntered(MouseEvent mouseEvent) {}
    public void mouseExited(MouseEvent mouseEvent) {}
}
