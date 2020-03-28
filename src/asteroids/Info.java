package asteroids;

import asteroids.util.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public interface Info {
    // general
    String GAME_TITLE = "ASTEROIDS";
    String FRAME_TITLE = "Asteroids v3";
    int MAX_FPS = 60;
    int CANVAS_WIDTH = 1000;
    int CANVAS_HEIGHT = 700;
    int TIME_BETWEEN_LEVELS = 100;
    int EXTRA_LIFE_SCORE = 10000;

    // game colors
    Color BACKGROUND_COLOR = Color.BLACK;
    Color FOREGROUND_COlOR = Color.WHITE;

    // menu particles
    Color MENU_PARTICLE_COLOR = Color.GRAY;
    int NUM_MENU_PARTICLES = 1000;
    int MENU_PARTICLE_SPEED = 3;
    int MAX_MENU_PARTICLE_SIZE = 8;

    // menu buttons
    Font BUTTON_FONT = new Font("arial", Font.PLAIN, 60);
    int BUTTON_WIDTH = 300;
    int BUTTON_HEIGHT = 100;
    float PLAY_BUTTON_HEIGHT = 0.40f;
    float HELP_BUTTON_HEIGHT = 0.57f;
    float QUIT_BUTTON_HEIGHT = 0.74f;
    String PLAY_BUTTON_TEXT = "PLAY";
    String HELP_BUTTON_TEXT = "HELP";
    String QUIT_BUTTON_TEXT = "QUIT";

    // help screen
    float BACK_BUTTON_HEIGHT = 0.74f;
    String BACK_BUTTON_TEXT = "BACK";
    int HELP_TEXT_X = 30, HELP_TEXT_Y = 50;
    int LINE_SPACE = 50;
    String HELP_TEXT_FILE = "HelpText.txt";
    Font HELP_TEXT_FONT = new Font("arial", Font.PLAIN, 28);

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
    int SCORE_X = 15;
    int SCORE_Y = CANVAS_HEIGHT - 50;

    // player / player movement
    Point[] PLAYER_POINTS = {
        new Point(0, -30), new Point(-15, 15), new Point(-12, 7),
        new Point(-6, 7), new Point(0, 20), new Point(6, 7),
        new Point(-5, 7), new Point(12, 7), new Point(15, 15)
    };
    int FIRST_AC_POINT = 3;
    int LAST_AC_POINT = 5;
    float SHOW_THRUST_CHANCE = 0.7f;
    float PLAYER_ACCELERATION = 0.14f;
    float PLAYER_DECELERATION = 0.984f;
    float MIN_VELOCITY = 0.1f;
    float PLAYER_TURN_RATE = 0.075f;
    int PLAYER_RESPAWN_TIME = 150;
    int PLAYER_SAVE_TIME = 150;
    int PLAYER_FLASH_TIME = 10;

    // destroyed player lines
    int NUM_LINES = 4;
    float MAX_LINE_SPEED = 0.5f;
    float MAX_LINE_LENGTH = 20;
    float MIN_LINE_LENGTH = 5;

    // asteroids
    float LARGE_ASTEROID_SPEED = 1.8f;
    float MAX_MEDIUM_ASTEROID_SPEED = 3f;
    float MAX_SMALL_ASTEROID_SPEED = 5f;
    float MIN_ASTEROID_SPEED = 0.3f;
    int SMALL_SCORE = 100;
    int MEDIUM_SCORE = 50;
    int LARGE_SCORE = 20;
    int MIN_SPAWN_DIST_FROM_PLAYER = 300;
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
    Point[] BULLET_POINTS = {
        new Point(-1, -1), new Point(-1, 1), new Point(1, 1), new Point(1, -1)
    };
    int BULLET_SPEED = 11;
    int BULLET_LIFE = 50;
    int BULLET_STARTING_DIST = 30;
    int MAX_BULLET_PATH_LENGTH = 2 * BULLET_SPEED;

    // debris particles
    Point[] DEBRIS_PARTICLE_POINTS = {
        new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(0, 1)
    };
    float MAX_DEBRIS_PARTICLE_SPEED = 6;
    int NUM_DEBRIS_PARTICLES = 4;
    int MAX_DEBRIS_PARTICLE_LIFE = 50;

    // UFOs
    Point[] UFO_POINTS = {
        new Point(-8, -25), new Point(-10, -12), new Point(-30, 0), new Point(-15, 10),
        new Point(15, 10), new Point(30, 0), new Point(-30, 0), new Point(30, 0),
        new Point(10, -12), new Point(-10, -12), new Point(10, -12), new Point(8, -25),
    };
    float SMALL_UFO_SCALE = 0.5f;
    int SMALL_UFO_SCORE = 1000;
    int LARGE_UFO_SCORE = 200;
    int UFO_SPEED = 2;
    int UFO_TURN_TIMER = 40;
    float UFO_SPAWN_CHANCE = 0.0000005f;

    // sounds
    String FIRE_SOUND_FILE = "fire.wav";
    String THRUST_SOUND_FILE = "thrust.wav";
    String BEAT1_SOUND_FILE = "beat1.wav";
    String BEAT2_SOUND_FILE = "beat2.wav";
    int TIME_BETWEEN_BEATS = 70;
    int MIN_TIME_BETWEEN_BEATS = 25;
    int INC_TIME = 100;
    String LARGE_UFO_SOUND_FILE = "saucerBig.wav";
    String SMALL_UFO_SOUND_FILE = "saucerSmall.wav";
    String LARGE_EXPLOSION_SOUND_FILE = "bangLarge.wav";
    String MEDIUM_EXPLOSION_SOUND_FILE = "bangMedium.wav";
    String SMALL_EXPLOSION_SOUND_FILE = "bangSmall.wav";
}
