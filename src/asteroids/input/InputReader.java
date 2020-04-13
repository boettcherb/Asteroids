package asteroids.input;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * The InputReader class combines all of the functionality of reading in
 * input from a file into one class. In this project, the InputReader is
 * used to read in the text that is displayed on the help screen, which
 * is contained in the HelpText.txt file.
 */
public class InputReader {
    private final BufferedReader br;

    public InputReader(String fileName) {
        InputStream in = this.getClass().getResourceAsStream(fileName);
        br = new BufferedReader(new InputStreamReader(in));
    }

    /**
     * This method determines if there is more text to be read from
     * the buffered reader.
     * @return true if there is more to read, false otherwise
     */
    public boolean hasNext() {
        try {
            return br.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Read in the next line from the buffered reader. Only read the next
     * line if hasNext() is true.
     * @return the next line as a string, or null if !hasNext()
     */
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
