package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

public record BoolValue(Position position, Boolean value) implements IDataValue {
    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
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
