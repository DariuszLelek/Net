package component.value.normalized;

public class Bias extends NormalizedValue {
    public static final double MAX_VALUE = 1.0;
    public static final double MIN_VALUE = 0.0;
    public static final double DEFAULT_VALUE = MIN_VALUE;

    private Bias(Bias bias){
        super(bias.getMin(), bias.getMax());
        this.value = bias.value;
    }

    public Bias() {
        super(MIN_VALUE, MAX_VALUE);
        value = DEFAULT_VALUE;
    }

    @Override
    public Bias copy() {
        return new Bias(this);
    }
}
