package function;

import type.Either;

public interface Computable {
    Either<String, Double> evaluate(Formula formula);
    String reconstruct();

    static double closeTo(double x, double e) {
        if (Math.abs(Math.round(x) - x) <= e ) {
            return Math.round(x);
        }

        return x;
    }

    static String CalculationError(double d) {
        if (Double.isInfinite(d)) {
            return "overflow";
        }

        if (Double.isNaN(d)) {
            return "undefined";
        }

        return null;
    }
}
