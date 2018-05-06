package network;

import component.Connection;
import component.neuron.ActivationFunctionType;
import component.neuron.Neuron;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Layer {
    private final List<Neuron> neurons;

    public Layer(int neuronsNumber, ActivationFunctionType activationFunctionType) {
        neurons = createNeurons(neuronsNumber, activationFunctionType);
    }

    private List<Neuron> createNeurons(int neuronsNumber, ActivationFunctionType activationFunctionType){
        return IntStream.range(0, neuronsNumber).mapToObj(i -> new Neuron(activationFunctionType)).collect(Collectors.toList());
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public Collection<Connection> getOutputNeuronsConnections(){
        return neurons.stream().map(Neuron::getOutputConnection).collect(Collectors.toList());
    }
}
