package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class InputReader {
    private BufferedReader br;

    public InputReader(String fileName) {
        InputStream in = this.getClass().getResourceAsStream(fileName);
        br = new BufferedReader(new InputStreamReader(in));
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
