package component.value;

import component.value.normalized.Normalized;
import component.value.normalized.NormalizedValue;

public class TransputValue implements Normalized {
    private final String name;
    private final double min;
    private final double max;
    private double value = 0.0;

    public TransputValue(String name, double min, double max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
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
        if (o == null || getClass() != o.getClass()) return false;

        TransputValue that = (TransputValue) o;

        if (Double.compare(that.min, min) != 0) return false;
        if (Double.compare(that.max, max) != 0) return false;
        if (Double.compare(that.value, value) != 0) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(min);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
