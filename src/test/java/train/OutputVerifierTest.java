package train;

import component.value.TransputValue;
import component.value.normalized.NormalizedValue;
import exception.ValueNotInRangeException;
import network.Transput;
import org.junit.Assert;
import org.junit.Test;

public class OutputVerifierTest {

    @Test
    public void getOutputValueMatchRate() throws ValueNotInRangeException {
        TransputValue expectedValue = new TransputValue("value", 0.0, 10.0);
        TransputValue outputValue = new TransputValue("value", 0.0, 10.0);

        expectedValue .setValue(5.0);

        outputValue.setValue(5.0);
        Assert.assertEquals(1.0, OutputVerifier.getOutputValueMatchRate(outputValue, expectedValue), NormalizedValue.FLOAT_DELTA);

        outputValue.setValue(0.0);
        Assert.assertEquals(0.0, OutputVerifier.getOutputValueMatchRate(outputValue, expectedValue), NormalizedValue.FLOAT_DELTA);

        outputValue.setValue(10.0);
        Assert.assertEquals(0.0, OutputVerifier.getOutputValueMatchRate(outputValue, expectedValue), NormalizedValue.FLOAT_DELTA);

        outputValue.setValue(7.0);
        Assert.assertEquals(0.6, OutputVerifier.getOutputValueMatchRate(outputValue, expectedValue), NormalizedValue.FLOAT_DELTA);

        outputValue.setValue(3.0);
        Assert.assertEquals(0.6, OutputVerifier.getOutputValueMatchRate(outputValue, expectedValue), NormalizedValue.FLOAT_DELTA);
    }

    @Test
    public void getOutputValuesMachRate() throws ValueNotInRangeException {
        Transput output = new Transput();
        Transput expectedOutput = new Transput();

        TransputValue expectedValue1 = new TransputValue("value", 0.0, 10.0);
        TransputValue expectedValue2 = new TransputValue("value", 0.0, 20.0);
        TransputValue outputValue1 = new TransputValue(expectedValue1);
        TransputValue outputValue2 = new TransputValue(expectedValue2);

        outputValue1.setValue(2.5);
        outputValue2.setValue(12.5);

        output.addTransputValue(outputValue1);
        output.addTransputValue(outputValue2);

        expectedValue1.setValue(5.0);
        expectedValue2.setValue(10.0);

        expectedOutput.addTransputValue(expectedValue1);
        expectedOutput.addTransputValue(expectedValue2);

        Assert.assertEquals(0.625, OutputVerifier.getOutputValuesMachRate(output.getTransputValues(), expectedOutput.getTransputValues()), NormalizedValue.FLOAT_DELTA);

        outputValue1.setValue(5.0);
        outputValue2.setValue(10.0);
        Assert.assertEquals(1.0 , OutputVerifier.getOutputValuesMachRate(output.getTransputValues(), expectedOutput.getTransputValues()), NormalizedValue.FLOAT_DELTA);
    }
}