package network;

import component.Connection;
import component.ConnectionWeight;
import component.neuron.Neuron;
import component.value.Weight;
import exception.InvalidNetworkParametersException;
import exception.ValueNotInRangeException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
    private final Layer inputLayer;
    private final Layer outputLayer;
    private final Access input;
    private final Access output;
    private final ArrayList<Layer> layers = new ArrayList<>();

    public Network(Access input, Access output, int... neuronsByLayer) throws InvalidNetworkParametersException {
        this.input = input;
        this.output = output;

        if(!isValid(input.size(), output.size(), neuronsByLayer)){
            String invalidParametersMessage = "Cannot construct network with given parameters: " +
                "inputs = " + input.size() + " outputs = " + output.size() +
                " neuronsByLayer = " + String.join(",", Arrays.toString(neuronsByLayer));
            throw new InvalidNetworkParametersException(invalidParametersMessage);
        }

        inputLayer = new Layer(input.size());
        outputLayer = new Layer(output.size());
        ArrayList<Layer> hiddenLayers = createHiddenLayers(neuronsByLayer);

        layers.add(inputLayer);
        layers.addAll(hiddenLayers);
        layers.add(outputLayer);

        createInputConnections(inputLayer);
        connectLayers(layers);
    }

    private void createInputConnections(Layer inputLayer){
        inputLayer.getNeurons().forEach(neuron -> neuron.addInputConnection(new Connection()));
    }

    public Access getOutput(final Access input){
        // TODO: add check if input is same as used for network creation
        assert input.size() == inputLayer.getNeurons().size() && this.input.equals(input);
        IntStream.range(0, input.size())
                .forEach(i -> inputLayer.getNeurons().get(i).getInputConnections().get(0).setNormalized(input.getAccessValues().get(i).getNormalized()));
        fire();
        return output;
    }

    private void updateOutput() {
        IntStream.range(0, output.size())
                .forEach(i -> output.getAccessValues().get(i)
                        .setFromNormalizedValue(
                                outputLayer.getNeurons().get(i)
                                        .getOutputConnection()
                                        .getNormalizedValue()));
    }

    private ArrayList<Layer> createHiddenLayers(int... neuronsByLayer){
        return IntStream.of(neuronsByLayer)
            .mapToObj(Layer::new)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public void fire(){
        fireLayersNeurons();
        updateOutput();
    }

    private void fireLayersNeurons(){
        layers.forEach(Layer::fireNeurons);
    }

    private void connectLayers(ArrayList<Layer> layers){
        IntStream.range(0, layers.size() - 1)
            .forEach(i -> connectLayersNeurons(layers.get(i), layers.get(i + 1)));
    }

    public void randomiseConnectionWeights(){
        Random random = new Random();

        for(Layer layer : layers){
            for(Neuron neuron : layer.getNeurons()){
                for(ConnectionWeight cw : neuron.getInputConnectionWeights()){
                    try {
                        cw.getWeight().set(Weight.MAX_WEIGHT * random.nextDouble());
                    } catch (ValueNotInRangeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        System.out.println();
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
            IntStream.range(0, neuronsByLayer.length).noneMatch(i -> neuronsByLayer[i] < 1);
    }
}
