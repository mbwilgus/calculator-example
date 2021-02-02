package function;

import type.Either;

public interface Computable {
    Either<String, Double> evaluate();
    String reconstruct();

    default String CalculationError(double d) {
        if (Double.isInfinite(d)) {
            return "overflow";
        }

        if (Double.isNaN(d)) {
            return "undefined";
        }

        return null;
    }
}
