package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import parser.program_components.data_values.SceneValue;
import visitors.IVisitor;

import java.util.ArrayList;
import java.util.Objects;

public class SceneListValue extends GenericListValue {
    public SceneListValue(Position position) {
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
        var that = (SceneListValue) obj;
        return Objects.equals(this.position, that.position) &&
                Objects.equals(this.values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, values);
    }

    @Override
    public String toString() {
        return "SceneListValue[" +
                "position=" + position + ", " +
                "values=" + values + ']';
    }

    @Override
    public void add(IDataValue val) {
        if (val.getClass().equals(SceneValue.class)) {
            super.add(val);
        }
    }
}
