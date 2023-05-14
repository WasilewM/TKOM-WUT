package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record PointValue(Position position, IExpression x, IExpression y) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
