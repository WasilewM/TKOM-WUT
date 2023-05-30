package parser.program_components.data_values;

import lexer.Position;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.Objects;

public final class DoubleListValue extends GenericListValue {
    private final Position position = null;
    private final ArrayList<DoubleValue> value = null;

    public DoubleListValue(Position position, ArrayList<Object> value) {
        super(position, value);
    }

    public DoubleListValue(Position position) {
        this(position, new ArrayList<>());
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public ArrayList<DoubleValue> value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DoubleListValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, value);
    }

    @Override
    public String toString() {
        return "DoubleListValue[" +
                "position=" + position + ", " +
                "value=" + value + ']';
    }

}
