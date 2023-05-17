package parser.utils;

import lexer.tokens.Token;
import parser.IFunctionDef;

import java.util.HashMap;
import java.util.List;

public record ParserSingleTestParams(List<Token> tokens, HashMap<String, IFunctionDef> expectedFunctions) {
}
