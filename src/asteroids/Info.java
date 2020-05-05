package asteroids;

import asteroids.util.Point;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

/**
 * Info is an interface which contains all the constants and literals of this project.
 */
public interface Info {
    // general
    String GAME_TITLE = "ASTEROIDS";
    String FRAME_TITLE = "Asteroids v5";
    int TARGET_FPS = 60;
    int CANVAS_WIDTH = 1000;
    int CANVAS_HEIGHT = 700;
    int TIME_BETWEEN_LEVELS = 100;
    int EXTRA_LIFE_SCORE = 10000;
    float EPSILON = 1e-6f;

    // high score
    int NUM_HIGH_SCORES = 7;
    String[] HIGH_SCORE_KEYS = {"hs1", "hs2", "hs3", "hs4", "hs5", "hs6", "hs7"};
    Font HS_LIST_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 68);
    int HS_LIST_X = 35;
    int HS_LIST_Y = 10;
    int HS_LIST_LINE_SPACING = 68;
    int HS_LIST_SPACES = 22;
    String INITIAL_NAME = "Type your name";
    int MAX_NAME_CHARACTERS = 10;
    char[] VALID_SYMBOLS = {'-', '_', ' ', '.', ',', '\'', '\"'};
    Font HS_FONT = new Font("arial", Font.PLAIN, 60);
    Rectangle HS_RECT = new Rectangle(0, 0, CANVAS_WIDTH, 150);
    Rectangle NAME_RECT = new Rectangle(CANVAS_WIDTH / 2 - 270, 180, 540, 100);
    Font NAME_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 60);

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
    Font HS_BUTTON_FONT = new Font("arial", Font.PLAIN, 20);
    int[] PLAY_BUTTON = {CANVAS_WIDTH / 2 - 150, 280, 300, 100};
    int[] HELP_BUTTON = {CANVAS_WIDTH / 2 - 150, 400, 300, 100};
    int[] QUIT_BUTTON = {CANVAS_WIDTH / 2 - 150, 520, 300, 100};
    int[] BACK_BUTTON = {CANVAS_WIDTH / 2 - 150, 520, 300, 100};
    int[] ADD_BUTTON = {CANVAS_WIDTH / 2 - 270, 340, 540, 100};
    int[] HS_LIST_BUTTON = {30, 575, 250, 50};

    // help screen
    int HELP_TEXT_X = 30, HELP_TEXT_Y = 50;
    int LINE_SPACE = 29;
    String HELP_TEXT_FILE = "HelpText.txt";
    Font HELP_TEXT_FONT = new Font("arial", Font.PLAIN, 25);

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
    int PLAYER_FLASH_TIME = 8;

    // destroyed player lines
    int NUM_LINES = 4;
    float MAX_LINE_SPEED = 0.5f;
    float MAX_LINE_LENGTH = 20;
    float MIN_LINE_LENGTH = 5;

    // asteroids
    int STARTING_ASTEROIDS = 3;
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
    int UFO_SHOOT_TIMER = 70;
    float UFO_SPAWN_CHANCE = 0.0000005f;

    // sounds
    String FIRE_SOUND_FILE = "fire.wav";
    String THRUST_SOUND_FILE = "thrust.wav";
    String BEAT1_SOUND_FILE = "beat1.wav";
    String BEAT2_SOUND_FILE = "beat2.wav";
    int TIME_BETWEEN_BEATS = 70;
    int MIN_TIME_BETWEEN_BEATS = 30;
    int INC_TIME = 60;
    String LARGE_UFO_SOUND_FILE = "saucerBig.wav";
    String SMALL_UFO_SOUND_FILE = "saucerSmall.wav";
    String LARGE_EXPLOSION_SOUND_FILE = "bangLarge.wav";
    String MEDIUM_EXPLOSION_SOUND_FILE = "bangMedium.wav";
    String SMALL_EXPLOSION_SOUND_FILE = "bangSmall.wav";
}
