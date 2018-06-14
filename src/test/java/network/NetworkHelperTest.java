package network;

import component.value.normalized.Bias;
import component.value.normalized.Threshold;
import exception.InvalidNetworkParametersException;
import org.junit.Assert;
import org.junit.Test;

public class NetworkHelperTest {
    @Test
    public void resetNetworkNeuronsThreshold() throws InvalidNetworkParametersException {
        Network network = TestNetworkProvider.getDefaultTestNetwork();

        NetworkHelper.resetNetworkNeuronsThreshold(network);

        Assert.assertTrue(hasAllNeutronsThresholdReset(network));
    }

    @Test
    public void resetNetworkLayersBias() throws InvalidNetworkParametersException {
        Network network = TestNetworkProvider.getDefaultTestNetwork();

        NetworkHelper.resetNetworkLayersBias(network);

        Assert.assertTrue(hasAllLayersBiasReset(network));
    }

    private boolean hasAllLayersBiasReset(final Network network){
        return network.getLayers().stream().noneMatch(layer -> layer.getBias().getNormalized() != Bias.MIN_VALUE);
    }

    private boolean hasAllNeutronsThresholdReset(final Network network){
        return network.getNeurons().stream().noneMatch(neuron -> neuron.getThreshold().getNormalized() != Threshold.MIN_VALUE);
    }
}