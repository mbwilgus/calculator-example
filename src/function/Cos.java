package function;

public class Cos implements Computable {
    private final Computable operand;

    public Cos(Computable operand) { this.operand = operand; }

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
