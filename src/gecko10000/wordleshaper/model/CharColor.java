package gecko10000.wordleshaper.model;

public enum CharColor {
    GRAY,
    YELLOW,
    GREEN,
    ;


    public static CharColor fromChar(char c) {
        return switch (c) {
            case 'X' -> GRAY;
            case 'Y' -> YELLOW;
            case 'G' -> GREEN;
            default -> throw new IllegalArgumentException("Invalid CharColor " + c);
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case GRAY -> "X";
            case YELLOW -> "Y";
            case GREEN -> "G";
        };
    }
}
