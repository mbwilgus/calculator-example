package function;

import java.util.List;

public class Exp implements Operator {
    private Computable operand;

    public Exp() {}

    public void bind(List<Computable> operands) {
        this.operand = operands.get(0);
    }

    @Override
    public double evaluate() {
        int N = 1000;
        double x = operand.evaluate();
        double pN = 1 + x/N;

        while (--N >= 1) {
            pN = 1 + x/N*pN;
        }
        return pN;
    }
}
