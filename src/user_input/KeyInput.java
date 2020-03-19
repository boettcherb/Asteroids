package user_input;

import display.GUI;
import display.Handler;
import shape.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    private GUI gui;
    private Handler handler;

    public KeyInput(GUI gui, Handler handler) {
        this.gui = gui;
        this.handler = handler;
    }

    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
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
            if (key == KeyEvent.VK_SPACE) {
                handler.addShape(player.shoot());
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
        }
    }

    public void keyTyped(KeyEvent keyEvent) {}
}
