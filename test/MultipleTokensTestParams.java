import java.util.List;

public class MultipleTokensTestParams {
    private final String inputString;
    private final List<SingleTokenDescription> tokens;

    public MultipleTokensTestParams(String inputString, List<SingleTokenDescription> tokens) {
        this.inputString = inputString;
        this.tokens = tokens;
    }

    public String getInputString() {
        return inputString;
    }

    public List<SingleTokenDescription> getTokens() {
        return tokens;
    }
}
