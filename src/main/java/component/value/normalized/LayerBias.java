package component.value.normalized;

import component.value.normalized.type.Type;

public class LayerBias extends NormalizedValue{
    public static final double MAX_VALUE = 1.0;
    public static final double MIN_VALUE = 0.0;
    public static final double DEFAULT_VALUE = MIN_VALUE;

    public LayerBias() {
        super(MIN_VALUE, MAX_VALUE);
        value = DEFAULT_VALUE;
    }

    @Override
    public Type getType() {
        return Type.BIAS;
    }
}
