package display;

import game_info.Info;
import user_input.*;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class GUI extends Canvas implements Runnable, Info {
    private boolean running;
    private boolean playing;
    private Thread thread;
    private Menu menu;
    private Handler handler;

    public GUI() {
        new Frame(this);
        thread = new Thread(this);
        menu = new Menu(this);
        handler = new Handler();
        addMouseListener(new MouseInput(menu));
        createBufferStrategy(3);
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
                running = false;
                thread.join();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    public void run() {
        long lastTime = System.nanoTime();
        double maxFramesPerSecond = 60.0;
        double ns = 1e9 / maxFramesPerSecond;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                --delta;
            }
            render();
        }
    }

    public void tick() {
        if (playing) {
            handler.tick();
        } else {
            menu.tick();
        }

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g = bs.getDrawGraphics();
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        g.setColor(FOREGROUND_COlOR);
        if (playing) {
            handler.render(g);
        } else {
            menu.render(g);
        }
        g.dispose();
        bs.show();
    }

    public void setPlaying(boolean play) {
        playing = play;
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.start();
    }
}
