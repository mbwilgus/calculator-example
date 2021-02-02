package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Log implements Computable {
    private final Computable operand;

    public Log(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> op = operand.evaluate();

        Function<Double, Either<String, Double>> f = (Double x) -> {
            double out = Math.log(x);

            String error = CalculationError(out);
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return new Right<>(out);
        };

        return op.bind(f);
    }

    @Override
    public String reconstruct() {
        return "log(" + operand.reconstruct() + ")";
    }
}