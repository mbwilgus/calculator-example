package function;

public class Cos implements Computable {
    private Computable operand;

    public Cos(Computable operand) {
        this.operand = operand;
    }

    @Override
    public double evaluate() {
        int N = 1000;
        double x = normalize(operand.evaluate());
        double num = x*x;
        double pN = 1 - num/((2*N-1)*(2*N));
        while (--N >= 1) {
            pN = 1 - num/((2*N-1)*(2*N))*pN;
        }

        return pN;
    }

    private double normalize(double angle) {
        return angle - 2*Math.PI*Math.floor((angle + Math.PI)/(2*Math.PI));
    }
}
