package parser.exceptions;

public class MissingDataTypeDeclarationException extends Exception {
    public MissingDataTypeDeclarationException(String message) {
        super("MissingDataTypeDeclarationException: " + message);
    }
}
