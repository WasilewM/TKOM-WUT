package parser.program_components.statements;

import parser.IExpression;

public record ReturnStatement(IExpression value) implements IExpression {
}
