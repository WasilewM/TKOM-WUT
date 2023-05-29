package visitors;

import parser.IVisitable;

import java.util.ArrayList;

public class ContextManager {
    private final ArrayList<Context> contexts;

    public ContextManager() {
        this.contexts = new ArrayList<>();
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
