package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.Objects;

public class FigureListValue extends GenericListValue {
    public FigureListValue(Position position) {
        super(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FigureListValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, values);
    }

    @Override
    public String toString() {
        return "FigureListValue[" +
                "position=" + position + ", " +
                "value=" + values + ']';
    }

    @Override
    public void add(IDataValue val) {
        if (val.getClass().equals(FigureValue.class)) {
            super.add(val);
        }
    }
}
