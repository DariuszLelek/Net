package network.mutation;

import component.ConnectionWeight;
import component.neuron.Neuron;
import component.value.normalized.LayerBias;
import component.value.normalized.NeuronThreshold;
import component.value.normalized.NormalizedValue;
import component.value.normalized.Originator;
import component.value.normalized.type.Type;
import component.value.normalized.Weight;
import exception.TraceableNotFoundException;
import network.Layer;
import network.Network;
import component.NormalizedValueSupplier;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class NetworkMutation {
    private static final Map<Type, NormalizedValueSupplier> traceableProviders = new HashMap<>();
    private static final Map<Type, MutationInfoSupplier> mutationProviders = new HashMap<>();

    static {
        traceableProviders.put(Type.WEIGHT, network -> getNeuronsConnectionWeight(network));
        traceableProviders.put(Type.BIAS, network -> getLayersBias(network));
        traceableProviders.put(Type.THRESHOLD, network -> getNeuronsThreshold(network));

        mutationProviders.put(Type.WEIGHT, network -> mutateRandomConnectionWeight(network));
        mutationProviders.put(Type.BIAS, network -> mutateRandomLayerBias(network));
        mutationProviders.put(Type.THRESHOLD, network -> mutateRandomNeuronThreshold(network));
    }

    public static MutationInfo mutateRandom(final Network network){
        return mutationProviders.get(Type.getRandomMutable()).get(network);
    }

    public static void removeMutation(Network network, MutationInfo mutationInfo) throws TraceableNotFoundException {
        getTraceable(traceableProviders.get(mutationInfo.getOriginator().getType()).get(network),
                mutationInfo.getOriginator()).setFromNormalized(mutationInfo.getOldNormalizedValue());
    }

    public static MutationInfo mutateRandomLayerBias(final Network network){
        Layer layer = getRandomNetworkLayer(network);
        double oldNormalizedValue = layer.getLayerBias().getNormalized();
        layer.getLayerBias().setFromNormalized(new Random().nextDouble() * LayerBias.MAX_VALUE);
        return new MutationInfo(layer.getLayerBias(), oldNormalizedValue);
    }

    public static MutationInfo mutateRandomNeuronThreshold(final Network network){
        Neuron neuron = getRandomNetworkNeuron(network);
        double oldNormalizedValue = neuron.getNeuronThreshold().getNormalized();
        neuron.getNeuronThreshold().setFromNormalized(new Random().nextDouble() * NeuronThreshold.MAX_VALUE);
        return new MutationInfo(neuron.getNeuronThreshold(), oldNormalizedValue);
    }

    public static MutationInfo mutateRandomConnectionWeight(final Network network){
        ConnectionWeight connectionWeight = getRandomNeuronConnectionWeight(getRandomNetworkNeuron(network));
        double oldNormalizedValue = connectionWeight.getWeight().getNormalized();
        connectionWeight.getWeight().setFromNormalized(new Random().nextDouble() * Weight.MAX_VALUE);
        return new MutationInfo(connectionWeight.getWeight(), oldNormalizedValue);
    }

    public static Neuron getRandomNetworkNeuron(final Network network){
        List<Neuron> neurons =  network.getNeurons();
        return neurons.get(new Random().nextInt(neurons.size()));
    }

    public static Layer getRandomNetworkLayer(final Network network){
        List<Layer> layers = network.getLayers();
        return layers.get(new Random().nextInt(layers.size()));
    }

    public static ConnectionWeight getRandomNeuronConnectionWeight(final Neuron neuron){
        List<ConnectionWeight> connectionWeights = neuron.getInputConnectionWeights();
        return connectionWeights.get(new Random().nextInt(connectionWeights.size()));
    }

    private static Collection<? extends NormalizedValue> getNeuronsThreshold(final Network network){
        return network.getNeurons().stream().map(Neuron::getNeuronThreshold).collect(Collectors.toList());
    }

    private static Collection<? extends NormalizedValue> getLayersBias(final Network network){
        return network.getLayers().stream().map(Layer::getLayerBias).collect(Collectors.toList());
    }

    private static Collection<? extends NormalizedValue> getNeuronsConnectionWeight(final Network network){
        return getNetworkConnectionWeights(network).stream().map(ConnectionWeight::getWeight).collect(Collectors.toList());
    }

    private static Set<ConnectionWeight> getNetworkConnectionWeights(final Network network){
        return new HashSet<>(network.getNeurons().stream().flatMap(neuron ->
                neuron.getInputConnectionWeights().stream()).collect(Collectors.toList()));
    }

    private static NormalizedValue getTraceable(Collection<? extends NormalizedValue> traceables, Originator originator) throws TraceableNotFoundException {
        return traceables.stream().filter(traceable -> traceable.match(originator)).findFirst().orElseThrow(TraceableNotFoundException::new);
    }
}
