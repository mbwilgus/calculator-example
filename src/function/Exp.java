package function;

public class Exp implements Computable {
    private Computable operand;

    public Exp(Computable operand) {
        this.operand = operand;
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
