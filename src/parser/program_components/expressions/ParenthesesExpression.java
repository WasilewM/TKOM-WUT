package parser.program_components.expressions;

import parser.IExpression;

public record ParenthesesExpression(IExpression alternativeExp) implements IExpression {
}
