package network;

import component.Connection;
import component.neuron.ActivationFunctionType;
import component.neuron.Neuron;
import exception.InvalidNetworkParametersException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
    private final ArrayList<Layer> layers;
    private final List<Connection> layersConnections;

    public Network(int inputs, int outputs, int... neuronsByLayer) throws InvalidNetworkParametersException {
        if(!isValid(inputs, outputs, neuronsByLayer)){
            String invalidParametersMessage = "Cannot construct network with given parameters: " +
                "inputs = " + inputs + " outputs = " + outputs +
                " neuronsByLayer = " + String.join(",", Arrays.toString(neuronsByLayer));
            throw new InvalidNetworkParametersException(invalidParametersMessage);
        }

        layers = createLayers(inputs, outputs, neuronsByLayer);

        // TODO: ceck if can use one collections to store all connections
        layersConnections = getLayersConnections(layers);

        initialize();
    }

    private ArrayList<Layer> createLayers(int inputs, int outputs, int... neuronsByLayer){
        ArrayList<Layer> layers = new ArrayList<>(neuronsByLayer.length + 2);

        layers.add(new Layer(inputs, ActivationFunctionType.SIGMOID));
        layers.addAll(IntStream.of(neuronsByLayer).mapToObj(i -> new Layer(i, ActivationFunctionType.NORMALIZED)).collect(Collectors.toList()));
        layers.add(new Layer(outputs, ActivationFunctionType.NORMALIZED));

        return layers;
    }

    // TODO: getOutputNeuronsConnections will get one connection per neuron, use neuron inputs instead
    private List<Connection> getLayersConnections(ArrayList<Layer> layers){
        return IntStream.range(0, layers.size() - 1)
            .mapToObj(layers::get)
            .map(Layer::getOutputNeuronsConnections)
            .collect(ArrayList::new, List::addAll, List::addAll);
    }

    private void initialize(){
        connectLayers();
        randomiseConnectionWeights();
        randomiseNeuronThresholds();
    }

    private void connectLayers(){
        IntStream.range(0, layers.size() - 1)
            .forEach(i -> connectLayersNeurons(layers.get(i), layers.get(i + 1)));
    }

    private void randomiseConnectionWeights(){
        Random random = new Random();
//        layersConnections.forEach(connection -> connection. setWeight(Connection.VALUE_MAX * random.nextDouble()));
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

    public List<Connection> getLayersConnectionsCopy() {
        return new ArrayList<>(layersConnections);
    }
}
