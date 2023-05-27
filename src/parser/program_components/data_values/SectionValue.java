package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;

public record SectionValue(Position position, IExpression first, IExpression second) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Object value() {
        return null;
    }
}
