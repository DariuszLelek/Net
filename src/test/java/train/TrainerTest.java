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

import static org.junit.Assert.*;

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
        Network network = new Network(input, output, neuronsByLayer);

        TrainData trainData = new TrainData();

        input.getTransputValues().get(0).setValue(2.0);
        input.getTransputValues().get(1).setValue(7.0);

        Transput expectedOutput = new Transput();

        expectedOutput.addTransputValue(new TransputValue("output1", 0.0d, 10.0d));
        expectedOutput.addTransputValue(new TransputValue("output2", 0.0d, 10.0d));

        expectedOutput.getTransputValues().get(0).setValue(5.0);
        expectedOutput.getTransputValues().get(1).setValue(2.0);

        trainData.addTrainData(input.getTransputValues(), expectedOutput.getTransputValues());

        Trainer.train(network, trainData, 10000);

        Transput networkOutput = network.getOutput(input);

//        System.out.println(networkOutput);

        System.out.println("Expected = " + 5.0 + " from network: " + networkOutput.getTransputValues().get(0).getValue());
        System.out.println("Expected = " + 2.0 + " from network: " + networkOutput.getTransputValues().get(1).getValue());

    }

}