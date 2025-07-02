package gecko10000.wordleshaper.model;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static gecko10000.wordleshaper.WordleShaper.WORD_LENGTH;

public class TargetPattern {

    CharColor[] wordColorings;

    public TargetPattern(CharColor[] wordColorings) {
        if (wordColorings.length != WORD_LENGTH) {
            throw new IllegalArgumentException("Target pattern must be " + WORD_LENGTH +
                    " characters but was " + wordColorings.length);
        }
        this.wordColorings = wordColorings;
    }

    // https://paste.gecko.lol/5szk.png
    // Green is first priority,
    // then yellow is done from left to right.
    public boolean fitsCriteria(String solution, String guess) {
        // Tracks which chars can be marked yellow
        // vs gray. Used for cases as shown above.
        Map<Character, Integer> remainingCorrect = new HashMap<>();
        for (char c : solution.toCharArray()) {
            remainingCorrect.compute(c, (k, v) -> (v == null ? 0 : v) + 1);
        }
        // Ensure greens are green.
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (wordColorings[i] != CharColor.GREEN) continue;
            char c = solution.charAt(i);
            if (c != guess.charAt(i)) {
                Thread.dumpStack();
                return false;
            }
            remainingCorrect.put(c, remainingCorrect.get(c) - 1);
        }
        // Now we go left to right and check yellow/gray.
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (wordColorings[i] == CharColor.GREEN) continue;
            // See if this spot would be marked yellow or gray.
            char guessed = guess.charAt(i);
            // Green, doesn't fit
            if (guessed == solution.charAt(i)) return false;
            int count = remainingCorrect.getOrDefault(guessed, 0);
            // Expecting gray, only correct when no options left.
            if (wordColorings[i] == CharColor.GRAY) {
                if (count == 0) {
                    continue;
                } else {
                    return false;
                }
            }
            // Yellow for sure
            // No char left for it though
            if (count == 0) {
                return false;
            }
            // Works, subtract 1 from char count.
            remainingCorrect.put(guessed, count - 1);
        }
        return true;
    }

    @Override
    public String toString() {
        return Stream.of(wordColorings)
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    public static TargetPattern fromString(String line) {
        CharColor[] colorings = new CharColor[line.length()];
        for (int i = 0; i < line.length(); i++) {
            colorings[i] = CharColor.fromChar(line.charAt(i));
        }
        return new TargetPattern(colorings);
    }

}
