package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Sin implements Computable {
    private final Computable operand;

    public Sin(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> op = operand.evaluate();

        Function<Double, Either<String, Double>> f = (Double d) -> {
            int N = 999;
            double ang = CustomMath.normalize(d);
            double x = ang * ang;
            double p = 1 - x / ((2 * N) * (2 * N + 1));
            while (--N >= 1) {
                p = 1 - x / ((2 * N) * (2 * N + 1)) * p;
            }

            p = ang * p;

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
        return "sin(" + operand.reconstruct() + ")";
    }
}
