package function;

public class CustomMath {
    public static double normalize(double angle) {
        return angle - 2*Math.PI*Math.floor((angle + Math.PI)/(2*Math.PI));
    }
}
