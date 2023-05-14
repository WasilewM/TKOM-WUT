package parser.program_components.data_values;

import lexer.Position;
import parser.IExpression;
import parser.IVisitor;

public record SectionValue(Position position, IExpression first, IExpression second) implements IExpression {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
