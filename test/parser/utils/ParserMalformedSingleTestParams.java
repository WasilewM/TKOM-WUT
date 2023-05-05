package parser.utils;

import lexer.tokens.Token;

import java.util.List;

public record ParserMalformedSingleTestParams(List<Token> tokens, List<Exception> expectedErrorLog) {
}
