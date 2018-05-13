package network;

import component.value.normalized.Connection;
import component.neuron.Neuron;
import component.value.normalized.Bias;
import component.value.normalized.Weight;
import exception.InvalidNetworkInputException;
import exception.InvalidNetworkParametersException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
    private final Transput input;
    private final Transput output;
    private final ArrayList<Layer> layers = new ArrayList<>();
    private final Random random = new Random();

    public Network(Transput input, Transput output, int[] neuronsByLayer) throws InvalidNetworkParametersException {
        this.input = input;
        this.output = output;

        if (!isValid(input.size(), output.size(), neuronsByLayer)) {
            String invalidParametersMessage = "Cannot construct network with given parameters: " +
                "inputs = " + input.size() + " outputs = " + output.size() +
                " neuronsByLayer = " + String.join(",", Arrays.toString(neuronsByLayer));
            throw new InvalidNetworkParametersException(invalidParametersMessage);
        }

        layers.add(new Layer(String.valueOf(layers.size()) ,input.size()));
        layers.addAll(createHiddenLayers(neuronsByLayer));
        layers.add(new Layer(String.valueOf(layers.size()), output.size()));

        createInputConnections(getInputLayer());
        connectLayers(layers);

        randomiseConnectionsWeight();
        randomiseNeuronsBias();
    }

    private void createInputConnections(Layer inputLayer) {
        inputLayer.getNeurons().forEach(neuron -> neuron.addInputConnection(new Connection()));
    }

    public Transput getOutput(final Transput input) throws InvalidNetworkInputException {
        if (this.input.equals(input)) {
            IntStream.range(0, input.size())
                .forEach(i -> getInputLayer().getNeurons().get(i)
                    .getInputConnections().get(0)
                    .setFromNormalized(input.getTransputValues().get(i).getNormalized()));
            fire();
            return output;
        } else {
            throw new InvalidNetworkInputException("Input object used for network creation [" + this.input +
            "] is not equal to the object passed [" + input + "]. Cannot get Network output for not matching input.");
        }
    }

    private Layer getInputLayer(){
        return layers.get(0);
    }

    private Layer getOutputLayer(){
        return layers.get(layers.size() - 1);
    }

    private void updateOutput() {
        IntStream.range(0, output.size())
            .forEach(i -> output.getTransputValues().get(i)
                .setFromNormalizedValue(
                    getOutputLayer().getNeurons().get(i)
                        .getOutputConnection()));
    }

    private ArrayList<Layer> createHiddenLayers(int... neuronsByLayer) {
        return IntStream.of(neuronsByLayer)
            .mapToObj(neurons -> new Layer(String.valueOf(layers.size()), neurons))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Layer> getLayersCopy() {
        return new ArrayList<>(layers);
    }

    public void fire() {
        fireLayersNeurons();
        updateOutput();
    }

    private void fireLayersNeurons() {
        layers.forEach(Layer::fireNeurons);
    }

    private void connectLayers(ArrayList<Layer> layers) {
        IntStream.range(0, layers.size() - 1)
            .forEach(i -> connectLayersNeurons(layers.get(i), layers.get(i + 1)));
    }

    public void randomiseConnectionsWeight() {
        layers.forEach(layer ->
            layer.getNeurons().forEach(neuron ->
                neuron.getInputConnectionWeights()
                    .forEach(cw -> cw.getWeight().setFromNormalized(Weight.MAX_VALUE * random.nextDouble()))));
    }

    private void randomiseNeuronsBias() {
        layers.forEach(layer ->
            layer.getNeurons().forEach(neuron ->
                neuron.getBias().setFromNormalized(Bias.MAX_VALUE * random.nextDouble())));
    }

    private void connectLayersNeurons(Layer leftLayer, Layer rightLayer) {
        for (Neuron leftLayerNeuron : leftLayer.getNeurons()) {
            for (Neuron rightLayerNeuron : rightLayer.getNeurons()) {
                rightLayerNeuron.addInputConnection(leftLayerNeuron.getOutputConnection());
            }
        }
    }

    private boolean isValid(int inputs, int outputs, int... neuronsByLayer) {
        return inputs > 0 && outputs > 0 &&
            IntStream.range(0, neuronsByLayer.length).noneMatch(i -> neuronsByLayer[i] < 1);
    }
}
