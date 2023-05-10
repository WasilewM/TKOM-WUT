package parser.program_components.expressions;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record GreaterOrEqualExpression(Position position, IExpression leftExp,
                                       IExpression rightExp) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
