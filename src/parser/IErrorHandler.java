package parser;

import java.util.ArrayList;

public interface IErrorHandler {
    void handle(Exception e);

    ArrayList<Exception> getErrorLog();
}
