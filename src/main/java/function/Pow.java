package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Pow implements Computable {
    private final Computable base;
    private final Computable exp;

    public Pow(Computable base, Computable power) { this.base = base; this.exp = power; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> x = base.evaluate();

        if (x.isLeft()) {
            return x;
        }

        Either<String, Double> y = exp.evaluate();

        Function<Double, Either<String, Double>> f = (Double b) -> {
            Function<Double, Double> pow = (Double a) -> Math.pow(a, b);

            // x is definitely Right
            Right<String, Double> power = x.fmap(pow).projectRight();

            String error = Computable.CalculationError(power.get());
            if (error != null) {
                return new Left<>(error + " @ " + reconstruct());
            }

            return power;
        };

        return y.bind(f);
    }

    @Override
    public String reconstruct() {
        return base.reconstruct() + "^" + exp.reconstruct();
    }
}
