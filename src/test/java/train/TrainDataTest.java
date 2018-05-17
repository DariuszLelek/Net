package train;

import component.value.TransputValue;
import exception.InvalidTransputDataException;
import exception.ValueNotInRangeException;
import network.Transput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TrainDataTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addTrainData_differentInputValues() throws InvalidTransputDataException, ValueNotInRangeException {
        int expectedSize = 2;

        Transput input = new Transput();
        Transput output = new Transput();

        input.addTransputValue(new TransputValue("input", 0.0, 10.5));

        TrainData trainData = new TrainData();

        trainData.addTrainData(input.getTransputValues(), output.getTransputValues());

        input.getTransputValues().get(0).setValue(5.0);

        trainData.addTrainData(input.getTransputValues(), output.getTransputValues());

        Assert.assertTrue("Invalid train data size: " + trainData.size() + ", should be: " + expectedSize, trainData.size() == expectedSize);
    }

    @Test
    public void addTrainData_sameInputValues() throws InvalidTransputDataException {
        int expectedSize = 1;

        Transput input = new Transput();
        Transput output = new Transput();

        input.addTransputValue(new TransputValue("input", 0.0, 10.5));

        TrainData trainData = new TrainData();

        trainData.addTrainData(input.getTransputValues(), output.getTransputValues());
        trainData.addTrainData(input.getTransputValues(), output.getTransputValues());

        Assert.assertTrue("Invalid train data size: " + trainData.size() + ", should be: " + expectedSize, trainData.size() == expectedSize);
    }
}