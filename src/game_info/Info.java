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
    int TIME_BETWEEN_LEVELS = 100;

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

    // hud
    float LIVES_SCALE = 0.5f;
    int STARTING_LIVES = 3;
    int LIVES_X = 20;
    int LIVES_Y = 30;
    int LIVES_DIST = 30;
    Font SCORE_FONT = new Font("arial", Font.PLAIN, 15);
    int SCORE_X = 20;
    int SCORE_Y = CANVAS_HEIGHT - 50;

    // player / player movement
    Point[] PLAYER_POINTS = { new Point(0, -33), new Point(15, 20), new Point(0, 10), new Point(-15, 20) };
    float PLAYER_ACCELERATION = 0.18f;
    float PLAYER_DECELERATION = 0.984f;
    float MIN_VELOCITY = 0.1f;
    float PLAYER_TURN_RATE = 0.075f;
    int PLAYER_RESPAWN_TIME = 150;
    int PLAYER_SAVE_TIME = 400;
    int PLAYER_FLASH_TIME = 20;

    // asteroids
    float LARGE_ASTEROID_SPEED = 0.7f;
    float MAX_MEDIUM_ASTEROID_SPEED = 2f;
    float MAX_SMALL_ASTEROID_SPEED = 4f;
    float MIN_ASTEROID_SPEED = 0.3f;
    int SMALL_SCORE = 100;
    int MEDIUM_SCORE = 50;
    int LARGE_SCORE = 20;
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
    int BULLET_LIFE = 50;
    int BULLET_STARTING_DIST = 30;
    int MAX_BULLET_PATH_LENGTH = 2 * BULLET_SPEED;
}
