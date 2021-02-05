package interpret;

import function.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public static class ParseError extends RuntimeException {}

    private final List<Token> tokens;
    private int current = 0;

    private boolean disallowPrimary = false;

    private static final Map<String, OperatorInitializer> operators;

    static {
        operators = new HashMap<>();
        operators.put("cos", new OperatorInitializer.CosInitializer());
        operators.put("exp", new OperatorInitializer.ExpInitializer());
        operators.put("log", new OperatorInitializer.LogInitializer());
        operators.put("sin", new OperatorInitializer.SinInitializer());
        operators.put("tan", new OperatorInitializer.TanInitializer());
    }

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
            disallowPrimary = false;
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
            disallowPrimary = false;
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
            disallowPrimary = false;
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

        if (disallowPrimary) throw new ParseError();

        return primary();    }

    private Computable getArguments(Token operator) {
        List<Computable> arguments = new ArrayList<>();

        if (!check(TokenType.RIGHT_PAREN)) {
            do {
                arguments.add(addition());
            } while (match(TokenType.COMMA));
        }

        /* Token paren = */ consume(TokenType.RIGHT_PAREN);

        OperatorInitializer init = operators.get(operator.lexeme);
        if (init != null && init.arity() == arguments.size()) return init.create(arguments);

        throw new ParseError();
    }

    private Computable primary() {
        if (match(TokenType.NUMBER)) {
            Token number = previous();
            disallowPrimary = true;
            return new Literal(number.value, number.lexeme);
        }

        if (match(TokenType.OPERATOR)) {
            Token operator = previous();
            if (match(TokenType.LEFT_PAREN)) {
                Computable f = getArguments(operator);
                disallowPrimary = true;
                return f;
            }
        }

        if (match(TokenType.LEFT_PAREN)) {
            Computable expression = addition();
            consume(TokenType.RIGHT_PAREN);
            disallowPrimary = true;
            return expression;
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
