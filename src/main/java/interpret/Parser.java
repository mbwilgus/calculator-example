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
            expression = addition();
        }

        return expression;
    }

    private Computable addition() {
        Computable expression = multiplication();

        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Token operator = previous();
            Computable rhs = multiplication();
            switch (operator.type) {
                case PLUS:
                    expression = new Add(expression, rhs);
                    break;
                case MINUS:
                    expression = new Sub(expression, rhs);
                    break;
            }
        }

        return expression;
    }

    private Computable multiplication() {
        Computable expression = exponentiation();

        while (match(TokenType.STAR, TokenType.SLASH)) {
            Token operator = previous();
            Computable rhs = exponentiation();
            switch (operator.type) {
                case STAR:
                    expression = new Mul(expression, rhs);
                    break;
                case SLASH:
                    expression = new Div(expression, rhs);
                    break;
            }
        }

        return expression;
    }

    private Computable exponentiation() {
        Computable expression = unary();

        while (match(TokenType.CARET)) {
            Computable power = exponentiation(); // exponentiation is right associative
            expression = new Pow(expression, power);
        }

        return expression;
    }

    private Computable unary() {
        if (match(TokenType.MINUS)) {
            Computable negation = new Negate(unary());
            return negation;
        }

        return primary();
    }

    private Computable getArguments(Token operator) {
        List<Computable> arguments = new ArrayList<>();

        if (!check(TokenType.RIGHT_PAREN)) {
            do {
                arguments.add(addition());
            } while (match(TokenType.COMMA));
        }

        /* Token paren = */ consume(TokenType.RIGHT_PAREN);

        switch (operator.lexeme) {
            case "exp":
                return new Exp(arguments.get(0));
            case "cos":
                return new Cos(arguments.get(0));
            case "sin":
                return new Sin(arguments.get(0));
            case "log":
                return new Log(arguments.get(0));
            default:
                throw new ParseError();
        }
    }

    private Computable primary() {
        if (match(TokenType.NUMBER)) {
            Token number = previous();
            return new Literal(number.value, number.lexeme);
        }

        if (match(TokenType.OPERATOR)) {
            Token operator = previous();
            if (match(TokenType.LEFT_PAREN)) {
                return getArguments(operator);
            }
        }

        if (match(TokenType.LEFT_PAREN)) {
            Computable subexpression = addition();
            consume(TokenType.RIGHT_PAREN);
            return new SubExpression(subexpression);
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
