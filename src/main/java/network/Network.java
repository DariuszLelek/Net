package network;

import component.value.TransputValueHelper;
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
    private final ArrayList<Layer> layers;
    private final Random random;
    private final int[] neuronsByLayer;

    private Network(Network another){
        input = another.input.copy();
        output = another.output.copy();
        neuronsByLayer = another.neuronsByLayer;
        random = another.random;
        layers = new ArrayList<>(another.layers.size());

        IntStream.range(0, another.layers.size()).forEach(i -> layers.add(i, another.layers.get(i).copy()));
    }

    public Network(Transput input, Transput output, int[] neuronsByLayer) throws InvalidNetworkParametersException {
        this.input = input;
        this.output = output;
        this.neuronsByLayer = neuronsByLayer;
        random = new Random();
        layers = new ArrayList<>();

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

        List<Layer> layersExcludingInputLayer = getLayersExcludingInputLyer(layers);
        randomiseConnectionsWeight(layersExcludingInputLayer);
        randomiseNeuronsBias(layersExcludingInputLayer);
    }

    private List<Layer> getLayersExcludingInputLyer(final ArrayList<Layer> layers){
        return new ArrayList<>(layers.subList(Math.min(1, layers.size()), layers.size()));
    }

    private void createInputConnections(Layer inputLayer) {
        inputLayer.getNeurons().forEach(neuron -> neuron.addInputConnection(new Connection()));
    }

    public Transput getInput() {
        return input;
    }

    public Transput getOutput(final Transput input) throws InvalidNetworkInputException {
        if (TransputValueHelper.sameTransputValuesDefinitions(input, this.input)) {
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

    private void fire() {
        fireLayersNeurons();
        updateOutput();
    }

    public Network copy(){
        return new Network(this);
    }

    // TODO change this
    public void train(){
        randomiseConnectionsWeight(layers);
        randomiseNeuronsBias(layers);
    }

    private void fireLayersNeurons() {
        layers.forEach(Layer::fireNeurons);
    }

    private void connectLayers(ArrayList<Layer> layers) {
        IntStream.range(0, layers.size() - 1)
            .forEach(i -> connectLayersNeurons(layers.get(i), layers.get(i + 1)));
    }

    private void randomiseConnectionsWeight(final List<Layer> layers) {
        layers.forEach(layer ->
            layer.getNeurons().forEach(neuron ->
                neuron.getInputConnectionWeights()
                    .forEach(cw -> cw.getWeight().setFromNormalized(Weight.MAX_VALUE * random.nextDouble()))));
    }

    private void randomiseNeuronsBias(final List<Layer> layers) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Network)) return false;

        Network network = (Network) o;

        if (!getInput().equals(network.getInput())) return false;
        if (!output.equals(network.output)) return false;
        if (!layers.equals(network.layers)) return false;
        if (!random.equals(network.random)) return false;
        return Arrays.equals(neuronsByLayer, network.neuronsByLayer);
    }

    @Override
    public int hashCode() {
        int result = getInput().hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + layers.hashCode();
        result = 31 * result + random.hashCode();
        result = 31 * result + Arrays.hashCode(neuronsByLayer);
        return result;
    }
}
