package view;

import function.Computable;
import interpret.Parser;
import interpret.Scanner;
import interpret.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

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
        List<Token> tokens;

        try {
            tokens = scanner.tokenize();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        Parser parser = new Parser(tokens);
        Computable expression;

        try {
            expression = parser.parse();
            Double result = expression.evaluate();
            String text = result.toString();
            if (text.equals("-0.0")) text = "0";
            if (text.endsWith(".0")) {
                text = text.substring(0, text.length() - 2);
            }
            System.out.println(text);
        } catch (Parser.ParseError e) {
            System.out.println("Cannot parse expression");
            return;
        }
    }
}
