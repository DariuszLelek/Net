package network;

import component.Neuron;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Layer {
    private final List<Neuron> neurons;

    public Layer(int neuronsNumber) {
        neurons = createNeurons(neuronsNumber);
    }

    private List<Neuron> createNeurons(int neuronsNumber){
        return Stream.generate(Neuron::new).limit(neuronsNumber).collect(Collectors.toList());
    }
}
