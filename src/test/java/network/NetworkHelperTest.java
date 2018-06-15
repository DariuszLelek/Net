package network;

import component.value.normalized.LayerBias;
import component.value.normalized.NeuronThreshold;
import exception.InvalidNetworkParametersException;
import org.junit.Assert;
import org.junit.Test;

public class NetworkHelperTest {
    @Test
    public void resetNetworkNeuronsThreshold() throws InvalidNetworkParametersException {
        Network network = TestNetworkDataProvider.getDefaultTestNetwork(2,2);

        NetworkHelper.resetNetworkNeuronsThreshold(network);

        Assert.assertTrue(hasAllNeutronsThresholdReset(network));
    }

    @Test
    public void resetNetworkLayersBias() throws InvalidNetworkParametersException {
        Network network = TestNetworkDataProvider.getDefaultTestNetwork(2,2);

        NetworkHelper.resetNetworkLayersBias(network);

        Assert.assertTrue(hasAllLayersBiasReset(network));
    }

    private boolean hasAllLayersBiasReset(final Network network){
        return network.getLayers().stream().noneMatch(layer -> layer.getLayerBias().getNormalized() != LayerBias.MIN_VALUE);
    }

    private boolean hasAllNeutronsThresholdReset(final Network network){
        return network.getNeurons().stream().noneMatch(neuron -> neuron.getNeuronThreshold().getNormalized() != NeuronThreshold.MIN_VALUE);
    }
}