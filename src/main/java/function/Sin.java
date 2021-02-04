package function;

import type.Either;

import java.util.function.Function;

public class Sin implements Computable {
    private final Computable operand;

    public Sin(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Double> sin = (Double x) ->
                Computable.roundIf(formula.sin(x), 1E-10);

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return operand.evaluate(formula).fmap(sin).bind(err);
    }

    @Override
    public String reconstruct() {
        return "sin(" + operand.reconstruct() + ")";
    }
}
