package parser.program_components.expressions;

import parser.IExpression;

public record ConjunctiveExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
}
