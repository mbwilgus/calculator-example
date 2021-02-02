package function;

import type.Either;

public class SubExpression implements Computable{
    private final Computable expr;

    public SubExpression(Computable expr) { this.expr = expr; }

    @Override
    public Either<String, Double> evaluate() {
        return expr.evaluate();
    }

    @Override
    public String reconstruct() {
        return "(" + expr.reconstruct() + ")";
    }
}