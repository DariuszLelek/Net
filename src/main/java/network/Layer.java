package network;

import component.ConnectionWeight;
import component.neuron.Neuron;
import component.value.normalized.LayerBias;
import component.value.normalized.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Layer {
    private final String id;
    private final ArrayList<Neuron> neurons;
    private final LayerBias layerBias;

    public Layer(String id, int neuronsNumber) {
        this.id = id;
        layerBias = new LayerBias();
        neurons = createNeurons(neuronsNumber, layerBias);
    }

    private ArrayList<Neuron> createNeurons(int neuronsNumber, final LayerBias layerBias){
        return IntStream.range(0, neuronsNumber)
            .mapToObj(i -> new Neuron(id + "-" + String.valueOf(i), layerBias))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void fireNeurons(){
        neurons.forEach(Neuron::fire);
    }

    public List<Weight> getNeuronInputsWeights(){
        return neurons.stream().map(Neuron::getInputConnectionWeights)
            .flatMap(List::stream)
            .map(ConnectionWeight::getWeight)
            .collect(Collectors.toList());
    }

    public LayerBias getLayerBias() {
        return layerBias;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Layer{" +
                "id='" + id + '\'' +
                ", neurons=" + neurons +
                ", layerBias =" + layerBias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Layer)) return false;

        Layer layer = (Layer) o;

        return id.equals(layer.id) && getNeurons().equals(layer.getNeurons());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + getNeurons().hashCode();
        return result;
    }
}
