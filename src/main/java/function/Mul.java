package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Mul implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Mul(Computable lhs, Computable rhs) { this.lhs = lhs; this.rhs = rhs; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> l = lhs.evaluate();

        if (l.isLeft()) {
            return l;
        }

        Either<String, Double> r = rhs.evaluate();

        Function<Double, Either<String, Double>> f = (Double d) -> {
            double out = ((Right<String, Double>) l).get() * d;

            String error = CalculationError(out);
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return new Right<>(out);
        };

        return r.bind(f);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + " * " + rhs.reconstruct();
    }
}
