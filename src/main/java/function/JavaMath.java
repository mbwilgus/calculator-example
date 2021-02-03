package function;

public class JavaMath implements Formula{
    @Override
    public double cos(double x) {
        return Math.cos(x);
    }

    @Override
    public double exp(double x) {
        return Math.exp(x);
    }

    @Override
    public double log(double x) {
        return Math.log(x);
    }

    @Override
    public double pow(double x, double y) {
        return Math.pow(x, y);
    }

    @Override
    public double sin(double x) {
        return Math.sin(x);
    }
}
