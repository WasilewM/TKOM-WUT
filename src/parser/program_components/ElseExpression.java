package parser.program_components;

import parser.IExpression;

public record ElseExpression(IExpression alternativeExp, CodeBlock codeBlock) implements IExpression {
    public ElseExpression() {
        this(null, null);
    }
}
