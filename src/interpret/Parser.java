package interpret;

import function.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static class ParseError extends RuntimeException {}

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Computable parse() {
        if (isFullyConsumed()) {
            throw new ParseError();
        }

        Computable expression = null;
        while (!isFullyConsumed()) {
            expression = unary();
        }

        return expression;
    }

    private Computable unary() {
        if (match(TokenType.MINUS)) {
            List<Computable> arg = new ArrayList<>();
            arg.add(unary());
            Operator negation = new Negate();
            negation.bind(arg);
            return negation;
        }

        return operator();
    }

    private Computable operator() {
        Computable expression = primary();

        while (true) {
            if (match(TokenType.LEFT_PAREN, TokenType.CARET)) {
                expression = getArguments((Operator)expression);
            } else {
                break;
            }
        }

        return expression;
    }

    private Computable getArguments(Operator operator) {
        List<Computable> arguments = new ArrayList<>();
        Token open = previous();
        if (open.type == TokenType.LEFT_PAREN) {
            if (!check(TokenType.RIGHT_PAREN)) {
                do {
                    arguments.add(unary());
                } while (match(TokenType.COMMA));
            }

            Token paren = consume(TokenType.RIGHT_PAREN);
        } else {
            arguments.add(unary());
        }

        operator.bind(arguments);
        return operator;
    }

    private Computable primary() {
        if (match(TokenType.NUMBER)) {
            return new Literal(previous().value);
        }

        if (match(TokenType.OPERATOR)) {
            if (previous().lexeme.equals("e")) {
                return new Exp();
            } else if (previous().lexeme.equals("cos")) {
                return new Cos();
            } else if (previous().lexeme.equals("sin")) {
                return new Sin();
            }
        }

        throw new ParseError();
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    private Token consume(TokenType type) {
        if (check(type)) return advance();

        throw new ParseError();
    }

    private boolean check(TokenType type) {
        if (isFullyConsumed()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isFullyConsumed()) current++;
        return previous();
    }

    private boolean isFullyConsumed() { return peek().type == TokenType.EOL; }

    private Token peek() { return tokens.get(current); }

    private Token previous() { return tokens.get(current - 1); }
}
