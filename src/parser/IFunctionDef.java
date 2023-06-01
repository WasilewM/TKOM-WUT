package parser;

import parser.program_components.CodeBlock;
import parser.program_components.data_values.*;
import parser.program_components.data_values.lists.*;
import parser.program_components.parameters.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IFunctionDef extends IVisitable {
    String name();

    HashMap<String, IParameter> parameters();

    default Boolean areArgumentsTypesValid(List<IExpression> args) {
        if (args.size() != this.parameters().size()) {
            return false;
        }


        int idx = 0;
        for (Map.Entry<String, IParameter> p : this.parameters().entrySet()) {
            if ((p.getValue().getClass().equals(BoolListParameter.class) && !args.get(idx).getClass().equals(BoolListValue.class))
                    || (p.getValue().getClass().equals(BoolParameter.class) && !args.get(idx).getClass().equals(BoolValue.class))
                    || (p.getValue().getClass().equals(DoubleListParameter.class) && !args.get(idx).getClass().equals(DoubleListValue.class))
                    || (p.getValue().getClass().equals(DoubleParameter.class) && !args.get(idx).getClass().equals(DoubleValue.class))
                    || (p.getValue().getClass().equals(FigureListParameter.class) && !args.get(idx).getClass().equals(FigureValue.class))
                    || (p.getValue().getClass().equals(FigureParameter.class) && !args.get(idx).getClass().equals(FigureValue.class))
                    || (p.getValue().getClass().equals(IntListParameter.class) && !args.get(idx).getClass().equals(IntListValue.class))
                    || (p.getValue().getClass().equals(IntParameter.class) && !args.get(idx).getClass().equals(IntValue.class))
                    || (p.getValue().getClass().equals(PointListParameter.class) && !args.get(idx).getClass().equals(PointListValue.class))
                    || (p.getValue().getClass().equals(PointParameter.class) && !args.get(idx).getClass().equals(PointValue.class))
                    || (p.getValue().getClass().equals(SceneListParameter.class) && !args.get(idx).getClass().equals(SceneListValue.class))
                    || (p.getValue().getClass().equals(SceneParameter.class) && !args.get(idx).getClass().equals(SceneValue.class))
                    || (p.getValue().getClass().equals(SectionListParameter.class) && !args.get(idx).getClass().equals(SectionListValue.class))
                    || (p.getValue().getClass().equals(SectionParameter.class) && !args.get(idx).getClass().equals(SectionValue.class))
                    || (p.getValue().getClass().equals(StringListParameter.class) && !args.get(idx).getClass().equals(StringListValue.class))
                    || (p.getValue().getClass().equals(StringParameter.class) && !args.get(idx).getClass().equals(StringValue.class))) {
                return false;
            }

            idx += 1;
        }

        return true;
    }

    CodeBlock functionCode();
}
