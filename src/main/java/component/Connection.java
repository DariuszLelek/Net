package component;

import component.value.NumericValue;
import component.value.Value;
import exception.ValueNotInRangeException;

public class Connection {
    public static final double VALUE_MAX = 1.0d;
    private final Value<Double> value = new NumericValue<>(0.0, VALUE_MAX);

    public double getValue() {
        return value.getNormalized();
    }

    public void setValue(double value){
        try {
            this.value.setValue(value);
        } catch (ValueNotInRangeException e) {
            e.printStackTrace();
        }
    }
}
