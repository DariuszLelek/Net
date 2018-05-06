package component;

public class Connection {
    private Value value;
    private float weight = 0;

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
