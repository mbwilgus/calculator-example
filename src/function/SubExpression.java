package function;

public class SubExpression implements Computable{
    private final Computable expr;

    public SubExpression(Computable expr) { this.expr = expr; }

    @Override
    public double evaluate() {
        return expr.evaluate();
    }
}
