package display;

import game_info.Info;

import javax.swing.JFrame;
import java.awt.Dimension;

public class Frame implements Info {
    private JFrame frame;

    public Frame(GUI gui) {
        frame = new JFrame(FRAME_TITLE);
        int frameWidth = CANVAS_WIDTH + frame.getInsets().left + frame.getInsets().right;
        int frameHeight = CANVAS_HEIGHT + frame.getInsets().top + frame.getInsets().bottom;
        setDimensions(new Dimension(frameWidth, frameHeight));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(gui);
        frame.setVisible(true);
    }

    private void setDimensions(Dimension d) {
        frame.setSize(d);
        frame.setMinimumSize(d);
        frame.setMaximumSize(d);
        frame.setResizable(false);
    }
}
