package network;

import component.Connection;
import component.Neuron;
import exception.InvalidNetworkParametersException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class NetworkTest {

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidInputs() throws InvalidNetworkParametersException {
        new Network(0,1,1);
    }

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidOutputs() throws InvalidNetworkParametersException {
        new Network(1,-1,1);
    }

    @Test(expected = InvalidNetworkParametersException.class)
    public void invalidNetwork_invalidNeuronsByLayer() throws InvalidNetworkParametersException {
        new Network(1,1,1, -1);
    }

    @Test
    public void layersConnections() throws InvalidNetworkParametersException {
        List<Layer> networkLayers = new Network(2, 2, 3, 4, 1).getLayersCopy();

        for(int i=0; i < networkLayers.size() - 1; i++){
            for(Neuron leftNeuron : networkLayers.get(i).getNeurons()){
                for(Neuron rightNeuron : networkLayers.get(i + 1).getNeurons()){
                    Connection leftNeuronOutput = leftNeuron.getOutputConnection();
                    Assert.assertTrue(rightNeuron.getInputConnections().contains(leftNeuronOutput));
                }
            }
        }
    }
}