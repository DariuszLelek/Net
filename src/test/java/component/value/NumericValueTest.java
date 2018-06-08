package component.value;

import component.value.normalized.NormalizedValue;
import exception.ValueNotInRangeException;
import org.junit.Assert;
import org.junit.Test;

public class NumericValueTest {
    private final double min = 0.0;
    private final double max = 13.0;
    private final NumericValue numericValue = new NumericValue(min, max);

    @Test(expected = ValueNotInRangeException.class)
    public void setValue_exception() throws ValueNotInRangeException {
        double notInRange = 20.0;
        numericValue.setValue(notInRange);
    }

    @Test
    public void getValue() throws ValueNotInRangeException {
        Assert.assertEquals(min, numericValue.getValue(), NormalizedValue.FLOAT_DELTA);
        double inRange = 5.0;
        numericValue.setValue(inRange);
        Assert.assertEquals(inRange, numericValue.getValue(), NormalizedValue.FLOAT_DELTA);
    }
}