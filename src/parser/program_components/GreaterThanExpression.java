package parser.program_components;

import parser.IExpression;

public record GreaterThanExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
