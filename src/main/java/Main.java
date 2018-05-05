import component.Connection;
import component.Value;
import network.Network;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test Class");

        List<Value> inputs = new ArrayList<>();
        List<Value> outputs = new ArrayList<>();

        Network network = new Network(inputs, outputs, 5, 10);

//        network.getLayers();
    }
}

