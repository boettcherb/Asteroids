package game_info;

import util.Point;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public interface Info {
    // general
    String GAME_TITLE = "ASTEROIDS";
    int MAX_FPS = 60;
    int CANVAS_WIDTH = 1000;
    int CANVAS_HEIGHT = 700;
    int BUFFER = 75;
    int MIN_X = -BUFFER, MIN_Y = -BUFFER;
    int MAX_X = CANVAS_WIDTH + BUFFER;
    int MAX_Y = CANVAS_HEIGHT + BUFFER;
    int SHIFT_X = MAX_X + BUFFER;
    int SHIFT_Y = MAX_Y + BUFFER;

    // game colors
    Color BACKGROUND_COLOR = Color.BLACK;
    Color FOREGROUND_COlOR = Color.WHITE;

    // menu particles
    Color MENU_PARTICLE_COLOR = Color.GRAY;
    int NUM_PARTICLES = 1000;
    int PARTICLE_SPEED = 3;
    int MAX_PARTICLE_SIZE = 8;

    // menu buttons
    Font BUTTON_FONT = new Font("arial", Font.PLAIN, 60);
    int BUTTON_WIDTH = 300;
    int BUTTON_HEIGHT = 100;
    float PLAY_BUTTON_HEIGHT = 0.40f;
    float HELP_BUTTON_HEIGHT = 0.57f;
    float QUIT_BUTTON_HEIGHT = 0.74f;
    float BACK_BUTTON_HEIGHT = 0.74f;
    String PLAY_BUTTON_TEXT = "PLAY";
    String HELP_BUTTON_TEXT = "HELP";
    String QUIT_BUTTON_TEXT = "QUIT";
    String BACK_BUTTON_TEXT = "BACK";

    // title (displayed on home menu screen)
    Font TITLE_FONT = new Font("arial", Font.ITALIC, 135);
    Rectangle TITLE_RECT = new Rectangle(0, 0, CANVAS_WIDTH, (int) (CANVAS_HEIGHT * 0.4));

    // player / player movement
    Point[] PLAYER_POINTS = { new Point(0, -33), new Point(15, 20), new Point(0, 10), new Point(-15, 20) };
    float PLAYER_ACCELERATION = 0.2f;
    float PLAYER_DECELERATION = 0.984f;
    float MIN_VELOCITY = 0.1f;
    float PLAYER_TURN_RATE = 0.1f;
    int PLAYER_RESPAWN_TIME = 150;

    // asteroids
    float LARGE_ASTEROID_SPEED = 0.5f;
    float MEDIUM_ASTEROID_SPEED = 0.7f;
    float SMALL_ASTEROID_SPEED = 0.9f;
    Point[][] ASTEROID_POINTS = {
        {
            new Point(-37, -74), new Point(21, -74), new Point(75, -42), new Point(75, -20),
            new Point(27, -4), new Point(75, 27), new Point(43, 75), new Point(16, 42),
            new Point(-21, 75), new Point(-74, 11), new Point(-74, -42), new Point(-15, -42)
        },
        {
            new Point(-75, -40), new Point(-40, -75), new Point(-3, -42), new Point(37, -75),
            new Point(75, -45), new Point(51, -5), new Point(73, 37), new Point(17, 75),
            new Point(-46, 75), new Point(-75, 47)
        },
        {
            new Point(-74, -18), new Point(-18, -74), new Point(48, -74), new Point(75, -21),
            new Point(75, 5), new Point(19, 75), new Point(-18, 75), new Point(-7, 5),
            new Point(-45, 74), new Point(-74, 15), new Point(-37, -3)
        },
        {
            new Point(-74, -37), new Point(-29, -74), new Point(-2, -55), new Point(37, -74),
            new Point(75, -37), new Point(31, -18), new Point(75, 19), new Point(29, 75),
            new Point(-20, 54), new Point(-36, 75), new Point(-74, 36), new Point(-52, 0)
        }
    };

    // bullets
    Point[] BULLET_POINTS = { new Point(-1, -1), new Point(-1, 1), new Point(1, 1), new Point(1, -1) };
    int BULLET_SPEED = 11;
    int BULLET_LIFE = 60;
    int MAX_BULLET_PATH = 2 * BULLET_SPEED;
}
