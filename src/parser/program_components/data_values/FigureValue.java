package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import visitor.IVisitor;

public record FigureValue(Position position) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
