package component.value;


public class NumericValue<T extends Number & Comparable<? super T>> extends Value<T> {

    public NumericValue(T min, T max) {
        super(min, max);
    }

    //    TODO: research if can use normalized value in NumericValue class
    @Override
    protected void updateNormalized(T value) {
        normalized = ((value.doubleValue() - min.doubleValue()) / (max.doubleValue() - min.doubleValue()));
    }

}
