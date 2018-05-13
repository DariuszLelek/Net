package component.value.normalized;

public class Bias extends NormalizedValue {
    // TODO research bias max value
    public static final double MAX_VALUE = 0.5;
    public static final double MIN_VALUE = 0.0;

    public Bias() {
        super(MIN_VALUE, MAX_VALUE);
    }
}
