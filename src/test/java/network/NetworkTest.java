package network;

import component.value.AccessValue;
import exception.InvalidNetworkParametersException;
import exception.ValueNotInRangeException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class NetworkTest {
    private final List<AccessValue> inputs = new ArrayList<>();
    private final List<AccessValue> outputs = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        inputs.add(new AccessValue("input1", 0.0d, 10.0d));
        inputs.add(new AccessValue("input2", 0.0d, 10.0d));
        outputs.add(new AccessValue("output1", 0.0d, 10.0d));
        outputs.add(new AccessValue("output2", 0.0d, 10.0d));
    }

//    @Test(expected = InvalidNetworkParametersException.class)
//    public void invalidNetwork_invalidInputs() throws InvalidNetworkParametersException {
//        new Network(new ArrayList<>(), outputs,1);
//    }
//
//    @Test(expected = InvalidNetworkParametersException.class)
//    public void invalidNetwork_invalidOutputs() throws InvalidNetworkParametersException {
//        new Network(inputs,new ArrayList<>(),1);
//    }
//
//    @Test(expected = InvalidNetworkParametersException.class)
//    public void invalidNetwork_invalidNeuronsByLayer() throws InvalidNetworkParametersException {
//        new Network(inputs,outputs,1, 0);
//    }
//
//    @Test
//    public void layersConnections_neuronsConnected() throws InvalidNetworkParametersException {
//        Network network = new Network(inputs, outputs, 3, 4, 1);
//        List<Layer> networkLayers = network.getLayers();
//
//        for(int i=0; i < networkLayers.size() - 1; i++){
//            for(Neuron leftNeuron : networkLayers.get(i).getNeurons()){
//                for(Neuron rightNeuron : networkLayers.get(i + 1).getNeurons()){
//                    Connection leftNeuronOutput = leftNeuron.getOutputConnection();
//                    Assert.assertTrue(rightNeuron.getInputConnections().contains(leftNeuronOutput));
//                }
//            }
//        }
//    }
//
//    @Test
//    public void layersConnections_connectionsNumber() throws InvalidNetworkParametersException {
//        int layer1 = 3;
//        int layer2 = 2;
//        int expected = inputs.size() * layer1 + layer1 * layer2 + layer2 * outputs.size();
//
//        Network network = new Network(inputs, outputs, layer1, layer2);
//        List<Layer> networkLayers = network.getLayers();
//
//        Collection<ConnectionWeight> connectionWeights = new ArrayList<>();
//
//        for (int i = 1; i < networkLayers.size(); i++) {
//            for (Neuron neuron : networkLayers.get(i).getNeurons()) {
//                connectionWeights.addAll(neuron.getInputConnectionWeights());
//            }
//        }
//
//        Assert.assertEquals(expected , connectionWeights.size());
//    }
//
//    @Test
//    public void layersConnections_valuePassedBetweenTwoNeurons() throws InvalidNetworkParametersException {
//        Network network = new Network(inputs, outputs, 2);
//        List<Layer> networkLayers = network.getLayers();
//
//        double expected = 0.75d;
//        Neuron leftNeuron = networkLayers.get(0).getNeurons().get(0);
//        Neuron rightNeuron = networkLayers.get(1).getNeurons().get(0);
//
//        leftNeuron.getOutputConnection().setNormalized(expected);
//
//        List<Double> inputConnectionsValues = rightNeuron.getInputConnections().stream()
//            .map(Connection::getNormalizedValue)
//            .map(Value::getNormalized)
//            .collect(Collectors.toList());
//
//        Assert.assertTrue(inputConnectionsValues.contains(expected));
//    }
//
//    @Test
//    public void randomiseConnectionsWeight() throws InvalidNetworkParametersException, CloneNotSupportedException {
//        Network network = new Network(inputs, outputs, 2);
//
//        Collection<Weight> originalWeights = new ArrayList<>();
//
//        for(Layer layer : network.getLayers()){
//            for(Weight weight : layer.getNeuronInputsWeights()){
//                originalWeights.add((Weight) weight.clone());
//            }
//        }
//
//        network.randomiseConnectionsWeight();
//
//        Collection<Weight> randomizedWeights = new ArrayList<>();
//
//        for(Layer layer : network.getLayers()){
//            randomizedWeights.addAll(layer.getNeuronInputsWeights());
//        }
//
//        Assert.assertNotEquals(originalWeights, randomizedWeights);
//    }
//
//    @Test
//    public void getOutputs() throws InvalidNetworkParametersException, ValueNotInRangeException, CloneNotSupportedException {
//        Network network = new Network(inputs, outputs, 2);
//
//        List<AccessValue> inputValues = network.getInputs();
//
//        Assert.assertEquals(2, inputValues.size());
//
//        inputValues.get(0).set(0.6d);
//        inputValues.get(0).set(0.2d);
//
//        Collection<Value> initialOutputValues = new ArrayList<>();
//
//        for(Value value : network.getOutputValues()){
//            initialOutputValues.add((Value) value.clone());
//        }
//
//        network.randomiseConnectionsWeight();
//        network.fire();
//
//        Collection<AccessValue> outputValues = network.getOutputs();
//
//        Assert.assertNotEquals(outputValues, initialOutputValues);
//    }

    @Test
    public void getOutputs_inRange() throws InvalidNetworkParametersException, ValueNotInRangeException, CloneNotSupportedException {
        Access input = new Access();
        Access output = new Access();

        AccessValue value1 = new AccessValue("input1", 0.0d, 10.0d);
        AccessValue value2 = new AccessValue("input2", 0.0d, 25.0d);
        value1.set(6.0d);
        value2.set(12.0d);

        input.addBorderValue(value1);
        input.addBorderValue(value2);

        output.addBorderValue(new AccessValue("output1", 0.0d, 10.0d));

        Network network = new Network(input, output);
        network.randomiseConnectionsWeight();
        network.randomiseNeuronsBias();

        output = network.getOutput(input);

        for (int i =0 ; i<10; i++){
            network.randomiseConnectionsWeight();
            System.out.println(network.getOutput(input).getAccessValues().get(0).get());
        }

//        Assert.assertNotEquals(outputValues, initialOutputValues);
    }
}