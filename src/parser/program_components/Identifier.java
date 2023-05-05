package parser.program_components;

import parser.IExpression;

public record Identifier(String name) implements IExpression {
}
