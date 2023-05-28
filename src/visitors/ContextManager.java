package visitors;

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

    protected void createNewContext() {
        contexts.add(new Context());
    }

    protected void deleteLastContext() {
        contexts.remove(contexts.size() - 1);
    }
}
