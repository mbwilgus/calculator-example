package function;

import java.util.List;

public class Negate implements Operator {
    private Computable operand;

    public Negate() {}

    @Override
    public void bind(List<Computable> operands) {
        this.operand = operands.get(0);
    }

    @Override
    public double evaluate() {
        return -operand.evaluate();
    }
}
