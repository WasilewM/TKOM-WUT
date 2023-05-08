package parser.program_components;

import parser.IExpression;

public record ParenthesesExpression(IExpression alternativeExp) implements IExpression {
}
