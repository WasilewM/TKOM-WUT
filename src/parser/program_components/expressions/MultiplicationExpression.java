package parser.program_components.expressions;

import lexer.Position;
import parser.IExpression;
import visitor.IVisitor;

public record MultiplicationExpression(Position position, IExpression leftExp,
                                       IExpression rightExp) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
