package network;

import component.Connection;
import component.ConnectionWeight;
import component.neuron.ActivationFunctionType;
import component.neuron.Neuron;
import component.value.NormalizedValue;
import component.value.Value;
import component.value.Weight;
import exception.InvalidNetworkParametersException;
import exception.ValueNotInRangeException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Network {
    private final Layer inputLayer;
    private final Layer outputLayer;
    private final ArrayList<Layer> layers = new ArrayList<>();

    public Network(int inputs, int outputs, int... neuronsByLayer) throws InvalidNetworkParametersException {
        if(!isValid(inputs, outputs, neuronsByLayer)){
            String invalidParametersMessage = "Cannot construct network with given parameters: " +
                "inputs = " + inputs + " outputs = " + outputs +
                " neuronsByLayer = " + String.join(",", Arrays.toString(neuronsByLayer));
            throw new InvalidNetworkParametersException(invalidParametersMessage);
        }

        inputLayer = new Layer(inputs, ActivationFunctionType.SIGMOID);
        outputLayer = new Layer(outputs, ActivationFunctionType.NORMALIZED);
        ArrayList<Layer> hiddenLayers = createHiddenLayers(neuronsByLayer);

        layers.add(inputLayer);
        layers.addAll(hiddenLayers);
        layers.add(outputLayer);

        connectLayers(layers);
        insertInputsConnections(inputs, NormalizedValue.class);
    }

    private ArrayList<Layer> createHiddenLayers(int... neuronsByLayer){
        return IntStream.of(neuronsByLayer)
            .mapToObj(i -> new Layer(i, ActivationFunctionType.NORMALIZED))
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Value> produceOutputValues(ArrayList<Value> inputValues){
        assert inputValues.size() == inputLayer.getNeurons().get(0).getInputConnections().size();

        setInputNeuronsConnectionValues(inputValues);
        fireLayersNeurons();

        return getOutputValues();
    }

    public List<Value> getOutputValues(){
        return outputLayer.getNeuronsOutputConnections().stream()
            .map(Connection::getValue)
            .collect(Collectors.toList());
    }

    private void setInputNeuronsConnectionValues(ArrayList<Value> inputs){
        inputLayer.getNeurons().forEach(neuron -> IntStream.range(0, inputs.size())
            .forEach(i -> neuron.getInputConnectionWeights().get(i).getConnection().setValue(inputs.get(i).getNormalized())));
    }

    private void fireLayersNeurons(){
        layers.forEach(Layer::fireNeurons);
    }

    private void connectLayers(ArrayList<Layer> layers){
        IntStream.range(0, layers.size() - 1)
            .forEach(i -> connectLayersNeurons(layers.get(i), layers.get(i + 1)));
    }

    private void insertInputsConnections(int inputs, Class<? extends Value<Double>> inputValueClass){
        for (Neuron neuron : inputLayer.getNeurons()) {
            try {
                for (int i = 0; i < inputs; i++) {
                    Weight weight = new Weight();
                    weight.setValue(Weight.MAX_WEIGHT);
                    neuron.addInputConnectionWeight(new ConnectionWeight(new Connection(inputValueClass.newInstance()), weight));
                }
            } catch (InstantiationException | IllegalAccessException | ValueNotInRangeException e) {
                e.printStackTrace();
            }
        }
    }

    public void randomiseConnectionWeights(){
        Random random = new Random();

        for(Layer layer : layers){
            for(Neuron neuron : layer.getNeurons()){
                for(ConnectionWeight cw : neuron.getInputConnectionWeights()){
                    try {
                        cw.getWeight().setValue(Weight.MAX_WEIGHT * random.nextDouble());
                    } catch (ValueNotInRangeException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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


    public ArrayList<Layer> getLayers() {
        return new ArrayList<>(layers);
    }
}
