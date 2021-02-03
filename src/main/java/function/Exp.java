package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Exp implements Computable {
    private final Computable operand;

    public Exp(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> op = operand.evaluate();

        Function<Double, Either<String, Double>> f = (Double x) -> {
            int N = 1000;
            double p = 1 + x/N;

            while (--N >= 1) {
                p = 1 + x/N*p;
            }

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
        return "exp(" + operand.reconstruct() + ")";
    }
}
