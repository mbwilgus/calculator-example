package function;

import data.Either;

import java.util.function.Function;

public class Log implements Computable {
    private final Computable operand;

    public Log(Computable operand) { this.operand = operand; }

    @Override
    public Either<String, Double> evaluate(Formula formula) {
        Function<Double, Double> log = (Double x) ->
                Computable.roundIf(formula.log(x), 1E-10);

        Function<Double, Either<String, Double>> err = (Double x) ->
                Computable.checkError(x, this);

        return operand.evaluate(formula).fmap(log).bind(err);
    }

    @Override
    public String reconstruct() {
        return "log(" + operand.reconstruct() + ")";
    }
}
