package interpret;

public class Token {
    public TokenType type;
    public Double value;

    public Token(TokenType type, Double value) {
        this.type = type;
        this.value = value;
    }
}
