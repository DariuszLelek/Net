package network;

import component.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
    private final List<Value> inputs;
    private final List<Value> outputs;
    private final List<Layer> layers;

    public Network(List<Value> inputs, List<Value> outputs, int... neuronsByLayer) {
        this.inputs = inputs;
        this.outputs = outputs;

        layers = createLayers(inputs, outputs, neuronsByLayer);

        initialize();
    }

    private List<Layer> createLayers(List<Value> inputs, List<Value> outputs, int... neuronsByLayer){
        List<Layer> layers = new ArrayList<>(neuronsByLayer.length + 2);

        layers.add(createInputLayer(inputs));
        layers.addAll(createHiddenLayers(neuronsByLayer));
        layers.add(createOutputLayer(outputs));

        return layers;
    }

    private Layer createInputLayer(List<Value> inputs){

        return null;
    }

    private Collection<Layer> createHiddenLayers(int... neuronsByLayer){
        return IntStream.of(neuronsByLayer).mapToObj(Layer::new).collect(Collectors.toList());
    }

    private Layer createOutputLayer(List<Value> outputs){

        return null;
    }

    private void initialize(){
        connectLayers();
        randomiseConnectionWeights();
        randomiseNeuronThresholds();
    }

    private void connectLayers(){

    }

    private void randomiseConnectionWeights(){

    }

    private void randomiseNeuronThresholds(){

    }

    public List<Layer> getLayers() {
        return new ArrayList<>(layers);
    }
}
