package component.value.normalized;

import component.value.normalized.type.Type;

public class NeuronThreshold extends NormalizedValue {

    @Override
    public Type getType() {
        return Type.THRESHOLD;
    }
}
