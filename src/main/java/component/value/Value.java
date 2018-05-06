package component.value;

import exception.ValueNotInRangeException;

public abstract class Value<T extends Comparable<? super T>> implements Cloneable{
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value<?> value1 = (Value<?>) o;

        if (Double.compare(value1.normalizedValue, normalizedValue) != 0) return false;
        if (!min.equals(value1.min)) return false;
        if (!max.equals(value1.max)) return false;
        return value.equals(value1.value);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = min.hashCode();
        result = 31 * result + max.hashCode();
        result = 31 * result + value.hashCode();
        temp = Double.doubleToLongBits(normalizedValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
