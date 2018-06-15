package component.value.normalized;

import component.value.normalized.type.Type;

public class Weight extends NormalizedValue{
    public Weight(){
        value = MAX_VALUE;
    }

    @Override
    public Type getType() {
        return Type.WEIGHT;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "value=" + value +
                '}';
    }
}
