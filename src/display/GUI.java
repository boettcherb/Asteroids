package display;

import java.awt.Canvas;

public class GUI extends Canvas implements Runnable {
    private boolean running;
    private Thread thread;

    public GUI() {
        new Frame(this);
        thread = new Thread(this);
    }

    public void start() {
        if (!running) {
            thread.start();
            running = true;
        }
    }

    public void stop() {
        if (running) {
            try {
                thread.join();
                running = false;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void run() {
        while (running) {

        }
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.start();
    }
}
