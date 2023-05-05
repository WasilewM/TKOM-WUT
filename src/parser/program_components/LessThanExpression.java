package parser.program_components;

import parser.IExpression;

public record LessThanExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
