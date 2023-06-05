package visitors;

import parser.IVisitable;

import java.util.HashMap;

public class Context {
    private final HashMap<String, IVisitable> context;
    private final boolean functionContext;

    public Context() {
        this.context = new HashMap<>();
        functionContext = false;
    }

    public Context(boolean functionContext) {
        this.context = new HashMap<>();
        this.functionContext = functionContext;
    }

    public boolean isFunctionContext() {
        return functionContext;
    }

    public void add(String parameterName, IVisitable value) {
        context.put(parameterName, value);
    }

    public boolean containsKey(String parameterName) {
        return context.containsKey(parameterName);
    }

    public IVisitable get(String parameterName) {
        return context.get(parameterName);
    }

    public void update(String parameterName, IVisitable newValue) {
        context.replace(parameterName, newValue);
    }

    @Override
    public boolean equals(Object other) {
        if (!other.getClass().equals(Context.class)) {
            return false;
        }

        if (!this.context.keySet().equals(((Context) other).context.keySet())) {
            return false;
        }

        for (String key : this.context.keySet()) {
            if (!this.context.get(key).equals(((Context) other).context.get(key))) {
                return false;
            }
        }

        return true;
    }
}
