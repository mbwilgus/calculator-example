package function;

public class Negate implements Computable {
    private final Computable operand;

    public Negate(Computable operand) { this.operand = operand; }

    @Override
    public double evaluate() {
        return -operand.evaluate();
    }
}
