package network;

import component.Connection;
import component.ConnectionWeight;
import component.neuron.Neuron;
import component.value.NormalizedValue;
import component.value.Value;
import component.value.Weight;
import exception.InvalidNetworkParametersException;
import exception.ValueNotInRangeException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkTest {
    private final int inputs = 2;
    private final int outputs = 2;

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidInputs() throws InvalidNetworkParametersException {
        new Network(0,outputs,1);
    }

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidOutputs() throws InvalidNetworkParametersException {
        new Network(inputs,0,1);
    }

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidNeuronsByLayer() throws InvalidNetworkParametersException {
        new Network(inputs,outputs,1, 0);
    }

    @Test
    public void layersConnections_neuronsConnected() throws InvalidNetworkParametersException {
        Network network = new Network(inputs, outputs, 3, 4, 1);
        List<Layer> networkLayers = network.getLayers();

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
        int expected = inputs * layer1 + layer1 * layer2 + layer2 * outputs;

        Network network = new Network(inputs, outputs, layer1, layer2);
        List<Layer> networkLayers = network.getLayers();

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
        Network network = new Network(inputs, outputs, 2);
        List<Layer> networkLayers = network.getLayers();

        double expected = 0.75d;
        Neuron leftNeuron = networkLayers.get(0).getNeurons().get(0);
        Neuron rightNeuron = networkLayers.get(1).getNeurons().get(0);

        leftNeuron.getOutputConnection().setValue(expected);

        List<Double> inputConnectionsValues = rightNeuron.getInputConnections().stream()
            .map(Connection::getValue)
            .map(Value::getNormalized)
            .collect(Collectors.toList());

        Assert.assertTrue(inputConnectionsValues.contains(expected));
    }

    @Test
    public void randomiseConnectionWeights() throws InvalidNetworkParametersException, CloneNotSupportedException {
        Network network = new Network(inputs, outputs, 2);

        Collection<Weight> originalWeights = new ArrayList<>();

        for(Layer layer : network.getLayers()){
            for(Weight weight : layer.getNeuronInputsWeights()){
                originalWeights.add((Weight) weight.clone());
            }
        }

        network.randomiseConnectionWeights();

        Collection<Weight> randomizedWeights = new ArrayList<>();

        for(Layer layer : network.getLayers()){
            randomizedWeights.addAll(layer.getNeuronInputsWeights());
        }

        Assert.assertNotEquals(originalWeights, randomizedWeights);
    }

    @Test
    public void produceOutputs() throws InvalidNetworkParametersException, ValueNotInRangeException, CloneNotSupportedException {
        Network network = new Network(inputs, outputs, 2);

        ArrayList<Value> inputValues = new ArrayList<>(inputs);

        Value<Double> inputValue1 = new NormalizedValue();
        Value<Double> inputValue2 = new NormalizedValue();

        inputValue1.setValue(0.6d);
        inputValue1.setValue(0.2d);

        inputValues.add(inputValue1);
        inputValues.add(inputValue2);

        Collection<Value> initialOutputValues = new ArrayList<>();

        for(Value value : network.getOutputValues()){
            initialOutputValues.add((Value) value.clone());
        }

        network.randomiseConnectionWeights();

        Collection<Value> outputValues = network.produceOutputValues(inputValues);

        Assert.assertNotEquals(outputValues, initialOutputValues);
    }
}