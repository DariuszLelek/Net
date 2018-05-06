package component.value;


public class BooleanValue<T extends Boolean> extends Value<T> {

    public BooleanValue(T min, T max) {
        super(min, max);
    }

    @Override
    protected void updateNormalizedValue(T value) {
        normalizedValue = Boolean.TRUE.equals(value) ? 1.0f : 0.0f;
    }

}
