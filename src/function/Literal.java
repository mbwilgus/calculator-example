package function;

public class Literal implements Computable {
    private double value;

    public Literal(double value) {
        this.value = value;
    }

    @Override
    public double evaluate() {
        return value;
    }
}
