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

    private final String solution;
    private final File shapeFile;
    private TargetShape targetShape = DEFAULT_TARGET_SHAPE;

    private WordleShaper(String solution, File shapeFile) throws Exception {
        this.solution = solution;
        this.shapeFile = shapeFile;
        parseTargetShape();
    }

    private void parseTargetShape() throws Exception {
        if (shapeFile == null) return;
        if (shapeFile.exists()) {
            Stream<String> lineStream = Files.lines(shapeFile.toPath());
            List<String> lines = lineStream.toList();
            lineStream.close();
            try {
                targetShape = TargetShape.fromStrings(lines.toArray(new String[0]));
            } catch (IllegalArgumentException e) {
                System.err.println("Could not parse shape for file " + shapeFile.getName());
            }
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
                System.out.println("No solution found for " + shapeFile.getName() + " (line " + (i + 1) + ")");
                System.out.println();
                return;
            }
            usedWords.add(guess);
        }
        System.out.println("Solution for " + shapeFile.getName() + ":");
        for (String word : usedWords) {
            System.out.println(word);
        }
        System.out.println();
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
        File shapesDir = new File("shapes");
        File[] children = shapesDir.listFiles(pathname -> pathname.getName().endsWith(".txt"));
        if (children == null || !shapesDir.isDirectory() || children.length == 0) {
            new WordleShaper(solution, null).solve();
            return;
        }

        for (File shapeFile : children) {
            WordleShaper shaper = new WordleShaper(solution, shapeFile);
            shaper.solve();
        }
    }

}
