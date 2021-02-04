package function;

import type.Either;

import java.util.function.Function;

public class Cos implements Computable {
    private final Computable operand;

    public Cos(Computable operand) {
        this.operand = operand;
    }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Double> cos = (Double x) ->
                Computable.roundIf(formula.cos(x), 1E-10);

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return operand.evaluate(formula).fmap(cos).bind(err);
    }

    @Override
    public String reconstruct() {
        return "cos(" + operand.reconstruct() + ")";
    }
}
