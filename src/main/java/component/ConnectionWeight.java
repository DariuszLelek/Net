package component;

import component.value.NormalizedValue;
import component.value.Weight;

public class ConnectionWeight {
    public static final ConnectionWeight EMPTY = new ConnectionWeight(new Connection(), new Weight());

    private final Connection connection;
    private final Weight weight;

    public ConnectionWeight(Connection connection, Weight weight) {
        this.connection = connection;
        this.weight = weight;
    }

    public Connection getConnection() {
        return connection;
    }

    public Weight getWeight() {
        return weight;
    }
}
