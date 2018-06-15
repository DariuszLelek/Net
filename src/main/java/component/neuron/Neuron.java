package component.neuron;

import component.value.normalized.Connection;
import component.ConnectionWeight;
import component.value.normalized.LayerBias;
import component.value.normalized.NeuronThreshold;
import component.value.normalized.Weight;

import java.util.*;
import java.util.stream.Collectors;

public class Neuron{
    private final String id;

    private final ArrayList<ConnectionWeight> inputConnectionWeights;
    private final Connection outputConnection;
    private final NeuronThreshold neuronThreshold;
    private final LayerBias layerBias;

    public Neuron() {
        id = "NO_ID";
        neuronThreshold = new NeuronThreshold();
        layerBias = new LayerBias();
        outputConnection = new Connection();
        inputConnectionWeights = new ArrayList<>();
    }

    public Neuron(String id, final LayerBias layerBias) {
        this.id = id;
        this.layerBias = layerBias;
        neuronThreshold = new NeuronThreshold();
        outputConnection = new Connection();
        inputConnectionWeights = new ArrayList<>();
    }

    public void fire() {
        outputConnection.setFromNormalized(ActivationFunction.calculateNormalized(
                inputConnectionWeights.stream()
                .map(cw -> cw.getConnection().getNormalized() * cw.getWeight().getNormalized())
                .collect(Collectors.toList()), layerBias, neuronThreshold));
    }

    public ArrayList<ConnectionWeight> getInputConnectionWeights() {
        return inputConnectionWeights;
    }

    public List<Connection> getInputConnections() {
        return inputConnectionWeights.stream().map(ConnectionWeight::getConnection).collect(Collectors.toList());
    }

    public ConnectionWeight getConnectionWeight(Connection connection){
        return inputConnectionWeights.stream()
            .filter(cw -> connection.equals(cw.getConnection()))
            .findFirst()
            .orElse(ConnectionWeight.EMPTY);
    }

    public NeuronThreshold getNeuronThreshold() {
        return neuronThreshold;
    }

    public LayerBias getLayerBias() {
        return layerBias;
    }

    public Connection getOutputConnection() {
        return outputConnection;
    }

    public void addInputConnection(Connection connection){
        addInputConnectionWeight(new ConnectionWeight(connection, new Weight()));
    }

    public void addInputConnectionWeight(ConnectionWeight connectionWeight){
        inputConnectionWeights.add(connectionWeight);
    }

    @Override
    public String toString() {
        return "Neuron{" +
            "id=" + id +
            ", inputConnectionWeights=" + inputConnectionWeights +
            ", outputConnection=" + outputConnection +
            ", neuronThreshold=" + neuronThreshold +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neuron)) return false;

        Neuron neuron = (Neuron) o;

        return id.equals(neuron.id) && getInputConnectionWeights().equals(neuron.getInputConnectionWeights())
                && getOutputConnection().equals(neuron.getOutputConnection()) && getLayerBias().equals(neuron.getLayerBias());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + getInputConnectionWeights().hashCode();
        result = 31 * result + getOutputConnection().hashCode();
        result = 31 * result + getLayerBias().hashCode();
        return result;
    }
}
