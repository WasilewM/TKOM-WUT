package parser;

import parser.program_components.CodeBlock;
import parser.program_components.Parameter;

import java.util.HashMap;

public interface IFunctionDef extends IVisitable {
    String name();

    HashMap<String, Parameter> parameters();

    CodeBlock functionCode();
}
