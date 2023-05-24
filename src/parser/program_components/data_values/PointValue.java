package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;

public record PointValue(Position position, IExpression x, IExpression y) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
