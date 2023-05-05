package parser.program_components.expressions;

import parser.IExpression;

public record AdditionExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
