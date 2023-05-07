package parser.program_components;

import parser.IExpression;

public record WhileExpression(IExpression alternativeExp, CodeBlock codeBlock) implements IExpression {
}
