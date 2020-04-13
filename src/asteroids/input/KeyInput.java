package asteroids.input;

import asteroids.display.GUI;
import asteroids.display.Handler;
import asteroids.display.Menu;
import asteroids.objects.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    private final GUI gui;
    private final Menu menu;
    private final Handler handler;
    boolean spacePressed;
    private Name name;

    public KeyInput(GUI gui, Menu menu, Handler handler) {
        this.gui = gui;
        this.menu = menu;
        this.handler = handler;
    }

    /**
     * The keyPressed method is defined in the KeyListener interface
     * and is called whenever a key is pushed down.
     * @param keyEvent contains information about the key down event
     */
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        // if ESC is pressed, either exit the game (to the menus) or
        // quit the program.
        if (key == KeyEvent.VK_ESCAPE) {
            if (gui.isPlaying()) {
                menu.endGame(0);
            } else {
                gui.stop();
            }
        }
        // read in input for controlling the player (arrow keys and space)
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
        // if the user is typing their name to be recorded on the high score
        // list, the letters typed will be added to the name here
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

    /**
     * The keyReleased method is called whenever the user lifts their finger
     * off of a key.
     * @param keyEvent contains information about the key released event
     */
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

    // unused methods from the KeyListener interface
    public void keyTyped(KeyEvent keyEvent) {}
}
