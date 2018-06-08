package component.value;

import component.value.normalized.Normalized;
import component.value.normalized.NormalizedValue;
import exception.ValueNotInRangeException;

public class TransputValue implements Normalized {
    private final String name;
    private final double min;
    private final double max;
    private double value = 0.0;

    public TransputValue(TransputValue transputValue) {
        name = transputValue.getName();
        min = transputValue.getMin();
        max = transputValue.getMax();
        value = transputValue.getValue();
    }

    public TransputValue(String name, double min, double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public TransputValue(String name, double min, double max, double value) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setValue(double value) throws ValueNotInRangeException {
        if(value >= min && value <= max){
            this.value = value;
        }else{
            throw new ValueNotInRangeException("NumericValue [" + value + "] not in range [" + min + " - " + max + "]");
        }
    }

    public String getName() {
        return name;
    }

    public void setFromNormalizedValue(NormalizedValue normalizedValue) {
        value = ((max - min) * normalizedValue.getNormalized() + min);
    }

    @Override
    public double getNormalized() {
        return (value - min) / (max - min);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransputValue)) return false;

        TransputValue that = (TransputValue) o;

        return Double.compare(that.getMin(), getMin()) == 0 && Double.compare(that.getMax(), getMax()) == 0
                && Double.compare(that.getValue(), getValue()) == 0
                && (getName() != null ? getName().equals(that.getName()) : that.getName() == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getName() != null ? getName().hashCode() : 0;
        temp = Double.doubleToLongBits(getMin());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getMax());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getValue());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
