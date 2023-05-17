package lexer.utils;

import java.util.List;

public record MultipleTokensTestParams(String inputString, List<SingleTokenDescription> tokens) {
}
