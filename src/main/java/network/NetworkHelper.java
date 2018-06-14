package network;

import component.value.normalized.Bias;
import component.value.normalized.Threshold;

public class NetworkHelper {
    public static void resetNetworkNeuronsThreshold(final Network network){
        network.getNeurons().forEach(neuron -> neuron.getThreshold().setFromNormalized(Threshold.MIN_VALUE));
    }

    public static void resetNetworkLayersBias(final Network network){
        network.getLayers().forEach(layer -> layer.getBias().setFromNormalized(Bias.MIN_VALUE));
    }
}
