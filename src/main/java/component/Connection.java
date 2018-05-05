package component;

public class Connection {
    private final Value value;
    private float weight = 0;

    public Connection(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
