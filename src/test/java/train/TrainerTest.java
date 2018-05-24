package train;

import component.value.TransputValue;
import exception.InvalidNetworkInputException;
import exception.InvalidNetworkParametersException;
import exception.InvalidTransputDataException;
import exception.ValueNotInRangeException;
import network.Network;
import network.Transput;
import org.junit.Before;
import org.junit.Test;

public class TrainerTest {
    private final Transput input = new Transput();
    private final Transput output = new Transput();
    private final int[] neuronsByLayer = new int[]{2};

    @Before
    public void setUp() throws Exception {
        input.addTransputValue(new TransputValue("input1", 0.0d, 10.0d));
        input.addTransputValue(new TransputValue("input2", 0.0d, 10.0d));
        output.addTransputValue(new TransputValue("output1", 0.0d, 10.0d));
        output.addTransputValue(new TransputValue("output2", 0.0d, 10.0d));
    }

    @Test
    public void train() throws InvalidNetworkParametersException, InvalidTransputDataException, ValueNotInRangeException, InvalidNetworkInputException {
        Transput output = new Transput();

        output.addTransputValue(new TransputValue("output1", 0.0d, 100.0d));

        Network untrainedNetwork = new Network(input, output, neuronsByLayer);

        TrainData trainData = new TrainData();

        input.getTransputValues().get(0).setValue(5.0);
        input.getTransputValues().get(1).setValue(0.0);

        Transput expectedOutput = new Transput();

        expectedOutput.addTransputValue(new TransputValue("output1", 0.0d, 100.0d));

        expectedOutput.getTransputValues().get(0).setValue(50.0);

        trainData.addTrainData(input.getTransputValues(), expectedOutput.getTransputValues());

        Network trainedNetwork = Trainer.train(untrainedNetwork, trainData, 50000);

        Transput tranedNetworkOutput = trainedNetwork.getOutput(input);

        System.out.println("Expected = " + 50.0 + " from network: " + tranedNetworkOutput.getTransputValues().get(0).getValue());
    }

}