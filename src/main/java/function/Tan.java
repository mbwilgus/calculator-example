package function;

import data.either.Either;

import java.util.function.Function;

public class Tan implements Computable{
    private final Computable operand;

    public Tan(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Double> tan = (Double x) ->
                Computable.roundIf(formula.tan(x), 1E-10);

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return operand.evaluate(formula).fmap(tan).bind(err);
    }

    @Override
    public String reconstruct() {
        return "tan(" + operand.reconstruct() + ")";
    }
}
