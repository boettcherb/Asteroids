package user_input;

import display.Menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {
    private Menu menu;

    public MouseInput(Menu menu) {
        this.menu = menu;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        menu.click(mouseEvent.getPoint());
    }

    public void mousePressed(MouseEvent mouseEvent) {}
    public void mouseReleased(MouseEvent mouseEvent) {}
    public void mouseEntered(MouseEvent mouseEvent) {}
    public void mouseExited(MouseEvent mouseEvent) {}
}
