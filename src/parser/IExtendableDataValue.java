package parser;

import parser.program_components.data_values.IntValue;
import visitors.exceptions.IncompatibleDataTypeException;
import visitors.exceptions.IncompatibleMethodArgumentException;

public interface IExtendableDataValue extends IDataValue {
    void add(IDataValue value) throws IncompatibleMethodArgumentException, IncompatibleDataTypeException;

    default IDataValue get(IDataValue idx) {
        return this.get((IntValue) idx);
    }

    IDataValue get(IntValue idx);

    int size();
}
