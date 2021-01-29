package function;

import java.util.List;

public class Negate implements Computable {
    private Computable operand;

    public Negate(Computable operand) { this.operand = operand; }

    @Override
    public double evaluate() {
        return -operand.evaluate();
    }
}
