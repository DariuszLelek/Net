package component.value.normalized;

public class Bias extends NormalizedValue {
    // TODO research bias max value
    public static final double MAX_VALUE = 0.5;
    public static final double MIN_VALUE = 0.0;

    private Bias(Bias bias){
        super(bias.getMin(), bias.getMax());
        this.value = bias.value;
    }

    public Bias() {
        super(MIN_VALUE, MAX_VALUE);
        value = MAX_VALUE;
    }

    @Override
    public Bias copy() {
        return new Bias(this);
    }
}
