package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import parser.program_components.data_values.PointValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;

import java.util.ArrayList;
import java.util.Objects;

public class PointListValue extends GenericListValue {

    public PointListValue(Position position) {
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
        var that = (PointListValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, values);
    }

    @Override
    public String toString() {
        return "PointListValue[" +
                "position=" + position + ", " +
                "values=" + values + ']';
    }

    @Override
    public void add(IDataValue val) throws IncompatibleDataTypeException {
        if (val.getClass().equals(PointValue.class)) {
            super.add(val);
        } else {
            throw new IncompatibleDataTypeException(this, val);
        }
    }
}
