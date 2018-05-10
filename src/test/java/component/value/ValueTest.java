package component.value;

import exception.ValueNotInRangeException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ValueTest {
    public static final double FLOAT_DELTA = 0.1f;

    private final Integer inRangeIntegerValue1 = 5;
    private final Integer inRangeIntegerValue2 = 8;
    private final Integer notInRangeIntegerValue = 20;
    private final Integer minIntegerValue = 0;
    private final Integer maxIntegerValue = 13;

    private final Value<Boolean> booleanValue = new BooleanValue<>(Boolean.FALSE, Boolean.TRUE);
    private final Value<Integer> integerValue = new NumericValue<>(minIntegerValue, maxIntegerValue);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test(expected = ValueNotInRangeException.class)
    public void setValue_exception() throws ValueNotInRangeException {
        integerValue.set(notInRangeIntegerValue);
    }

    @Test
    public void getValue() throws ValueNotInRangeException {
        Assert.assertEquals(Boolean.FALSE, booleanValue.get());

        booleanValue.set(Boolean.TRUE);
        Assert.assertEquals(Boolean.TRUE, booleanValue.get());

        Assert.assertEquals(minIntegerValue, integerValue.get());
        integerValue.set(inRangeIntegerValue1);
        Assert.assertEquals(inRangeIntegerValue1, integerValue.get());
    }

    @Test
    public void getNormalizedValue() throws ValueNotInRangeException {
        booleanValue.set(Boolean.TRUE);
        Assert.assertEquals(1.0f, booleanValue.getNormalized(), FLOAT_DELTA);
        booleanValue.set(Boolean.FALSE);
        Assert.assertEquals(0.0f, booleanValue.getNormalized(), FLOAT_DELTA);

        integerValue.set(inRangeIntegerValue1);
        double expected = (double)(inRangeIntegerValue1 - minIntegerValue) / maxIntegerValue - minIntegerValue;
        Assert.assertEquals(expected, integerValue.getNormalized(), FLOAT_DELTA);

        integerValue.set(inRangeIntegerValue2);
        expected = (double)(inRangeIntegerValue2 - minIntegerValue) / maxIntegerValue - minIntegerValue;
        Assert.assertEquals(expected, integerValue.getNormalized(), FLOAT_DELTA);
    }
}