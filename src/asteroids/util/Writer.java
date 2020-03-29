package asteroids.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private FileWriter writer;

    public Writer(String fileName) {
        try {
            writer = new FileWriter(new File(this.getClass().getResource(fileName).getPath()), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int num) {
        try {
            writer.write(Integer.toString(num));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
