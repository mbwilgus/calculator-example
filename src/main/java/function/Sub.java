package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Sub implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Sub(Computable lhs, Computable rhs) { this.lhs = lhs; this.rhs = rhs; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> x = lhs.evaluate();

        if (x.isLeft()) {
            return x;
        }

        Either<String, Double> y = rhs.evaluate();

        Function<Double, Either<String, Double>> f = (Double b) -> {
            Function<Double, Double> sub = (Double a) -> a - b;

            // x is definitely Right
            Right<String, Double> difference = x.fmap(sub).projectRight();

            String error = Computable.CalculationError(difference.get());
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return difference;
        };

        return y.bind(f);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + " - " + rhs.reconstruct();
    }
}
