package asteroids.display;

import asteroids.Info;
import asteroids.user_input.*;
import asteroids.util.Sound;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

/**
 * The GUI class is the main class of this game. It contains the main method and the
 * game loop, and it is where the different components of the game, such as the
 * Handler, the Menu, and the HUD, are created.
 */
public class GUI extends Canvas implements Info {
    private boolean running; // true if the program is running
    private boolean playing; // true if the game is in play (not in the menus)
    private Thread gameThread;
    private Handler handler;
    private Menu menu;
    private HUD hud;

    public GUI() {
        gameThread = new Thread(this::gameLoop);
        menu = new Menu(this);
        hud = new HUD();
        handler = new Handler(hud, menu);
        // create the frame and pass this GUI to it (this GUI
        // will be added to the frame as a component)
        new Frame(this);
        // add user input as event listeners
        addMouseListener(new MouseInput(menu));
        addKeyListener(new KeyInput(this, handler));
        // Load sound now so that it is not loaded while the user is playing
        // See the Sound class for a better explanation
        Sound.initialize();
        // set the rendering to take place with a triple-buffering strategy
        createBufferStrategy(3);
    }

    /**
     * Sets the running boolean to true, runs the game thread, and begins the game loop
     */
    public void start() {
        if (!running) {
            running = true;
            gameThread.start();
        }
    }

    /**
     * Ends the game by terminating the game loop and joining
     * the game thread to the main thread.
     */
    public void stop() {
        if (running) {
            try {
                running = false;
                gameThread.join();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }

    /**
     * The game loop is called by the game thread after the user calls start(). The tick method
     * updates the position or state of the game, and the render method draws the current state
     * to the canvas. The game loop is designed so that the tick method will be called around
     * TARGET_FPS times per second, every second. If this was not the case, faster computers
     * would cause the game to move too fast for humans to play.
     */
    private void gameLoop() {
        long lastTime = System.nanoTime();
        double nanosecondsPerTick = 1e9 / TARGET_FPS;
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nanosecondsPerTick;
            lastTime = now;
            while (delta >= 1) {
                tick();
                --delta;
            }
            render();
        }
    }

    /**
     * The tick method is called TARGET_FPS times per second by the game loop. When the game is
     * ticked, the state of the game is updated. This method calls the tick methods in either
     * the handler or the menu, depending on whether or not the game is in play.
     */
    public void tick() {
        if (playing) {
            handler.tick();
        } else {
            menu.tick();
        }
    }

    /**
     * The render method draws to the canvas. It takes the buffer strategy's graphics,
     * renders the current state of the game, and then disposes those graphics to the canvas.
     */
    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        // the following loops safeguard against lost and repeated
        // frames, which happen every now and then.
        do {
            do {
                // the graphics object comes from the canvas's buffer strategy
                Graphics g = bs.getDrawGraphics();
                // draw the background
                g.setColor(BACKGROUND_COLOR);
                g.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
                // draw the current state of the game. What is drawn
                // depends on whether or not the game is in play
                g.setColor(FOREGROUND_COlOR);
                if (playing) {
                    handler.render(g);
                    hud.render(g);
                } else {
                    menu.render(g);
                }
                g.dispose();
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    /**
     * Getter method, returns the value of the playing boolean
     * @return True if the game is in play, false otherwise
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     * Setter method, sets the state of the game to either playing == true (if the user starts
     * a new game) or playing == false (if the user's game ends)
     * @param play The new value of the playing boolean
     */
    public void setPlaying(boolean play) {
        if (play) {
            // if a new game is starting, tell the handler
            handler.newGame();
        } else {
            // if the user's game ends, reset the menus back to the start menu
            menu.setStartMenu();
        }
        playing = play;
    }

    /**
     * All the main method has to do is create an instance of GUI and start it. Doing this
     * creates a frame, creates and runs a new thread, and begins the game loop. Once the user
     * ends the game loop, the program also ends.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        new GUI().start();
    }
}
