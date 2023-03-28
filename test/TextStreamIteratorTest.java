import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextStreamIteratorTest {
    @Test
    void textStreamIteratorInit() {
        String text = "hello world";
        TextStreamIterator iterator = new TextStreamIterator(text);
        assertEquals(text, iterator.getText());
        assertEquals(0, iterator.getCurrentIndex());
    }

    @Test
    void textStreamIterator_getCurrentCharacter() {
        String text = "hello world";
        TextStreamIterator iterator = new TextStreamIterator(text);
        assertEquals('h', iterator.getCurrentCharacter());
    }

    @Test
    void textStreamIterator_moveToNextCharacter_singleTime() {
        String text = "hello world";
        TextStreamIterator iterator = new TextStreamIterator(text);
        assertEquals('e', iterator.moveToNextCharacter());
    }

    @Test
    void textStreamIterator_moveToNextCharacter_multipleTimes() {
        String text = "hello world";
        TextStreamIterator iterator = new TextStreamIterator(text);
        assertEquals('e', iterator.moveToNextCharacter());
        assertEquals('l', iterator.moveToNextCharacter());
        assertEquals('l', iterator.moveToNextCharacter());
        assertEquals('o', iterator.moveToNextCharacter());
    }

    @Test
    void textStreamIterator_moveToNextCharacter_multipleTimesAndExceedText() {
        String text = "abc";
        TextStreamIterator iterator = new TextStreamIterator(text);
        iterator.moveToNextCharacter();
        iterator.moveToNextCharacter();
        assertEquals(TextStreamIterator.ETX, iterator.moveToNextCharacter());
    }
}
