package gecko10000.wordleshaper;

import gecko10000.wordleshaper.model.TargetPattern;
import gecko10000.wordleshaper.model.TargetShape;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WordleShaper {

    public static final int WORD_LENGTH = 5;
    public static final int LINE_COUNT = 6;

    // :)
    private static final TargetShape DEFAULT_TARGET_SHAPE = TargetShape.fromStrings(new String[]{
            "XXXXX",
            "XYXYX",
            "XXXXX",
            "YXXXY",
            "XYYYX",
            "XXXXX",
    });

    private String solution;
    private TargetShape targetShape = DEFAULT_TARGET_SHAPE;

    private WordleShaper(String solution) throws Exception {
        this.solution = solution;
        parseTargetShape();
    }

    private void parseTargetShape() throws Exception {
        File shapeFile = new File("shape.txt");
        if (shapeFile.exists()) {
            Stream<String> lineStream = Files.lines(shapeFile.toPath());
            List<String> lines = lineStream.toList();
            lineStream.close();
            targetShape = TargetShape.fromStrings(lines.toArray(new String[0]));
        }
    }

    private void solve() throws Exception {
        List<String> usedWords = new ArrayList<>();
        for (int i = 0; i < LINE_COUNT; i++) {
            TargetPattern pattern = targetShape.lines[i];
            String guess = Stream.of(Data.VALID_WORDS)
                    .filter(w -> pattern.fitsCriteria(solution, w))
                    .filter(w -> !usedWords.contains(w)).findFirst().orElse(null);
            if (guess == null) {
                System.err.println("Could not find word for pattern " + pattern);
                return;
            }
            usedWords.add(guess);
        }
        for (String word : usedWords) {
            System.out.println(word);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: wordleshaper <solution>");
            return;
        }
        String solution = args[0];
        if (solution.length() != WORD_LENGTH) {
            System.err.println("Solution must be " + WORD_LENGTH + " characters.");
            return;
        }
        WordleShaper shaper = new WordleShaper(solution);
        shaper.solve();
    }

}
