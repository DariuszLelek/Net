import component.value.Value;
import exception.InvalidNetworkParametersException;
import network.Network;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test Class");

        List<Value> inputs = new ArrayList<>();
        List<Value> outputs = new ArrayList<>();

        try {
            Network network = new Network(1, 1, 5, 10, 0);
        } catch (InvalidNetworkParametersException e) {
            e.printStackTrace();
        }

//        network.getLayersCopy();
    }
}

