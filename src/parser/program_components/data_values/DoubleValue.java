package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

public record DoubleValue(Position position, Double value) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof DoubleValue castedOther)) {
            return false;
        }

        return (this.value).equals(castedOther.value);
    }

    @Override
    public String getPrinting() {
        return value().toString();
    }

    @Override
    public void print() {
        System.out.println(getPrinting());
    }
}
