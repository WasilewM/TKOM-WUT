package parser.program_components;

import parser.IExpression;

public record ReturnExpression(IExpression value) implements IExpression {
}
