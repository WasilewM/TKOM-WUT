public interface ILexer {
    void setIdentifierMaxLength(int identifierMaxLength);
    void setMaxInt (int maxInt);
    void setMaxDouble(double maxDouble);
    Token lexToken();
}
