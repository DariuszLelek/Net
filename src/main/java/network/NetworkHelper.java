package network;

import component.value.normalized.LayerBias;
import component.value.normalized.NeuronThreshold;

public class NetworkHelper {
    public static void resetNetworkNeuronsThreshold(final Network network){
        network.getNeurons().forEach(neuron -> neuron.getNeuronThreshold().setFromNormalized(NeuronThreshold.MIN_VALUE));
    }

    public static void resetNetworkLayersBias(final Network network){
        network.getLayers().forEach(layer -> layer.getLayerBias().setFromNormalized(LayerBias.MIN_VALUE));
    }
}
