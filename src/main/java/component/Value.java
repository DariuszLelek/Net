package component;

import exception.ValueNotInRangeException;

public abstract class Value<T extends Comparable<? super T>> {
    final T min;
    final T max;
    private T value;
    double normalizedValue;

    Value(T min, T max) {
        this.min = min;
        this.max = max;

        value = min;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public T get() {
        return value;
    }

    public void setValue(T value) throws ValueNotInRangeException {
        if(isInRange(value)){
            this.value = value;
            updateNormalizedValue(value);
        }else{
            throw new ValueNotInRangeException("Value [" + value + "] not in range [" + min + "-" + max + "]");
        }
    }

    private boolean isInRange(T value){
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }

    protected abstract void updateNormalizedValue(T value);

    public double getNormalized(){
        return normalizedValue;
    }
}
