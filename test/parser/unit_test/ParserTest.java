import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserTest {

    @Test
    void parserInit() {
        ArrayList<Token> tokens = new ArrayList<>();
        Parser parser = new Parser(new MockedLexer(tokens));

        assertNotNull(parser);
    }

    @Test
    void parseEmptyTokenList() {
        ArrayList<Token> tokens = new ArrayList<>();
        Parser parser = new Parser(new MockedLexer(tokens));
        Program program = parser.parse();

        assertEquals(new HashMap<String, FunctionDef>(), program.functions());
    }

    @Test
    void parseOnlyMainIdentifier() {
        ArrayList<Token> tokens = new ArrayList<>(
                Arrays.asList(
                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                        )
        );
        Parser parser = new Parser(new MockedLexer(tokens));
        Program program = parser.parse();
        HashMap<String, FunctionDef> expectedFunctions = new HashMap<String, FunctionDef>();
        expectedFunctions.put("main", new FunctionDef("main", TokenTypeEnum.INT_KEYWORD, new ArrayList<Parameter>(), new BlockStatement(new ArrayList<IStatement>())));

        assertEquals(expectedFunctions, program.functions());
    }
}
