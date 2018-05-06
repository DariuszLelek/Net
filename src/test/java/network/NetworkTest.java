package network;

import component.Connection;
import component.ConnectionWeight;
import component.neuron.Neuron;
import exception.InvalidNetworkParametersException;
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
        int expected = inputs * layer1 + layer1 * layer2 + layer2 * outputs;

        Network network = new Network(inputs, outputs, layer1, layer2);
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
        Network network = new Network(inputs, outputs, 2);
        List<Layer> networkLayers = network.getLayersCopy();

        double expected = 0.75d;
        Neuron leftNeuron = networkLayers.get(0).getNeurons().get(0);
        Neuron rightNeuron = networkLayers.get(1).getNeurons().get(0);

        leftNeuron.getOutputConnection().setValue(expected);

        List<Double> inputConnectionsValues = rightNeuron.getInputConnections().stream()
            .map(Connection::getValue)
            .collect(Collectors.toList());

        Assert.assertTrue(inputConnectionsValues.contains(expected));
    }
}