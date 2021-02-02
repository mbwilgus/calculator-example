package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Cos implements Computable {
    private final Computable operand;

    public Cos(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> op = operand.evaluate();

        Function<Double, Either<String, Double>> f = (Double d) -> {
            int N = 1000;
            double x = CustomMath.normalize(d);
            x = x*x;
            double p = 1 - x/((2*N-1)*(2*N));
            while (--N >= 1) {
                p = 1 - x/((2*N-1)*(2*N))*p;
            }

            String error = CalculationError(p);
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return new Right<>(p);
        };

        return op.bind(f);
    }

    @Override
    public String reconstruct() {
        return "cos(" + operand.reconstruct() + ")";
    }
}
