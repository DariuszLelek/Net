package component;

import component.value.NormalizedValue;
import exception.ValueNotInRangeException;

public class Connection {
    private final NormalizedValue normalizedValue = new NormalizedValue();

    public NormalizedValue getNormalizedValue() {
        return normalizedValue;
    }

    public void setNormalized(double normalized){
        try {
            this.normalizedValue.set(normalized);
        } catch (ValueNotInRangeException e) {
            e.printStackTrace();
        }
    }
}
