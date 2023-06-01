package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import parser.IExtendableDataValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;

import java.util.ArrayList;

public class GenericListValue implements IExtendableDataValue {
    protected final ArrayList<Object> values;
    protected final Position position;

    public GenericListValue(Position position, ArrayList<Object> values) {
        this.values = values;
        this.position = position;
    }

    public static ArrayList<String> getImplementedMethods() {
        ArrayList<String> implementedMethods = new ArrayList<>();
        implementedMethods.add("get");
        implementedMethods.add("add");
        return implementedMethods;
    }

    @Override
    public Object value() {
        return values;
    }

    @Override
    public Position position() {
        return position;
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Object get(int idx) {
        return values.get(idx);
    }

    @Override
    public void add(IDataValue val) throws IncompatibleDataTypeException {
        values.add(val);
    }

    @Override
    public int size() {
        return values.size();
    }
}
