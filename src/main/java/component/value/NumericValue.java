package component.value;

import exception.ValueNotInRangeException;

public class NumericValue {
    private final double min;
    private final double max;
    protected double value;

    protected NumericValue(double min, double max) {
        this.min = min;
        this.max = max;

        value = min;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) throws ValueNotInRangeException {
        if(value >= min && value <= max){
            this.value = value;
        }else{
            throw new ValueNotInRangeException("NumericValue [" + value + "] not in range [" + min + " - " + max + "]");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NumericValue that = (NumericValue) o;

        return Double.compare(that.min, min) == 0 && Double.compare(that.max, max) == 0 && Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(min);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(max);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

}
