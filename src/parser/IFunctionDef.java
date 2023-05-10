package parser;

import parser.program_components.CodeBlock;

import java.util.HashMap;

public interface IFunctionDef extends IVisitable {
    String name();

    HashMap<String, IParameter> parameters();

    CodeBlock functionCode();
}
