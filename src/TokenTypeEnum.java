enum TokenTypeEnum {
    // keywords
    INT_KEYWORD,
    DOUBLE_KEYWORD,
    STRING_KEYWORD,
    POINT_KEYWORD,
    SECTION_KEYWORD,
    FIGURE_KEYWORD,
    SCENE_KEYWORD,
    LIST_KEYWORD,
    BOOL_KEYWORD,
    BOOL_TRUE_VALUE_KEYWORD,
    BOOL_FALSE_VALUE_KEYWORD,
    WHILE_KEYWORD,
    IF_KEYWORD,
    ELSE_IF_KEYWORD,
    ELSE_KEYWORD,
    MAIN_KEYWORD,
    RETURN_KEYWORD,
    VOID_KEYWORD,

    // Operator
    ADDITION_OPERATOR,
    SUBTRACTION_OPERATOR,
    MULTIPLICATION_OPERATOR,
    DIVISION_OPERATOR,
    DISCRETE_DIVISION_OPERATOR,
    AND_OPERATOR,
    OR_OPERATOR,
    NEGATION_OPERATOR,
    EQUAL_OPERATOR,
    NOT_EQUAL_OPERATOR,
    LESS_THAN_OPERATOR,
    GREATER_THAN_OPERATOR,
    LESS_OR_EQUAL_OPERATOR,
    GREATER_OR_EQUAL_OPERATOR,
    ASSIGNMENT_OPERATOR,

    // Special signs
    COMMENT,
    LEFT_BRACKET,
    RIGHT_BRACKET,
    LEFT_SQUARE_BRACKET,
    RIGHT_SQUARE_BRACKET,
    LEFT_CURLY_BRACKET,
    RIGHT_CURLY_BRACKET,
    SEMICOLON,
    DOT,
    COMMA,

    // Other language part tokens
    STRING_VALUE,
    INT_VALUE,
    DOUBLE_VALUE,
    IDENTIFIER,

    // Errors
    UNKNOWN_CHAR_ERROR,
    UNCLOSED_QUOTES_ERROR,
    STRING_EXCEEDED_MAXIMUM_LENGTH_ERROR
}
