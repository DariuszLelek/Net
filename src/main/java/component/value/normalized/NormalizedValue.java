package component.value.normalized;

import component.value.NumericValue;
import exception.ValueNotInRangeException;

public class NormalizedValue extends NumericValue implements Normalized {
    public static final double MAX_VALUE = 1.0;
    public static final double MIN_VALUE = 0.0;
    public static final double FLOAT_DELTA = 0.01;

    private NormalizedValue(NormalizedValue normalizedValue){
        super(normalizedValue.getMin(), normalizedValue.getMax());
        this.value = normalizedValue.value;
    }

    public NormalizedValue(double min, double max) {
        super(min, max);
    }

    public NormalizedValue copy(){
        return new NormalizedValue(this);
    }

    public NormalizedValue() {
        super(MIN_VALUE, MAX_VALUE);
    }

    @Override
    public double getNormalized() {
        return getValue();
    }

    public void setFromNormalized(double normalizedValue) {
        try {
            setValue(normalizedValue);
        } catch (ValueNotInRangeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
