package network;

import component.Connection;
import component.ConnectionWeight;
import component.neuron.ActivationFunctionType;
import component.neuron.Neuron;
import component.value.Weight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Layer {
    private final ArrayList<Neuron> neurons;

    public Layer(int neuronsNumber) {
        neurons = createNeurons(neuronsNumber);
    }

    private ArrayList<Neuron> createNeurons(int neuronsNumber){
        return IntStream.range(0, neuronsNumber).mapToObj(i -> new Neuron()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void fireNeurons(){
        for(Neuron neuron : neurons){
            neuron.fire();
        }
    }

    public Collection<Connection> getNeuronsOutputConnections(){
        return neurons.stream().map(Neuron::getOutputConnection).collect(Collectors.toList());
    }

    public List<Weight> getNeuronInputsWeights(){
        return neurons.stream().map(Neuron::getInputConnectionWeights)
            .flatMap(List::stream)
            .map(ConnectionWeight::getWeight)
            .collect(Collectors.toList());
    }
}
