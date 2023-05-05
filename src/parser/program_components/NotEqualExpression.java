package parser.program_components;

import parser.IExpression;

public record NotEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
