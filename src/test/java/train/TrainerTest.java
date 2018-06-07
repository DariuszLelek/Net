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

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TrainerTest {
    private static final double EXPECTED_VALUE_DEVIATION = 0.05;
    private static final int TRAIN_ITERATIONS = 500;
    private static final double MIN = 0.0;
    private static final double MAX = 100.0;

    private final Transput input = new Transput();
    private final int[] neuronsByLayer = new int[]{2};

    @Before
    public void setUp() throws Exception {
        input.addTransputValue(new TransputValue("input1", MIN, MAX));
        input.addTransputValue(new TransputValue("input2", MIN, MAX));
    }

    @Test
    public void train_singleTrainData_singleOutputValue() throws InvalidNetworkParametersException, InvalidTransputDataException, ValueNotInRangeException, InvalidNetworkInputException {
        double expectedValue = 50.0;

        Transput output = new Transput();
        output.addTransputValue(new TransputValue("output1", MIN, MAX));

        Network notTrainedNetwork = new Network(input, output, neuronsByLayer);
        TrainData trainData = new TrainData();

        input.getTransputValues().get(0).setValue(5.0);
        input.getTransputValues().get(1).setValue(0.0);

        Transput expectedOutput = new Transput();
        expectedOutput.addTransputValue(new TransputValue("output1", MIN, MAX, expectedValue));

        double valueFromUntrained = notTrainedNetwork.getOutput(input).getTransputValues().get(0).getValue();

        trainData.addTrainData(input, expectedOutput);
        Network trainedNetwork = Trainer.train(notTrainedNetwork, trainData, TRAIN_ITERATIONS);
        Transput trainedNetworkOutput = trainedNetwork.getOutput(input);

        double valueFromTrained = trainedNetworkOutput.getTransputValues().get(0).getValue();

        Assert.assertNotEquals("Values from trained network and expected should be different.", expectedValue, valueFromTrained);
        Assert.assertNotEquals("Values from untrained network and expected should be different.", expectedValue, valueFromUntrained);
        Assert.assertTrue("After training network value [" + valueFromTrained + "] should be closer to the expected ["
                        + expectedValue + "] than value [" + valueFromUntrained + "] from untrained network.",
                Math.abs(expectedValue - valueFromTrained) <= Math.abs(expectedValue - valueFromUntrained));

        // TODO fix this test
        assertValueInBounds(valueFromTrained, expectedValue);
    }

    @Test
    public void train_multipleTrainData_singleOutputValue() throws InvalidNetworkParametersException, InvalidTransputDataException, ValueNotInRangeException, InvalidNetworkInputException {
        double inputValues[][] = {{2.0, 10.0}, {0.0, 10.0}, {18.0, 5.0}, {10.0, 10.0}};
        double expectedValues[] = {20.0, 0.0, 95.0, 100.0};

        Transput output = new Transput();
        output.addTransputValue(new TransputValue("output1", MIN, MAX));

        Network notTrainedNetwork = new Network(input, output, neuronsByLayer);
        TrainData trainData = new TrainData();

        assert inputValues.length == expectedValues.length;

        Transput expectedOutput;

        for (int i = 0; i < inputValues.length; i++) {
            for (int j = 0; j < inputValues[i].length; j++) {
                input.getTransputValues().get(j).setValue(inputValues[i][j]);
            }

            expectedOutput = new Transput();
            expectedOutput.addTransputValue(new TransputValue("output1", MIN, MAX, expectedValues[i]));

            trainData.addTrainData(input, expectedOutput);
        }

        Network trainedNetwork = Trainer.train(notTrainedNetwork, trainData, TRAIN_ITERATIONS);

        double valueFromTrained;
        double valueExpected;

        for(InputOutputPair pair : trainData.getInputOutputPairs()){
            valueFromTrained = trainedNetwork.getOutput(pair.getInput()).getTransputValues().get(0).getValue();
            valueExpected = pair.getOutput().getTransputValues().get(0).getValue();

            System.out.println("From network: " + valueFromTrained + ", expected: " + valueExpected);

//            assertValueInBounds(valueFromTrained, valueExpected);
        }
    }

    private void assertValueInBounds(double value, double expectedValue){
        int leftBound = getBound(expectedValue, true);
        int rightBound = getBound(expectedValue, false);

        Assert.assertTrue("Value [" + value + "] not in bounds [" + leftBound + " - " + rightBound + "]",
                IntStream.rangeClosed(leftBound, rightBound).boxed().collect(Collectors.toList()).contains((int) value));
    }

    private int getBound(double value, boolean leftBound) {
        return leftBound ? (int) (value - value * EXPECTED_VALUE_DEVIATION) : (int) (value + value * EXPECTED_VALUE_DEVIATION);
    }
}