package component.neuron;

import component.value.normalized.Connection;
import component.ConnectionWeight;
import component.value.normalized.Bias;
import component.value.normalized.Weight;

import java.util.*;
import java.util.stream.Collectors;

public class Neuron {
    private final String id;

    private final ArrayList<ConnectionWeight> inputConnectionWeights = new ArrayList<>();
    private final Connection outputConnection = new Connection();
    private final Bias bias = new Bias();

    public Neuron() {
        id = "NO_ID";
    }

    public Neuron(String id) {
        this.id = id;
    }

    public void fire() {
        outputConnection.setFromNormalized(ActivationFunction.calculateNormalized(
            inputConnectionWeights.stream()
                .map(cw -> cw.getConnection().getNormalized() * cw.getWeight().getNormalized())
                .collect(Collectors.toList()), bias));
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
            ", bias=" + bias +
            '}';
    }
}
