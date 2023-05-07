package parser.program_components;

import parser.IExpression;

public record ElseExpression(IExpression alternativeExp) implements IExpression {
}
