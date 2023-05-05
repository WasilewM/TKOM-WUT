package parser.program_components;

import parser.IExpression;

public record LessOrEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
