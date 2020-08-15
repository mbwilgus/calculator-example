package function;

import java.util.List;

public class Cos implements Operator {
    private Computable operand;

    public Cos() {}

    public void bind(List<Computable> operands) {
        this.operand = operands.get(0);
    }

    @Override
    public double evaluate() {
        int N = 1000;
        double x = CustomMath.normalize(operand.evaluate());
        double num = x*x;
        double pN = 1 - num/((2*N-1)*(2*N));
        while (--N >= 1) {
            pN = 1 - num/((2*N-1)*(2*N))*pN;
        }

        return pN;
    }


}
