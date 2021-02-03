package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Add implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Add(Computable lhs, Computable rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> x = lhs.evaluate();

        if (x.isLeft()) {
            return x;
        }

        Either<String, Double> y = rhs.evaluate();

        Function<Double, Either<String, Double>> f = (Double b) -> {
            Function<Double, Double> add = (Double a) -> a + b;

            // x is definitely Right
            Right<String, Double> sum = x.fmap(add).projectRight();

            String error = Computable.CalculationError(sum.get());
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return sum;
        };

        return y.bind(f);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + " + " + rhs.reconstruct();
    }
}
