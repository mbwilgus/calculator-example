package function;

public class Mul implements Computable {
    private final Computable lhs;
    private final Computable rhs;

    public Mul(Computable lhs, Computable rhs) { this.lhs = lhs; this.rhs = rhs; }

    @Override
    public double evaluate() {
        return lhs.evaluate() * rhs.evaluate();
    }
}
