package function;

import type.Either;
import type.Left;
import type.Right;

import java.util.function.Function;

public class Pow implements Computable {
    private final Computable base;
    private final Computable power;

    public Pow(Computable base, Computable power) { this.base = base; this.power = power; }

    @Override
    public Either<String, Double> evaluate() {
        Either<String, Double> l = base.evaluate();

        if (l.isLeft()) {
            return l;
        }

        Either<String, Double> r = power.evaluate();

        Function<Double, Either<String, Double>> f = (Double d) -> {
            double out = Math.pow(((Right<String, Double>) l).get(), d);

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
        return base.reconstruct() + "^" + power.reconstruct();
    }
}
