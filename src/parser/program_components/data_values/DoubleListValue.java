package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;

public record DoubleListValue(Position position, ArrayList<DoubleValue> value) implements IDataValue {
    public DoubleListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
