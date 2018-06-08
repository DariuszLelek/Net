package component.value.normalized;

import component.TraceableChange;
import component.value.NumericValue;
import exception.ValueNotInRangeException;

import java.util.UUID;

public class NormalizedValue extends NumericValue implements Normalized, TraceableChange {
    private final String id = UUID.randomUUID().toString();

    public static final double MAX_VALUE = 1.0;
    public static final double MIN_VALUE = 0.0;
    public static final double FLOAT_DELTA = 0.01;

    public NormalizedValue(double min, double max) {
        super(min, max);
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
    public String getId() {
        return id;
    }

    @Override
    public void setOriginal(double originalNormalizedValue) {
        setFromNormalized(originalNormalizedValue);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
