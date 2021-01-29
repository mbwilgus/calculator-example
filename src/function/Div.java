package function;

public class Div implements Computable {
    private Computable lhs;
    private Computable rhs;

    public Div(Computable lhs, Computable rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public double evaluate() {
        return lhs.evaluate() / rhs.evaluate();
    }
}
