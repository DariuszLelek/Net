package component.value;


public class Weight extends NumericValue<Double> {
    public static final double MAX_WEIGHT = 1.0d;
    public static final double MIN_WEIGHT = 0.0d;

    public Weight() {
        super(MIN_WEIGHT, MAX_WEIGHT);
    }
}
