package component;

import component.value.Value;
import exception.ValueNotInRangeException;

public class Connection {
    private final Value<Double> value;

    public Connection(Value<Double> value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(double value){
        try {
            this.value.setValue(value);
        } catch (ValueNotInRangeException e) {
            e.printStackTrace();
        }
    }
}
