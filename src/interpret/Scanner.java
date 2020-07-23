package interpret;

import java.util.Stack;

public class Scanner {
    private Stack<Token> tokens = new Stack<>();
    private String input;
    private int start = 0;
    private int current = 0;
    private int depth = 0;

    private boolean negative = false;

    public Scanner(String input) {
        this.input = input;
    }

    public Stack<Token> scan() {
        while (!isFullyConsumed()) {
            start = current;
            tokenize();
        }

        if (depth != 0) {
            throw new RuntimeException("Mismatched parentheses");
        }

        return tokens;
    }

    private void tokenize() {
        char consuming = advance();

        if (isDigit(consuming)) {
            number();
        } else if (consuming == '-') {
            if (isDigit(peek(1))) {
                negative = true;
            } else {
                throw new RuntimeException("Malformed expression");
            }
        } else if (isValidAlpha(consuming)) {
            switch (consuming) {
                case 'c':
                    consume("os");
                    addToken(TokenType.COS, null);
                    consume("(");
                    depth++;
                    break;
                case 'e':
                    consume("^");
                    addToken(TokenType.EXP, null);
                    break;
            }
        } else {
            if (consuming != ')') {
                throw new RuntimeException("Malformed expression");
            }
            depth--;
        }
    }

    private void addToken(TokenType type, Double value) {
        Token token = new Token(type, value);
        tokens.push(token);
    }

    private char advance() {
        current++;
        return input.charAt(current - 1);
    }

    private void consume(String expect) {
        int index = 0;
        while (index != expect.length()) {
            if (!isFullyConsumed() && input.charAt(current) == expect.charAt(index++)) {
                advance();
            } else {
                throw new RuntimeException("Malformed expression");
            }
        }
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

        addToken(TokenType.LITERAL, Double.parseDouble(literal));
    }

    private boolean isValidAlpha(char c) {
        return c == 'c' || c == 'e';
    }
}
