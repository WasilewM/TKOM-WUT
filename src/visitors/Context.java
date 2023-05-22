package visitors;

import parser.IExpression;

import java.util.HashMap;

public class Context {
    private final HashMap<String, IExpression> context;

    public Context() {
        this.context = new HashMap<>();
    }

    public void add(String parameterName, IExpression value) {
        context.put(parameterName, value);
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
