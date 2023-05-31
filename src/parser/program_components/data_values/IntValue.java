package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

public record IntValue(Position position, Integer value) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof IntValue castedOther)) {
            return false;
        }

        return (this.value).equals(castedOther.value);
    }
}
