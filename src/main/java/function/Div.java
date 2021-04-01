package function;

import data.either.Either;
import data.either.Left;

import java.util.function.Function;

public class Div implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Div(Computable lhs, Computable rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function <Double, Either<String, Double>> eval = (Double x) -> {
            Function<Double, Either<String, Double>> div = (Double y) -> {
                if (y == 0) {
                    return new Left<>("division by zero @ " + rhs.reconstruct());
                }

                return Either.unit(Computable.roundIf(x / y, 1E-10));
            };

            return rhs.evaluate(formula).bind(div);
        };

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return lhs.evaluate(formula).bind(eval).bind(err);
    }

    @Override
    public String reconstruct() {
        return lhs.reconstruct() + "/" + rhs.reconstruct();
    }
}
