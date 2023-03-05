public class TextStreamIterator {
    private final String text;
    private int currentIndex;

    static final char ETX = '^';

    public TextStreamIterator(String text) {
        this.text = text;
        this.currentIndex = 0;
    }

    public String getText() {
        return text;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public char getCurrentCharacter() {
        return text.charAt(currentIndex);
    }

    public char moveToNextCharacter() throws StringIndexOutOfBoundsException{
        this.currentIndex = currentIndex + 1;
        try {
            return text.charAt(currentIndex);
        }
        catch (StringIndexOutOfBoundsException e) {
            return TextStreamIterator.ETX;
        }

    }
}
