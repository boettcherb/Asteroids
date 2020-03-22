package util;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.StringTokenizer;

public class InputReader {
    private BufferedReader br;

    public InputReader(String filePath) {
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNextLine() {
        try {
            return br.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String nextLine() {
        if (hasNextLine()) {
            try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
