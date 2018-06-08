package network.helper;

import component.neuron.Neuron;
import network.Network;

import java.util.List;
import java.util.Random;

public class NetworkHelper {
    private final Random random = new Random();

    public Neuron getRandomNetworkNeuron(final Network network){
        List<Neuron> neurons =  network.getNeurons();
        return neurons.get(random.nextInt(neurons.size()));
    }
}
