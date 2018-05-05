package component;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private final List<Connection> inputs = new ArrayList<>();
    // TODO: implement output part
//    private final Connection output = new Connection();

    public List<Connection> getInputs() {
        return inputs;
    }

//    public Connection getOutput() {
//        return output;
//    }
}
