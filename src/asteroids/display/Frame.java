package asteroids.display;

import asteroids.Info;
import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * The Frame class creates and configures a JFrame and adds the current game instance's graphical
 * user interface as a component of the frame.
 */
public class Frame implements Info {
    private JFrame frame;

    /**
     * Constructor: Creates and configures the frame for this instance of the game
     * @param gui The current game instance's gui to be added to the frame
     */
    public Frame(GUI gui) {
        frame = new JFrame(FRAME_TITLE);
        // make sure the size of the frame takes into account any title bars or side bars
        // (otherwise a portion of the canvas would be invisible)
        int frameWidth = CANVAS_WIDTH + frame.getInsets().left + frame.getInsets().right;
        int frameHeight = CANVAS_HEIGHT + frame.getInsets().top + frame.getInsets().bottom;
        // set the size of the frame
        setDimensions(new Dimension(frameWidth, frameHeight));
        // make the red X at the top right of the frame end the program when clicked
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // place the frame in the center of the screen
        frame.setLocationRelativeTo(null);
        // add the current game instance's graphical user interface as a component of the frame
        frame.add(gui);
        // the frame is invisible by default
        frame.setVisible(true);
    }

    /**
     * Set the size of the frame and make sure that the frame cannot be resized.
     * @param d The dimensions (width and height) of the frame
     */
    private void setDimensions(Dimension d) {
        frame.setSize(d);
        // some aspects of this game were implemented with the default width and height of the
        // canvas in mind. I don't want those values to change
        frame.setMinimumSize(d);
        frame.setMaximumSize(d);
        frame.setResizable(false);
    }
}
