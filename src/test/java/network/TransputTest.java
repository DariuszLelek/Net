package network;

import component.value.TransputValue;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransputTest {

    @Test
    public void copy() throws CloneNotSupportedException {
        Transput transput = new Transput();

        transput.addTransputValue(new TransputValue("value1", 0.0, 10.0));

        Transput transputCopy = transput.copy();

        Assert.assertEquals(transput, transputCopy);

        transput.addTransputValue(new TransputValue("value2", 0.0, 10.0));

        Assert.assertNotEquals(transput, transputCopy);

        transput.clear();

        Assert.assertTrue(transputCopy.size() > 0);
    }
}