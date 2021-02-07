package function;

import data.Either;

import java.util.function.Function;

public class Negate implements Computable {
    private final Computable operand;

    public Negate(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Double> negate = (Double x) ->
                Computable.roundIf(-x, 1E-10);

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return operand.evaluate(formula).fmap(negate).bind(err);
    }

    @Override
    public String reconstruct() {
        return "-" + operand.reconstruct();
    }
}
