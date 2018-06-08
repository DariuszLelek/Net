package network;

import component.Transput;
import component.value.TransputValueHelper;
import component.value.normalized.Connection;
import component.neuron.Neuron;
import component.value.normalized.Bias;
import component.value.normalized.Threshold;
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

        randomiseConnectionsWeight(layers);
        randomiseLayersBias(layers);
        randomiseNeuronsThreshold(layers);
    }

    private void createInputConnections(Layer inputLayer) {
        inputLayer.getNeurons().forEach(neuron -> neuron.addInputConnection(new Connection()));
    }

    public Transput getInput() {
        return input;
    }

    public Transput getOutput(final Transput input) throws InvalidNetworkInputException {
        if (TransputValueHelper.sameTransputValuesDefinitions(input, this.input)) {
            setInputLayerValues(input);
            fire();
            return output;
        } else {
            throw new InvalidNetworkInputException("Input object used for network creation [" + this.input +
            "] is not equal to the object passed [" + input + "]. Cannot get Network output for not matching input.");
        }
    }

    private void setInputLayerValues(final Transput input){
        IntStream.range(0, input.size())
                .forEach(i -> getInputLayer().getNeurons().get(i)
                        .getInputConnections().get(0)
                        .setFromNormalized(input.getTransputValues().get(i).getNormalized()));
    }

    public ArrayList<Layer> getLayers() {
        return layers;
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

    private void randomiseLayersBias(final List<Layer> layers){
        layers.forEach(layer -> layer.getBias().setFromNormalized(Bias.MAX_VALUE * random.nextDouble()));
    }

    private void randomiseNeuronsThreshold(final List<Layer> layers){
        layers.forEach(layer ->
                layer.getNeurons().forEach(neuron ->
                        neuron.getThreshold().setFromNormalized(Threshold.MAX_VALUE * random.nextDouble())));
    }

    private void connectLayersNeurons(Layer leftLayer, Layer rightLayer) {
        leftLayer.getNeurons()
                .forEach(leftLayerNeuron -> rightLayer.getNeurons()
                        .forEach(rightLayerNeuron -> rightLayerNeuron.addInputConnection(leftLayerNeuron.getOutputConnection())));
    }

    public List<Neuron> getNeurons(){
        return layers.stream().flatMap(layer -> layer.getNeurons().stream()).collect(Collectors.toList());
    }

    private boolean isValid(int inputs, int outputs, int... neuronsByLayer) {
        return inputs > 0 && outputs > 0 &&
            IntStream.range(0, neuronsByLayer.length).noneMatch(i -> neuronsByLayer[i] < 1);
    }

    @Override
    public String toString() {
        return "Network{layers=" + layers + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Network)) return false;

        Network network = (Network) o;

        return getInput().equals(network.getInput()) && output.equals(network.output)
                && layers.equals(network.layers) && random.equals(network.random)
                && Arrays.equals(neuronsByLayer, network.neuronsByLayer);
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
