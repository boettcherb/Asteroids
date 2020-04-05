package asteroids.input;

import asteroids.display.GUI;
import asteroids.display.Handler;
import asteroids.display.Menu;
import asteroids.objects.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    private GUI gui;
    private Menu menu;
    private Handler handler;
    boolean spacePressed;
    private Name name;

    public KeyInput(GUI gui, Menu menu, Handler handler) {
        this.gui = gui;
        this.menu = menu;
        this.handler = handler;
    }

    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            if (gui.isPlaying()) {
                menu.endGame(0);
            } else {
                gui.stop();
            }
        }
        Player player = handler.getPlayer();
        if (gui.isPlaying() && player != null) {
            if (key == KeyEvent.VK_UP) {
                player.setAccelerate(true);
            }
            if (key == KeyEvent.VK_RIGHT) {
                player.setTurnRight(true);
            }
            if (key == KeyEvent.VK_LEFT) {
                player.setTurnLeft(true);
            }
            if (key == KeyEvent.VK_SPACE && !spacePressed) {
                handler.addShape(player.shoot());
                spacePressed = true;
            }
        }
        if (menu.readingName()) {
            if (name == null) {
                name = menu.getName();
            }
            if (key == KeyEvent.VK_BACK_SPACE) {
                name.backspace();
            } else {
                name.addLetter(keyEvent.getKeyChar());
            }
        } else {
            name = null;
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        Player player = handler.getPlayer();
        if (gui.isPlaying() && player != null) {
            if (key == KeyEvent.VK_UP) {
                player.setAccelerate(false);
            }
            if (key == KeyEvent.VK_RIGHT) {
                player.setTurnRight(false);
            }
            if (key == KeyEvent.VK_LEFT) {
                player.setTurnLeft(false);
            }
            if (key == KeyEvent.VK_SPACE) {
                spacePressed = false;
            }
        }
    }

    public void keyTyped(KeyEvent keyEvent) {}
}
