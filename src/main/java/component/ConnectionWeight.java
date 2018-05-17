package component;

import component.value.normalized.Connection;
import component.value.normalized.Weight;

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

    public ConnectionWeight copy(){
        return new ConnectionWeight(connection.copy(), weight.copy());
    }

    @Override
    public String toString() {
        return "ConnectionWeight{" + connection + ", " + weight + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConnectionWeight)) return false;

        ConnectionWeight that = (ConnectionWeight) o;

        if (!getConnection().equals(that.getConnection())) return false;
        return getWeight().equals(that.getWeight());
    }

    @Override
    public int hashCode() {
        int result = getConnection().hashCode();
        result = 31 * result + getWeight().hashCode();
        return result;
    }
}
