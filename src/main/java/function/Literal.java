package function;

import type.Either;

public class Literal implements Computable {
    private final double value;
    private final String lexeme;

    public Literal(double value, String lexeme) {
        this.value = value;
        this.lexeme = lexeme;
    }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        return Computable.checkError(value, this);
    }

    @Override
    public String reconstruct() {
        return lexeme;
    }
}
