package asteroids.user_input;

import asteroids.display.GUI;
import asteroids.display.Handler;
import asteroids.objects.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    private GUI gui;
    private Handler handler;
    boolean spacePressed;

    public KeyInput(GUI gui, Handler handler) {
        this.gui = gui;
        this.handler = handler;
    }

    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            if (gui.isPlaying()) {
                gui.setPlaying(false);
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
