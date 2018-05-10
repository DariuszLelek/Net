package component.neuron;

import component.Connection;
import component.ConnectionWeight;
import component.value.Bias;
import component.value.NormalizedValue;
import component.value.Value;
import component.value.Weight;

import java.util.*;
import java.util.stream.Collectors;

public class Neuron {
    private final ArrayList<ConnectionWeight> inputConnectionWeights = new ArrayList<>();
    private final Connection outputConnection = new Connection();
    private final Bias bias = new Bias();

    public void fire() {
        outputConnection.setNormalized(ActivationFunction.calculateNormalized(
            inputConnectionWeights.stream()
                .map(cw -> cw.getConnection().getNormalizedValue().getNormalized() * cw.getWeight().getNormalized())
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
}
