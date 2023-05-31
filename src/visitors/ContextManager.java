package visitors;

import parser.IFunctionDef;
import parser.IVisitable;
import parser.program_components.data_values.lists.GenericListValue;

import java.util.ArrayList;
import java.util.HashMap;

public class ContextManager {
    private final ArrayList<Context> contexts;
    private final HashMap<String, IFunctionDef> functions;

    private final ArrayList<String> methods;

    public ContextManager() {
        this.contexts = new ArrayList<>();
        functions = new HashMap<>();
        methods = new ArrayList<>();
        initMethodsList();
    }

    private void initMethodsList() {
        methods.addAll(GenericListValue.getImplementedMethods());
    }

    public Context getLastContext() {
        if (contexts.isEmpty()) {
            return null;
        }
        return contexts.get(contexts.size() - 1);
    }

    public ArrayList<Context> getContexts() {
        return contexts;
    }

    public boolean containsKey(String key) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            if (contexts.get(i).containsKey(key)) {
                return true;
            }

            if (contexts.get(i).isFunctionContext()) {
                return false;
            }
        }

        return false;
    }

    public IVisitable get(String parameterName) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            if (contexts.get(i).containsKey(parameterName)) {
                return contexts.get(i).get(parameterName);
            }

            if (contexts.get(i).isFunctionContext()) {
                return null;
            }
        }
        return null;
    }

    public void add(String key, IVisitable value) {
        contexts.get(contexts.size() - 1).add(key, value);
    }

    public void update(String key, IVisitable newValue) {
        for (int i = contexts.size() - 1; i >= 0; i--) {
            if (contexts.get(i).containsKey(key)) {
                contexts.get(i).update(key, newValue);
                break;
            }
        }
    }

    public boolean containsFunction(String functionName) {
        return functions.containsKey(functionName);
    }

    public void addFunction(String functionName, IFunctionDef functionDef) {
        functions.put(functionName, functionDef);
    }

    public IFunctionDef getFunction(String funcName) {
        return functions.get(funcName);
    }

    public boolean isMethodImplemented(String methodName) {
        return methods.contains(methodName);
    }

    protected void createNewContext() {
        contexts.add(new Context());
    }

    protected void createNewFunctionContext() {
        contexts.add(new Context(true));
    }

    protected void deleteLastContext() {
        contexts.remove(contexts.size() - 1);
    }
}
