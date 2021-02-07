package function;

import data.Either;

import java.util.function.Function;

public class Add implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Add(Computable lhs, Computable rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Either<String, Double>> eval = (Double x) -> {
            Function<Double, Double> add = (Double y) ->
                    Computable.roundIf(x + y, 1E-10);

            return rhs.evaluate(formula).fmap(add);
        };

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return lhs.evaluate(formula).bind(eval).bind(err);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + " + " + rhs.reconstruct();
    }
}
