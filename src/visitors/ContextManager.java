package visitors;

import parser.IFunctionDef;
import parser.IVisitable;

import java.util.ArrayList;
import java.util.HashMap;

public class ContextManager {
    private final ArrayList<Context> contexts;
    private final HashMap<String, IFunctionDef> functions;
    private final HashMap<String, IVisitable> parameters;

    private final ArrayList<String> valueReturningMethods;
    private final ArrayList<String> voidMethods;
    private IVisitable currentObject;

    public ContextManager() {
        this.contexts = new ArrayList<>();
        functions = new HashMap<>();
        parameters = new HashMap<>();
        currentObject = null;
        valueReturningMethods = new ArrayList<>();
        voidMethods = new ArrayList<>();
        initValueReturningMethodsLists();
        initVoidMethodsLists();
    }

    private void initValueReturningMethodsLists() {
        valueReturningMethods.add("get");
    }

    private void initVoidMethodsLists() {
        voidMethods.add("add");
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

    public void addFunctions(HashMap<String, IFunctionDef> f) {
        functions.putAll(f);
    }

    public IFunctionDef getFunction(String funcName) {
        return functions.get(funcName);
    }

    public boolean isMethodImplemented(String methodName) {
        return voidMethods.contains(methodName) || valueReturningMethods.contains(methodName);
    }

    public boolean isVoidMethod(String methodName) {
        return voidMethods.contains(methodName);
    }

    public boolean isValueReturningMethod(String methodName) {
        return valueReturningMethods.contains(methodName);
    }

    public void addParameter(String paramName, IVisitable val) {
        parameters.put(paramName, val);
    }

    public HashMap<String, IVisitable> consumeParameters() {
        HashMap<String, IVisitable> params = new HashMap<>(parameters);
        parameters.clear();
        return params;
    }

    public IVisitable getCurrentObject() {
        return currentObject;
    }

    public void setCurrentObject(IVisitable currentObject) {
        this.currentObject = currentObject;
    }

    public void unSetCurrentObject() {
        currentObject = null;
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
