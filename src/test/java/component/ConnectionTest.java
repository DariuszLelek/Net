package component;

import component.value.normalized.Connection;
import component.value.NumericValueTest;
import component.value.normalized.NormalizedValue;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {

    @Test
    public void getValue() throws Exception {
        Connection connection = new Connection();
        Assert.assertEquals(0.0, connection.getNormalized(), NormalizedValue.FLOAT_DELTA);
    }

    @Test
    public void setValue() throws Exception {
        Connection connection = new Connection();
        double testValue = 0.65d;
        connection.setFromNormalized(testValue);
        Assert.assertEquals(testValue, connection.getNormalized(), NormalizedValue.FLOAT_DELTA);
    }

}