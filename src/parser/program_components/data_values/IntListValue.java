package parser.program_components.data_values;

import lexer.Position;
import parser.IDataValue;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.Objects;

public final class IntListValue extends GenericListValue {
    public IntListValue(Position position, ArrayList<Object> value) {
        super(position, value);
    }

    public IntListValue(Position position) {
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
        var that = (IntListValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, value);
    }

    @Override
    public String toString() {
        return "IntListValue[" +
                "position=" + position + ", " +
                "value=" + value + ']';
    }

    @Override
    public void add(IDataValue val) {
        if (val.getClass().equals(IntValue.class)) {
            super.add(val);
        }
    }
}
