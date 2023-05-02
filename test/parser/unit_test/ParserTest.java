import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserTest {

    @Test
    void parserInit() {
        ArrayList<Token> tokens = new ArrayList<>();
        Parser parser = new Parser(new MockedLexer(tokens));

        assertNotNull(parser);
    }
}
