package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;

public record IntListValue(Position position, ArrayList<Integer> value) implements IDataValue {
    public IntListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
