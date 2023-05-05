package parser.utils;

import lexer.tokens.Token;
import parser.program_components.FunctionDef;

import java.util.HashMap;
import java.util.List;

public record ParserSingleTestParams(List<Token> tokens, HashMap<String, FunctionDef> expectedFunctions) {
}
