package component;

import exception.ValueNotInRangeException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValueTest {
    private final Integer inRangeIntegerValue1 = 5;
    private final Integer inRangeIntegerValue2 = 8;
    private final Integer notInRangeIntegerValue = 20;
    private final Integer minIntegerValue = 0;
    private final Integer maxIntegerValue = 13;
    private final float floatDelta = 0.01f;

    private final Value<Boolean> booleanValue = new BooleanValue<>(Boolean.FALSE, Boolean.TRUE);
    private final Value<Integer> integerValue = new NumericValue<>(minIntegerValue, maxIntegerValue);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test(expected = ValueNotInRangeException.class)
    public void setValue_exception() throws ValueNotInRangeException {
        integerValue.setValue(notInRangeIntegerValue);
    }

    @Test
    public void getValue() throws ValueNotInRangeException {
        Assert.assertEquals(Boolean.FALSE, booleanValue.get());

        booleanValue.setValue(Boolean.TRUE);
        Assert.assertEquals(Boolean.TRUE, booleanValue.get());

        Assert.assertEquals(minIntegerValue, integerValue.get());
        integerValue.setValue(inRangeIntegerValue1);
        Assert.assertEquals(inRangeIntegerValue1, integerValue.get());
    }

    @Test
    public void getNormalizedValue() throws ValueNotInRangeException {
        booleanValue.setValue(Boolean.TRUE);
        Assert.assertEquals(1.0f, booleanValue.getNormalized(), floatDelta);
        booleanValue.setValue(Boolean.FALSE);
        Assert.assertEquals(0.0f, booleanValue.getNormalized(), floatDelta);

        integerValue.setValue(inRangeIntegerValue1);
        double expected = (double)(inRangeIntegerValue1 - minIntegerValue) / maxIntegerValue - minIntegerValue;
        Assert.assertEquals(expected, integerValue.getNormalized(), floatDelta);

        integerValue.setValue(inRangeIntegerValue2);
        expected = (double)(inRangeIntegerValue2 - minIntegerValue) / maxIntegerValue - minIntegerValue;
        Assert.assertEquals(expected, integerValue.getNormalized(), floatDelta);
    }
}