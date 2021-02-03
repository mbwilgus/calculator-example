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
    public Either<String, Double> evaluate(Formula formula) {
        Either<String, Double> x = lhs.evaluate(formula);

        if (x.isLeft()) {
            return x;
        }

        Either<String, Double> y = rhs.evaluate(formula);

        Function<Double, Either<String, Double>> f = (Double b) -> {
            Function<Double, Double> mul = (Double a) -> a * b;

            // x is definitely Right
            Right<String, Double> product = x.fmap(mul).projectRight();

            String error = Computable.CalculationError(product.get());
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return product;
        };

        return y.bind(f);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + " * " + rhs.reconstruct();
    }
}
