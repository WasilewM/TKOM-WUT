package parser.program_components.expressions;

import parser.IExpression;

public record AlternativeExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
