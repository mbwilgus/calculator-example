package function;

import type.Either;

import java.util.function.Function;

public class Pow implements Computable {
    private final Computable base;
    private final Computable exp;

    public Pow(Computable base, Computable power) { this.base = base; this.exp = power; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Either<String, Double>> eval = (Double x) -> {
            Function<Double, Double> pow = (Double y) ->
                    Computable.roundIf(formula.pow(x, y), 1E-10);

            return exp.evaluate(formula).fmap(pow);
        };

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return base.evaluate(formula).bind(eval).bind(err);
    }

    @Override
    public String reconstruct() {
        return base.reconstruct() + "^" + exp.reconstruct();
    }
}
