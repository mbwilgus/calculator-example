package function;

public interface Formula {
    class UnimplementedException extends RuntimeException {
        public UnimplementedException(String msg) {
            super(msg);
        }

        public UnimplementedException(String msg, Throwable err) {
            super(msg, err);
        }
    }

    double cos(double x) throws UnimplementedException;
    double exp(double x) throws UnimplementedException;
    double log(double x) throws UnimplementedException;
    double pow(double x, double y) throws UnimplementedException;
    double sin(double x) throws UnimplementedException;
    double tan(double x) throws UnimplementedException;
}
