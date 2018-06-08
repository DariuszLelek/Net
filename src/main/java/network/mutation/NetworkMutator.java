package network.mutation;

import com.sun.istack.internal.Nullable;
import component.ConnectionWeight;
import component.TraceableChange;
import component.neuron.Neuron;
import component.value.normalized.Bias;
import component.value.normalized.Threshold;
import component.value.normalized.Weight;
import exception.TraceableNotFoundException;
import network.Network;
import component.TraceableChangeSupplier;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

// TODO change name to something else?
public class NetworkMutator {
    private static final Map<MutationType, TraceableChangeSupplier> traceableProviders = new HashMap<>();
    private static final Map<MutationType, NetworkMutationSupplier> mutationProviders = new HashMap<>();

    static {
        traceableProviders.put(MutationType.CONNECTION_WEIGHT, network -> getNeuronsConnectionWeight(network));
        traceableProviders.put(MutationType.NEURON_BIAS, network -> getNeuronsBias(network));
        traceableProviders.put(MutationType.NEURON_THRESHOLD, network -> getNeuronsThreshold(network));

        mutationProviders.put(MutationType.CONNECTION_WEIGHT, network -> mutateConnectionWeight(network));
        mutationProviders.put(MutationType.NEURON_BIAS, network -> mutateNeuronBias(network));
        mutationProviders.put(MutationType.NEURON_THRESHOLD, network -> mutateNeuronThreshold(network));
    }

    public static NetworkMutationInfo mutate(final Network network){
        return mutationProviders.get(MutationType.getRandom()).get(network);
    }

    public static void removeMutation(NetworkMutationInfo networkMutationInfo) throws TraceableNotFoundException {
        getTraceable(traceableProviders.get(networkMutationInfo.getType()).get(networkMutationInfo.getNetwork()),
                networkMutationInfo.getOriginator()).setOriginal(networkMutationInfo.getOldNormalizedValue());
    }

    public static NetworkMutationInfo mutateNeuronBias(final Network network){
        Neuron neuron = getRandomNetworkNeuron(network);
        double oldNormalizedValue = neuron.getBias().getNormalized();
        neuron.getBias().setFromNormalized(new Random().nextDouble() * Bias.MAX_VALUE);
        return new NetworkMutationInfo(network, MutationType.NEURON_BIAS, oldNormalizedValue, neuron.getBias());
    }

    public static NetworkMutationInfo mutateNeuronThreshold(final Network network){
        Neuron neuron = getRandomNetworkNeuron(network);
        double oldNormalizedValue = neuron.getThreshold().getNormalized();
        neuron.getThreshold().setFromNormalized(new Random().nextDouble() * Threshold.MAX_VALUE);
        return new NetworkMutationInfo(network, MutationType.NEURON_THRESHOLD, oldNormalizedValue, neuron.getThreshold());
    }

    public static NetworkMutationInfo mutateConnectionWeight(final Network network){
        ConnectionWeight connectionWeight = getRandomNeuronConnectionWeight(getRandomNetworkNeuron(network));
        double oldNormalizedValue = connectionWeight.getWeight().getNormalized();
        connectionWeight.getWeight().setFromNormalized(new Random().nextDouble() * Weight.MAX_VALUE);
        return new NetworkMutationInfo(network, MutationType.CONNECTION_WEIGHT, oldNormalizedValue, connectionWeight.getWeight());
    }

    public static Neuron getRandomNetworkNeuron(final Network network){
        List<Neuron> neurons =  network.getNeurons();
        return neurons.get(new Random().nextInt(neurons.size()));
    }

    public static ConnectionWeight getRandomNeuronConnectionWeight(final Neuron neuron){
        List<ConnectionWeight> connectionWeights = neuron.getInputConnectionWeights();
        return connectionWeights.get(new Random().nextInt(connectionWeights.size()));
    }

    private static Collection<? extends TraceableChange> getNeuronsThreshold(final Network network){
        return network.getNeurons().stream().map(Neuron::getThreshold).collect(Collectors.toList());
    }

    private static Collection<? extends TraceableChange> getNeuronsBias(final Network network){
        return network.getNeurons().stream().map(Neuron::getBias).collect(Collectors.toList());
    }

    private static Collection<? extends TraceableChange> getNeuronsConnectionWeight(final Network network){
        return getNetworkConnectionWeights(network).stream().map(ConnectionWeight::getWeight).collect(Collectors.toList());
    }

    private static Set<ConnectionWeight> getNetworkConnectionWeights(final Network network){
        return new HashSet<>(network.getNeurons().stream().flatMap(neuron ->
                neuron.getInputConnectionWeights().stream()).collect(Collectors.toList()));
    }

    @Nullable
    private static TraceableChange getTraceable(Collection<? extends TraceableChange> traceables, TraceableChange originator) throws TraceableNotFoundException {
        return traceables.stream().filter(traceable -> traceable.match(originator)).findFirst().orElseThrow(TraceableNotFoundException::new);
    }
}
