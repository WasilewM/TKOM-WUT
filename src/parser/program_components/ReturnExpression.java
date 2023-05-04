package parser.program_components;

import parser.IExpression;

public record ReturnExpression(Object value) implements IExpression {
}
