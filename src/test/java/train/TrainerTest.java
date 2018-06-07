package train;

import component.value.TransputValue;
import exception.InvalidNetworkInputException;
import exception.InvalidNetworkParametersException;
import exception.InvalidTransputDataException;
import exception.ValueNotInRangeException;
import network.Network;
import network.Transput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainerTest {
    private static final double EXPECTED_VALUE_DEVIATION = 0.05;
    private final static int TRAIN_ITERATIONS = 500;

    private final Transput input = new Transput();
    private final Transput output = new Transput();
    private final int[] neuronsByLayer = new int[]{2};

    @Before
    public void setUp() throws Exception {
        input.addTransputValue(new TransputValue("input1", 0.0, 10.0));
        input.addTransputValue(new TransputValue("input2", 0.0, 10.0));
        output.addTransputValue(new TransputValue("output1", 0.0, 10.0));
        output.addTransputValue(new TransputValue("output2", 0.0, 10.0));
    }

    @Test
    public void train() throws InvalidNetworkParametersException, InvalidTransputDataException, ValueNotInRangeException, InvalidNetworkInputException {
        double valueExpected = 50.0;

        Transput output = new Transput();
        output.addTransputValue(new TransputValue("output1", 0.0, 100.0));

        Network notTrainedNetwork = new Network(input, output, neuronsByLayer);
        TrainData trainData = new TrainData();

        input.getTransputValues().get(0).setValue(5.0);
        input.getTransputValues().get(1).setValue(0.0);

        Transput expectedOutput = new Transput();
        expectedOutput.addTransputValue(new TransputValue("output1", 0.0, 100.0));
        expectedOutput.getTransputValues().get(0).setValue(valueExpected);

        double valueFromUntrained = notTrainedNetwork.getOutput(input).getTransputValues().get(0).getValue();

        trainData.addTrainData(input.getTransputValues(), expectedOutput.getTransputValues());
        Network trainedNetwork = Trainer.train(notTrainedNetwork, trainData, TRAIN_ITERATIONS);
        Transput trainedNetworkOutput = trainedNetwork.getOutput(input);

        double valueFromTrained = trainedNetworkOutput.getTransputValues().get(0).getValue();

        Assert.assertNotEquals("Values from trained network and expected should be different.", valueExpected, valueFromTrained);
        Assert.assertNotEquals("Values from untrained network and expected should be different.", valueExpected, valueFromUntrained);

        int leftBound = getBound(valueExpected, true);
        int rightBound = getBound(valueExpected, false);

        Assert.assertTrue("Value [" + valueFromTrained + "] returned by trained network not in bounds [" + leftBound + " - " + rightBound + "]",
                IntStream.rangeClosed(leftBound, rightBound).boxed().collect(Collectors.toList()).contains((int) valueFromTrained));

        Assert.assertTrue("After training network value [" + valueFromTrained + "] should be closer to to expected ["
                        + valueExpected + "] than value [" + valueFromUntrained + "] from untrained network.",
                Math.abs(valueExpected - valueFromTrained) <= Math.abs(valueExpected - valueFromUntrained));
    }

    private int getBound(double value, boolean leftBound) {
        return leftBound ? (int) (value - value * EXPECTED_VALUE_DEVIATION) : (int) (value + value * EXPECTED_VALUE_DEVIATION);
    }
}