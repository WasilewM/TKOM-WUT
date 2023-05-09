package parser.program_components.expressions;

import parser.IExpression;
import parser.IVisitor;

public record ParenthesesExpression(IExpression alternativeExp) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
