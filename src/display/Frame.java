package display;

import game_info.Info;

import javax.swing.*;
import java.awt.*;


public class Frame implements Info {
    private JFrame frame;

    public Frame(GUI gui) {
        frame = new JFrame(GAME_TITLE);
        int frameWidth = WIDTH + frame.getInsets().left + frame.getInsets().right;
        int frameHeight = HEIGHT + frame.getInsets().top + frame.getInsets().bottom;
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
