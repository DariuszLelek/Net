package component.value;

public class NormalizedValue extends NumericValue<Double> {
    private static final double MAX_VALUE = 1.0d;
    private static final double MIN_VALUE = 0.0d;

    public NormalizedValue() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
