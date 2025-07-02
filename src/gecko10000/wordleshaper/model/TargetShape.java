package gecko10000.wordleshaper.model;

import static gecko10000.wordleshaper.WordleShaper.LINE_COUNT;

public class TargetShape {

    public TargetPattern[] lines;

    public TargetShape(TargetPattern[] lines) {
        if (lines.length != LINE_COUNT) {
            throw new IllegalArgumentException("Target must contain exactly " + LINE_COUNT + " lines.");
        }
        this.lines = lines;
    }

    public static TargetShape fromStrings(String[] strings) {
        TargetPattern[] patterns = new TargetPattern[strings.length];
        for (int i = 0; i < strings.length; i++) {
            patterns[i] = TargetPattern.fromString(strings[i]);
        }
        return new TargetShape(patterns);
    }

}
