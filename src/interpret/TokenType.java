package interpret;

public enum TokenType {
    // Single-character tokens
    LEFT_PAREN, RIGHT_PAREN, CARET, MINUS, COMMA,

    // Literals
    OPERATOR,
    NUMBER,

    EOL
}
