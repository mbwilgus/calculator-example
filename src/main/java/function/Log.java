package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Log implements Computable {
    private final Computable operand;

    public Log(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Either<String, Double> op = operand.evaluate(formula);

        Function<Double, Either<String, Double>> f = (Double x) -> {
            double p = Computable.closeTo(formula.log(x), 1E-10);

            String error = Computable.CalculationError(p);
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return new Right<>(p);
        };

        return op.bind(f);
    }

    @Override
    public String reconstruct() {
        return "log(" + operand.reconstruct() + ")";
    }
}
