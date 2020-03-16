package game_info;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

public interface Info {
    String GAME_TITLE = "ASTEROIDS";
    int CANVAS_WIDTH = 1000;
    int CANVAS_HEIGHT = 700;

    Color BACKGROUND_COLOR = Color.BLACK;
    Color FOREGROUND_COlOR = Color.WHITE;
    Color MENU_PARTICLE_COLOR = Color.GRAY;

    int NUM_PARTICLES = 1000;
    int PARTICLE_SPEED = 3;
    int MAX_PARTICLE_SIZE = 8;

    Font BUTTON_FONT = new Font("arial", Font.PLAIN, 60);
    int BUTTON_WIDTH = 300;
    int BUTTON_HEIGHT = 100;

    Font TITLE_FONT = new Font("arial", Font.ITALIC, 135);
    Rectangle TITLE_RECT = new Rectangle(0, 0, CANVAS_WIDTH, (int) (CANVAS_HEIGHT * 0.4));

    float PLAY_BUTTON_HEIGHT = 0.40f;
    float HELP_BUTTON_HEIGHT = 0.57f;
    float QUIT_BUTTON_HEIGHT = 0.74f;
    String PLAY_BUTTON_TEXT = "PLAY";
    String HELP_BUTTON_TEXT = "HELP";
    String QUIT_BUTTON_TEXT = "QUIT";
}
