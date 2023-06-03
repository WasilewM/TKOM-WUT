package parser.program_components.data_values.lists;

import lexer.Position;
import parser.IDataValue;
import parser.IExtendableDataValue;
import visitors.IVisitor;
import visitors.exceptions.IncompatibleDataTypeException;

import java.util.ArrayList;

public class GenericListValue implements IExtendableDataValue {
    protected final ArrayList<IDataValue> values;
    protected final Position position;

    public GenericListValue(Position position, ArrayList<IDataValue> values) {
        this.values = values;
        this.position = position;
    }

    @Override
    public ArrayList<IDataValue> value() {
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
    public IDataValue get(int idx) {
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
