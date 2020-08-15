package interpret;

import java.util.ArrayList;
import java.util.List;

public class Scanner {
    private final List<Token> tokens = new ArrayList<>();
    private final String input;
    private int start = 0;
    private int current = 0;

    private boolean negative = false;

    public Scanner(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        while (!isFullyConsumed()) {
            start = current;
            scan();
        }

        tokens.add(new Token(TokenType.EOL, "", null));
        return tokens;
    }

    private void scan() {
        char consuming = advance();

        switch(consuming) {
            case '(': addToken(TokenType.LEFT_PAREN, null); break;
            case ')': addToken(TokenType.RIGHT_PAREN, null); break;
            case '^': addToken(TokenType.CARET, null); break;
            case '-': addToken(TokenType.MINUS, null); break;
            case ',': addToken(TokenType.COMMA, null); break;
            case ' ':
            case '\r':
            case '\t':
                break;
            default:
                if (consuming == '.' && isDigit(peek(1)) || isDigit(consuming)) {
                    number();
                } else if (isAlpha(consuming)) {
                    operator();
                } else {
                    throw new RuntimeException("Malformed expression.");
                }
        }
    }

    private void addToken(TokenType type, Double value) {
        String lexeme = input.substring(start, current);
        Token token = new Token(type, lexeme, value);
        tokens.add(token);
    }

    private char advance() {
        current++;
        return input.charAt(current - 1);
    }

    private char peek(int lookahead) {
        if (isFullyConsumed()) return '\0';
        return input.charAt(current + lookahead - 1);
    }

    private boolean isFullyConsumed() {
        return current >= input.length();
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private void number() {
        while (isDigit(peek(1))) advance();

        if (peek(1) == '.' && isDigit(peek(2))) {
            advance();

            while (isDigit(peek(1))) advance();
        }

        String literal = (negative ? "-" : "") + input.substring(start, current);
        negative = false;

        addToken(TokenType.NUMBER, Double.parseDouble(literal));
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
               (c >= 'A' && c <= 'Z');
    }

    private void operator() {
        while (isAlpha(peek(1))) advance();

        String lexeme = input.substring(start, current);
        boolean notOperator = peek(1) != '^';
        if (notOperator) {
            if (lexeme.equals("pi")) {
                addToken(TokenType.NUMBER, Math.PI);
                return;
            } else if (lexeme.equals("e")) {
                addToken(TokenType.NUMBER, Math.E);
                return;
            }
        }

        addToken(TokenType.OPERATOR, null);
    }
}
