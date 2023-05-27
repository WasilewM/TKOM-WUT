package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import parser.IExpression;
import visitors.IVisitor;

import java.util.ArrayList;

public record FigureValue(Position position, ArrayList<IExpression> values) implements IDataValue {
    public FigureValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Object value() {
        return values;
    }
}
