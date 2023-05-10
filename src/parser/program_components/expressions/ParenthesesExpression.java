package parser.program_components.expressions;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record ParenthesesExpression(Position position, IExpression exp) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {

    }
}
