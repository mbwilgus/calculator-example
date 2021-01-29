package interpret;

public enum TokenType {
    // Single-character tokens
    LEFT_PAREN, RIGHT_PAREN, CARET, MINUS, PLUS,
    STAR, SLASH, COMMA,

    // Literals
    OPERATOR,
    NUMBER,

    EOL
}
