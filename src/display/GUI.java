package display;

import java.awt.Canvas;

public class GUI extends Canvas implements Runnable {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public GUI() {
        new Frame(this);
    }

    public void run() {

    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        Thread thread = new Thread(gui);
        thread.start();
    }
}
