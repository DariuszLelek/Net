package component.value;


public class Weight extends NumericValue<Double> {
    public static final double WEIGHT_MAX = 1.0d;

    public Weight() {
        super(0.0d, WEIGHT_MAX);
    }
}
