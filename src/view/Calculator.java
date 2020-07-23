package view;

import function.Computable;
import function.Cos;
import function.Exp;
import function.Literal;
import interpret.Scanner;
import interpret.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Calculator {
    public static void main(String[] args) throws IOException {
        prompt();
    }

    private static void prompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
    }

    private static void run(String input) {
        Scanner scanner = new Scanner(input);
        Stack<Token> tokens;

        try {
            tokens = scanner.scan();
        } catch(RuntimeException e) {
            System.err.println(e.getMessage());
            return;
        }

        double result;
        if (tokens.size() == 0) {
            return;
        } else if (tokens.size() == 1) {
            result = new Literal(tokens.pop().value).evaluate();
        } else {

            Token operand = tokens.pop();
            Token operator = tokens.pop();

            Computable expr = buildExpr(operator,
                    new Literal(operand.value));

            while (!tokens.isEmpty()) {
                operator = tokens.pop();
                expr = buildExpr(operator, expr);
            }

            result = expr.evaluate();
        }

        System.out.println(result);
    }

    private static Computable buildExpr(Token operator, Computable expr) {
        switch(operator.type) {
            case COS:
                expr = new Cos(expr);
                break;
            case EXP:
                expr = new Exp(expr);
                break;
        }

        return expr;
    }
}
