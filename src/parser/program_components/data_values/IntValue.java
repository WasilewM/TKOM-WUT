package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

public record IntValue(Position position, Integer value) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
