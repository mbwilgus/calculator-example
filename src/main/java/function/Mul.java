package function;

import type.Either;

import java.util.function.Function;

public class Mul implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Mul(Computable lhs, Computable rhs) { this.lhs = lhs; this.rhs = rhs; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Either<String, Double>> eval = (Double x) -> {
            Function<Double, Double> mul = (Double y) ->
                    Computable.roundIf(x * y, 1E-10);

            return rhs.evaluate(formula).fmap(mul);
        };

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return lhs.evaluate(formula).bind(eval).bind(err);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + " * " + rhs.reconstruct();
    }
}
