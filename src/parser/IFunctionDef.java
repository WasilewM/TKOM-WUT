package parser;

import parser.program_components.CodeBlock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IFunctionDef extends IVisitable {
    String name();

    HashMap<String, IParameter> parameters();

    default Boolean areParametersTypesValid(List<IParameter> params) {
        if (params.size() != this.parameters().size()) {
            return false;
        }

        int idx = 0;
        for (Map.Entry<String, IParameter> p : this.parameters().entrySet()) {
            if (!p.getValue().getClass().equals(params.get(idx).getClass())) {
                return false;
            }

            idx += 1;
        }

        return true;
    }

    CodeBlock functionCode();
}
