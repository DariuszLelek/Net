package component.neuron;

import component.value.normalized.Connection;
import component.ConnectionWeight;
import component.value.normalized.Bias;
import component.value.normalized.Threshold;
import component.value.normalized.Weight;

import java.util.*;
import java.util.stream.Collectors;

public class Neuron{
    private final String id;

    private final ArrayList<ConnectionWeight> inputConnectionWeights;
    private final Connection outputConnection;
    private final Threshold threshold;
    private final Bias bias;

    public Neuron() {
        id = "NO_ID";
        threshold = new Threshold();
        bias = new Bias();
        outputConnection = new Connection();
        inputConnectionWeights = new ArrayList<>();
    }

    public Neuron(String id, final Bias bias) {
        this.id = id;
        this.bias = bias;
        threshold = new Threshold();
        outputConnection = new Connection();
        inputConnectionWeights = new ArrayList<>();
    }

    public void fire() {
        outputConnection.setFromNormalized(ActivationFunction.calculateNormalized(
                inputConnectionWeights.stream()
                .map(cw -> cw.getConnection().getNormalized() * cw.getWeight().getNormalized())
                .collect(Collectors.toList()), bias, threshold));
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

    public Threshold getThreshold() {
        return threshold;
    }

    public Bias getBias() {
        return bias;
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
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neuron)) return false;

        Neuron neuron = (Neuron) o;

        if (!id.equals(neuron.id)) return false;
        if (!getInputConnectionWeights().equals(neuron.getInputConnectionWeights())) return false;
        if (!getOutputConnection().equals(neuron.getOutputConnection())) return false;
        return getBias().equals(neuron.getBias());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + getInputConnectionWeights().hashCode();
        result = 31 * result + getOutputConnection().hashCode();
        result = 31 * result + getBias().hashCode();
        return result;
    }
}
