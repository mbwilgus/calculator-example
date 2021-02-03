package function;

import type.Either;
import type.Left;
import type.Right;

public class Literal implements Computable {
    private final double value;
    private final String lexeme;

    public Literal(double value, String lexeme) {
        this.value = value;
        this.lexeme = lexeme;
    }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        String error = Computable.CalculationError(value);

        if (error != null) {
            return new Left<>(error + "@" + reconstruct());
        }

        return new Right<>(value);
    }

    @Override
    public String reconstruct() {
        return lexeme;
    }
}
