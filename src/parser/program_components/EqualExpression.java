package parser.program_components;

import parser.IExpression;

public record EqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
