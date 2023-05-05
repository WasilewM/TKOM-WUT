package parser.program_components;

import parser.IExpression;

public record AlternativeExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
