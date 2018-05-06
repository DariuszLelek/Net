package component.neuron;

import component.Connection;
import component.ConnectionWeight;
import component.value.Weight;

import java.util.*;
import java.util.stream.Collectors;

public class Neuron {
    private final ActivationFunctionType activationFunctionType;
    private final ArrayList<ConnectionWeight> inputConnectionWeights = new ArrayList<>();
    private final Connection outputConnection = new Connection();

    public Neuron(ActivationFunctionType activationFunctionType) {
        this.activationFunctionType = activationFunctionType;
    }

    public void fire() {
        outputConnection.setValue(ActivationFunction.calculateByNeuronType(
            activationFunctionType,
            inputConnectionWeights.stream()
                .map(cw -> cw.getConnection().getValue() * cw.getWeight().getNormalized())
                .collect(Collectors.toList())));
    }

    public ArrayList<ConnectionWeight> getInputConnectionWeights() {
        return inputConnectionWeights;
    }

    public List<Connection> getInputConnections() {
        return inputConnectionWeights.stream().map(ConnectionWeight::getConnection).collect(Collectors.toList());
    }

    public List<Weight> getInputWeights(){
        return inputConnectionWeights.stream().map(ConnectionWeight::getWeight).collect(Collectors.toList());
    }

    public ConnectionWeight getConnectionWeight(Connection connection){
        return inputConnectionWeights.stream()
            .filter(cw -> connection.equals(cw.getConnection()))
            .findFirst()
            .orElse(ConnectionWeight.EMPTY);
    }

    public Connection getOutputConnection() {
        return outputConnection;
    }

    public void addInputConnection(Connection connection){
        inputConnectionWeights.add(new ConnectionWeight(connection, new Weight()));
    }
}
