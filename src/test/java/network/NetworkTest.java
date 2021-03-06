package network;

import component.value.normalized.Connection;
import component.ConnectionWeight;
import component.neuron.Neuron;
import component.value.TransputValue;
import component.value.normalized.NormalizedValue;
import component.value.normalized.Weight;
import exception.InvalidNetworkInputException;
import exception.InvalidNetworkParametersException;
import exception.ValueNotInRangeException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkTest {
    private static final double DELTA = 0.00001;

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

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidInputs() throws InvalidNetworkParametersException {
        new Network(new Transput(), output, new int[]{1});
    }

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidOutputs() throws InvalidNetworkParametersException {
        new Network(input,new Transput(), new int[]{1});
    }

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidNeuronsByLayer() throws InvalidNetworkParametersException {
        new Network(input, output, new int[]{1, 0});
    }

    @Test
    public void invalidNetwork_valid() throws InvalidNetworkParametersException {
        new Network(input, output, new int[]{1});
        new Network(input, output, new int[]{});
        new Network(input, output, new int[]{5, 2});
    }

    @Test
    public void layersConnections_neuronsConnected() throws InvalidNetworkParametersException {
        Network network = new Network(input, output,  new int[]{3, 4, 1});
        List<Layer> networkLayers = network.getLayersCopy();

        for(int i=0; i < networkLayers.size() - 1; i++){
            for(Neuron leftNeuron : networkLayers.get(i).getNeurons()){
                for(Neuron rightNeuron : networkLayers.get(i + 1).getNeurons()){
                    Connection leftNeuronOutput = leftNeuron.getOutputConnection();
                    Assert.assertTrue(rightNeuron.getInputConnections().contains(leftNeuronOutput));
                }
            }
        }
    }

    @Test
    public void layersConnections_connectionsNumber() throws InvalidNetworkParametersException {
        int layer1 = 3;
        int layer2 = 2;
        int expected = input.size() * layer1 + layer1 * layer2 + layer2 * output.size();

        Network network = new Network(input, output, new int[]{layer1, layer2});
        List<Layer> networkLayers = network.getLayersCopy();

        Collection<ConnectionWeight> connectionWeights = new ArrayList<>();

        for (int i = 1; i < networkLayers.size(); i++) {
            for (Neuron neuron : networkLayers.get(i).getNeurons()) {
                connectionWeights.addAll(neuron.getInputConnectionWeights());
            }
        }

        Assert.assertEquals(expected , connectionWeights.size());
    }

    @Test
    public void layersConnections_valuePassedBetweenTwoNeurons() throws InvalidNetworkParametersException {
        Network network = new Network(input, output, neuronsByLayer);
        List<Layer> networkLayers = network.getLayersCopy();

        double expected = 0.75d;
        Neuron leftNeuron = networkLayers.get(0).getNeurons().get(0);
        Neuron rightNeuron = networkLayers.get(1).getNeurons().get(0);

        leftNeuron.getOutputConnection().setFromNormalized(expected);

        List<Double> inputConnectionsValues = rightNeuron.getInputConnections().stream()
            .map(Connection::getNormalized)
            .collect(Collectors.toList());

        Assert.assertTrue(inputConnectionsValues.contains(expected));
    }

    @Test
    public void randomiseConnectionsWeight() throws InvalidNetworkParametersException {
        Network network = new Network(input, output, neuronsByLayer);

        Collection<Weight> originalWeights = new ArrayList<>();

        for(Layer layer : network.getLayersCopy()){
            originalWeights.addAll(layer.getNeuronInputsWeights());
        }

        Collection<Weight> randomizedWeights = new ArrayList<>();

        network = new Network(input, output, neuronsByLayer);
        network.train();

        for(Layer layer : network.getLayersCopy()){
            randomizedWeights.addAll(layer.getNeuronInputsWeights());
        }

        Assert.assertNotEquals(originalWeights, randomizedWeights);
    }

    @Test
    public void getOutputs_sameInputs() throws InvalidNetworkParametersException, ValueNotInRangeException, InvalidNetworkInputException {
        Transput input = new Transput();

        TransputValue value1 = new TransputValue("input1", 0.0d, 10.0d);
        TransputValue value2 = new TransputValue("input2", 0.0d, 25.0d);

        value1.setValue(6.0d);
        value2.setValue(12.0d);

        input.addTransputValue(value1);
        input.addTransputValue(value2);

        Network network = new Network(input, this.output, neuronsByLayer);

        Transput output = network.getOutput(input);

        double output1 = output.getTransputValues().get(0).getValue();
        double output2 = output.getTransputValues().get(1).getValue();

        output = network.getOutput(input);

        Assert.assertEquals(output.getTransputValues().get(0).getValue(), output1, DELTA);
        Assert.assertEquals(output.getTransputValues().get(1).getValue(), output2, DELTA);
    }

    @Test(expected = InvalidNetworkInputException.class)
    public void getOutputs_differentInputs() throws InvalidNetworkParametersException, ValueNotInRangeException, InvalidNetworkInputException {
        Transput input1 = new Transput();
        Transput input2 = new Transput();

        input1.addTransputValue(new TransputValue("input1", 0.0d, 10.0d));
        input1.addTransputValue(new TransputValue("input2", 0.0d, 10.0d));

        input2.addTransputValue(new TransputValue("input3", 0.0d, 10.0d));
        input2.addTransputValue(new TransputValue("input4", 0.0d, 10.0d));

        Network network = new Network(input1, this.output, neuronsByLayer);

        network.getOutput(input2);
    }

    @Test
    public void getOutputs_differentInputValues() throws InvalidNetworkParametersException, ValueNotInRangeException, InvalidNetworkInputException {
        Transput input = new Transput();

        TransputValue value1 = new TransputValue("input1", 0.0d, 10.0d);
        TransputValue value2 = new TransputValue("input2", 0.0d, 25.0d);

        value1.setValue(9.0d);
        value2.setValue(12.0d);

        input.addTransputValue(value1);
        input.addTransputValue(value2);

        Network network = new Network(input, this.output, neuronsByLayer);
        network.resetNeuronsBias();

        Transput output = network.getOutput(input);

        double output1 = output.getTransputValues().get(0).getValue();
        double output2 = output.getTransputValues().get(1).getValue();

        input.getTransputValues().get(0).setValue(1.0);
        input.getTransputValues().get(1).setValue(5.0);

        output = network.getOutput(input);

        double output11 = output.getTransputValues().get(0).getValue();
        double output22 = output.getTransputValues().get(1).getValue();

        Assert.assertNotEquals(output11, output1, DELTA);
        Assert.assertNotEquals(output22, output2, DELTA);
    }

    @Test
    public void getOutputs_inRange() throws InvalidNetworkParametersException, ValueNotInRangeException, InvalidNetworkInputException {
        Transput input = new Transput();
        Transput output = new Transput();

        TransputValue value1 = new TransputValue("input1", 0.0d, 10.0d);
        TransputValue value2 = new TransputValue("input2", 0.0d, 25.0d);

        value1.setValue(6.0d);
        value2.setValue(12.0d);

        input.addTransputValue(value1);
        input.addTransputValue(value2);

        double min1 = -5.0d;
        double min2 = 0.0d;
        double max1 = 5.0d;
        double max2 = 10.0d;

        output.addTransputValue(new TransputValue("output1", min1, max1));
        output.addTransputValue(new TransputValue("output2", min2, max2));

        Network network = new Network(input, output, new int[]{});

        output = network.getOutput(input);

        Assert.assertTrue(output.getTransputValues().size() == 2);

        double output1 = output.getTransputValues().get(0).getValue();
        double output2 = output.getTransputValues().get(1).getValue();

        Assert.assertTrue(output1 >= min1 && output1 <= max1);
        Assert.assertTrue(output2 >= min2 && output2 <= max2);
    }

    @Test
    public void copy() throws InvalidNetworkParametersException {
        Network network = new Network(input, output, neuronsByLayer);
        Network copy = network.copy();

        Assert.assertEquals(network, copy);
    }
}