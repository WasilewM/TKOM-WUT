package parser.program_components.function_definitions;

import lexer.Position;
import parser.IFunctionDef;
import parser.IParameter;
import parser.IVisitor;
import parser.program_components.CodeBlock;
import parser.program_components.parameters.FigureListParameter;

import java.util.HashMap;

public record FigureListFunctionDef(Position position, String name, HashMap<String, IParameter> parameters,
                                    CodeBlock functionCode) implements IFunctionDef {

    public FigureListFunctionDef(FigureListParameter functionType, HashMap<String, IParameter> parameters, CodeBlock codeBlock) {
        this(functionType.position(), functionType.name(), parameters, codeBlock);
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
