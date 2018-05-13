package component.value;

import component.value.normalized.NormalizedValue;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransputValueTest {
    @Test
    public void getNormalized() throws Exception {
        TransputValue value = new TransputValue("value", -10.0, 20.0);

        value.setValue(5.0);
        Assert.assertEquals(0.5, value.getNormalized(), NormalizedValue.FLOAT_DELTA);
        value.setValue(-5.0);
        Assert.assertEquals(0.16666, value.getNormalized(), NormalizedValue.FLOAT_DELTA);
        value.setValue(-10.0);
        Assert.assertEquals(0.0, value.getNormalized(), NormalizedValue.FLOAT_DELTA);
        value.setValue(20.0);
        Assert.assertEquals(1.0, value.getNormalized(), NormalizedValue.FLOAT_DELTA);
    }

    @Test
    public void setFromNormalizedValue() throws Exception {
        TransputValue value = new TransputValue("value", -10.0, 20.0);
        NormalizedValue normalizedValue =  new NormalizedValue();
        normalizedValue.setValue(0.25);

        value.setFromNormalizedValue(normalizedValue);
        Assert.assertEquals(-2.5, value.getValue(), NormalizedValue.FLOAT_DELTA);
        normalizedValue.setValue(0.0);
        value.setFromNormalizedValue(normalizedValue);
        Assert.assertEquals(-10, value.getValue(), NormalizedValue.FLOAT_DELTA);
        normalizedValue.setValue(1.0);
        value.setFromNormalizedValue(normalizedValue);
        Assert.assertEquals(20, value.getValue(), NormalizedValue.FLOAT_DELTA);
    }

}