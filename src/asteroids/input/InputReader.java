package asteroids.input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class InputReader {
    private BufferedReader br;
    private StringTokenizer st;

    public InputReader(String fileName) {
        InputStream in = this.getClass().getResourceAsStream(fileName);
        br = new BufferedReader(new InputStreamReader(in));
    }

    public boolean hasNext() {
        try {
            return (st != null && st.hasMoreTokens()) || br.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String next() {
        if (!hasNext()) {
            throw new RuntimeException("Nothing more to read from this file");
        }
        if (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public String nextLine() {
        if (hasNext()) {
            try {
                return br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
