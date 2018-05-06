package component;

import component.value.NormalizedValue;
import component.value.ValueTest;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {

    @Test
    public void getValue() throws Exception {
        Connection connection = new Connection(new NormalizedValue());
        Assert.assertEquals(0.0, connection.getValue().getNormalized(), ValueTest.FLOAT_DELTA);
    }

    @Test
    public void setValue() throws Exception {
        Connection connection = new Connection(new NormalizedValue());
        double testValue = 0.65d;
        connection.setValue(testValue);
        Assert.assertEquals(testValue, connection.getValue().getNormalized(), ValueTest.FLOAT_DELTA);
    }

}