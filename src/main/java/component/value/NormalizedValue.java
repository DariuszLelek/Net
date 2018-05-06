package component.value;

public class NormalizedValue extends NumericValue<Double> {
    public static final double MAX_VALUE = 1.0d;
    public static final double MIN_VALUE = 0.0d;

    public NormalizedValue() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
