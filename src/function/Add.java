package function;

public class Add implements Computable {
    private Computable lhs;
    private Computable rhs;

    public Add(Computable lhs, Computable rhs) { this.lhs = lhs; this.rhs = rhs; }

    @Override
    public double evaluate() {
        return lhs.evaluate() + rhs.evaluate();
    }
}
