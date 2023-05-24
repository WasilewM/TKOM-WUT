package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;

public record PointListValue(Position position, ArrayList<PointValue> value) implements IDataValue {
    public PointListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
