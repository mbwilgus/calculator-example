package interpret;

public class Token {
    public TokenType type;
    public String lexeme;

    // All Tokens with a value will represent real numbers
    public Double value;

    public Token(TokenType type, String lexeme, Double value) {
        this.type = type;
        this.lexeme = lexeme;
        this.value = value;
    }
}
