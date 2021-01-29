package function;

public class Sub implements Computable {
    private Computable lhs;
    private Computable rhs;

    public Sub(Computable lhs, Computable rhs) { this.lhs = lhs; this.rhs = rhs; }

    @Override
    public double evaluate() {
        return lhs.evaluate() - rhs.evaluate();
    }
}
