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

    @Override
    public Object value() {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof PointValue castedOther)) {
            return false;
        }

        return ((this.x).equals(castedOther.x) && (this.y).equals(castedOther.y));
    }

    @Override
    public String getPrinting() {
        return "Point(" + ((DoubleValue) x).getPrinting() + ", " + ((DoubleValue) y).getPrinting() + ")";
    }

    @Override
    public void print() {
        System.out.print(getPrinting());
    }
}
