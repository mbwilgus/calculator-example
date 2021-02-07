package function;

import data.Either;
import data.Left;
import data.Right;

public interface Computable {
    Either<String, Double> evaluate(Formula formula);
    String reconstruct();

    static double roundIf(double x, double e) {
        if (Math.abs(Math.round(x) - x) <= e ) {
            return Math.round(x);
        }

        return x;
    }

    static Either<String, Double> checkError(double d, Computable expression) {
        if (Double.isInfinite(d)) {
            return new Left<>( "overflow @ " + expression.reconstruct());
        }

        if (Double.isNaN(d)) {
            return  new Left<>("undefined @ " + expression.reconstruct());
        }

        return new Right<>(d);
    }
}
