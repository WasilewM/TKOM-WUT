package parser.program_components.expressions;

import parser.IExpression;
import parser.IVisitor;

public record NotEqualExpression(IExpression leftExp, IExpression rightExp) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
