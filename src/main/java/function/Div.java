package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Div implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Div(Computable lhs, Computable rhs) {
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
            if (b == 0) {
                return new Left<>("division by zero @ " + rhs.reconstruct());
            }

            Function<Double, Double> div = (Double a) -> a / b;

            // x is definitely Right
            Right<String, Double> quotient = x.fmap(div).projectRight();

            String error = Computable.CalculationError(quotient.get());
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return quotient;
        };

        return y.bind(f);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + "/" + rhs.reconstruct();
    }
}
