package network;

import component.Neuron;
import exception.InvalidNetworkParametersException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
    private final ArrayList<Layer> layers;

    public Network(int inputs, int outputs, int... neuronsByLayer) throws InvalidNetworkParametersException {
        if(!isValid(inputs, outputs, neuronsByLayer)){
            String invalidParametersMessage = "Cannot construct network with given parameters: " +
                "inputs = " + inputs + " outputs = " + outputs +
                " neuronsByLayer = " + String.join(",", Arrays.toString(neuronsByLayer));
            throw new InvalidNetworkParametersException(invalidParametersMessage);
        }

        layers = createLayers(inputs, outputs, neuronsByLayer);

        initialize();
    }

    private ArrayList<Layer> createLayers(int inputs, int outputs, int... neuronsByLayer){
        ArrayList<Layer> layers = new ArrayList<>(neuronsByLayer.length + 2);

        layers.add(new Layer(inputs));
        layers.addAll(IntStream.of(neuronsByLayer).mapToObj(Layer::new).collect(Collectors.toList()));
        layers.add(new Layer(outputs));

        return layers;
    }

    private void initialize(){
        connectLayers();
        randomiseConnectionWeights();
        randomiseNeuronThresholds();
    }

    private void connectLayers(){
        IntStream.range(0, layers.size() - 1).forEach(i -> connectLayersNeurons(layers.get(i), layers.get(i + 1)));
    }

    private void randomiseConnectionWeights(){

    }

    private void randomiseNeuronThresholds(){

    }

    private void connectLayersNeurons(Layer leftLayer, Layer rightLayer){
        for(Neuron leftLayerNeuron : leftLayer.getNeurons()){
            for(Neuron rightLayerNeuron : rightLayer.getNeurons()){
                rightLayerNeuron.addInputConnection(leftLayerNeuron.getOutputConnection());
            }
        }
    }

    private boolean isValid(int inputs, int outputs, int... neuronsByLayer) {
        return inputs > 0 && outputs > 0 &&
            !IntStream.range(0, neuronsByLayer.length).anyMatch(i -> neuronsByLayer[i] < 1);
    }

    public ArrayList<Layer> getLayersCopy() {
        return new ArrayList<>(layers);
    }
}
