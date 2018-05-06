package component;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private final List<Connection> inputConnections = new ArrayList<>();
    private final Connection outputConnection = new Connection();

    public List<Connection> getInputConnections() {
        return inputConnections;
    }

    public Connection getOutputConnection() {
        return outputConnection;
    }

    public void addInputConnection(Connection connection){
        inputConnections.add(connection);
    }
}
