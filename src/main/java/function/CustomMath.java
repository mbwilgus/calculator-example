package function;

public class CustomMath implements Formula {
    public static double normalize(double angle) {
        return angle - 2*Math.PI*Math.floor((angle + Math.PI)/(2*Math.PI));
    }

    @Override
    public double cos(double x) {
        int N = 1000;
        x = normalize(x);
        x = x*x;
        double p = 1 - x/((2*N-1)*(2*N));
        while (--N >= 1) {
            p = 1 - x/((2*N-1)*(2*N))*p;
        }

        return p;
    }

    @Override
    public double exp(double x) {
        int N = 1000;
        double p = 1 + x/N;

        while (--N >= 1) {
            p = 1 + x/N*p;
        }

        return p;
    }

    @Override
    public double log(double x) throws UnimplementedException {
        throw new UnimplementedException("log(x) has not been implemented in CustomMath");
    }

    @Override
    public double pow(double x, double y) throws UnimplementedException {
        throw new UnimplementedException("b^e has not been implemented in CustomMath");
    }

    @Override
    public double sin(double x) {
        int N = 999;
        x = CustomMath.normalize(x);
        double y = x * x;
        double p = 1 - y / ((2 * N) * (2 * N + 1));
        while (--N >= 1) {
            p = 1 - y / ((2 * N) * (2 * N + 1)) * p;
        }

        return x * p;
    }
}
