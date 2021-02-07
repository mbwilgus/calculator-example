package function;

import data.Either;

import java.util.function.Function;

public class Exp implements Computable {
    private final Computable operand;

    public Exp(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Double> exp = (Double x) ->
                Computable.roundIf(formula.exp(x), 1E-10);

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return operand.evaluate(formula).fmap(exp).bind(err);
    }

    @Override
    public String reconstruct() {
        return "exp(" + operand.reconstruct() + ")";
    }
}
