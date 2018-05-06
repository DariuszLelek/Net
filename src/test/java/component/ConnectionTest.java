package component;

import component.value.ValueTest;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTest {

    @Test
    public void getValue() throws Exception {
        Connection connection = new Connection();
        Assert.assertEquals(0.0, connection.getValue(), ValueTest.FLOAT_DELTA);
    }

    @Test
    public void setValue() throws Exception {
        Connection connection = new Connection();
        double testValue = 0.65d;
        connection.setValue(testValue);
        Assert.assertEquals(testValue, connection.getValue(), ValueTest.FLOAT_DELTA);
    }

}