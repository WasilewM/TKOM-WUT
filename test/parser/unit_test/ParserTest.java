import lexer.Position;
import lexer.tokens.StringToken;
import lexer.tokens.Token;
import lexer.TokenTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import parser.Parser;
import parser.program_components.Program;
import parser.program_components.FunctionDef;
import parser.program_components.BlockStatement;
import utils.MockedExitErrorHandler;
import utils.MockedLexer;
import utils.ParserSingleTestParams;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserTest {

    @Test
    void parserInit() {
        ArrayList<Token> tokens = new ArrayList<>();
        Parser parser = new Parser(new MockedLexer(tokens), new MockedExitErrorHandler());

        assertNotNull(parser);
    }

    @ParameterizedTest
    @MethodSource("generateTestProgramData")
    void parseProgram(ParserSingleTestParams testParams) {
        ArrayList<Token> tokens = new ArrayList<>(testParams.tokens());
        Parser parser = new Parser(new MockedLexer(tokens), new MockedExitErrorHandler());
        Program program = parser.parse();

        assertEquals(testParams.expectedFunctions(), program.functions());
    }

    static Stream<Arguments> generateTestProgramData() {
        return Stream.of(
                Arguments.of(
                        new ParserSingleTestParams(
                                new ArrayList<>(),
                                new HashMap<>()
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.INT_KEYWORD),
                                        new StringToken("main", new Position(1, 5), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 9), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 10), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("main", new FunctionDef("main", TokenTypeEnum.INT_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.DOUBLE_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 15), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.DOUBLE_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.STRING_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 15), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.STRING_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.BOOL_KEYWORD),
                                        new StringToken("func", new Position(1, 6), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 10), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 11), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.BOOL_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.POINT_KEYWORD),
                                        new StringToken("func", new Position(1, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.POINT_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.SECTION_KEYWORD),
                                        new StringToken("func", new Position(1, 9), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 13), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 15), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 16), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.SECTION_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.FIGURE_KEYWORD),
                                        new StringToken("func", new Position(1, 8), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 12), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 15), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.FIGURE_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                ),
                Arguments.of(
                        new ParserSingleTestParams(
                                Arrays.asList(
                                        new Token(new Position(1, 1), TokenTypeEnum.SCENE_KEYWORD),
                                        new StringToken("func", new Position(1, 7), TokenTypeEnum.IDENTIFIER),
                                        new Token(new Position(1, 11), TokenTypeEnum.LEFT_BRACKET),
                                        new Token(new Position(1, 12), TokenTypeEnum.RIGHT_BRACKET),
                                        new Token(new Position(1, 13), TokenTypeEnum.LEFT_CURLY_BRACKET),
                                        new Token(new Position(1, 14), TokenTypeEnum.RIGHT_CURLY_BRACKET)
                                ),
                                new HashMap<>() {{
                                    put("func", new FunctionDef("func", TokenTypeEnum.SCENE_KEYWORD, new ArrayList<>(), new BlockStatement(new ArrayList<>())));
                                }}
                        )
                )
        );
    }
}
