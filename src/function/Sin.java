package function;

import java.util.List;

public class Sin implements Operator {
    private Computable operand;

    public Sin() {}

    @Override
    public void bind(List<Computable> operands) {
        this.operand = operands.get(0);
    }

    @Override
    public double evaluate() {
        int N = 999;
        double x = CustomMath.normalize(operand.evaluate());
        double num = x * x;
        double pN = 1 - num / ((2 * N) * (2 * N + 1));
        while (--N >= 1) {
            pN = 1 - num / ((2 * N) * (2 * N + 1)) * pN;
        }

        return x * pN;
    }
}
