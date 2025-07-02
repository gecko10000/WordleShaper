package gecko10000.wordleshaper;

import java.io.IOException;
import java.io.InputStream;

public class Data {
    public static final String[] VALID_WORDS;

    static {
        InputStream stream = null;
        try {
            stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("words.txt");
            if (stream == null) throw new InternalError("Couldn't find words.txt.");
            byte[] wordBytes = stream.readAllBytes();
            VALID_WORDS = new String(wordBytes).split("\\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (stream == null) throw new InternalError("Couldn't open words.txt.");
            try {
                stream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
