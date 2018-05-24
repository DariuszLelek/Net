package train;

import component.value.TransputValue;
import exception.InvalidNetworkInputException;
import network.Network;
import network.Transput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Trainer {
    private static Network bestNetwork;

    public static Network train(Network network, TrainData trainData, int iterations){
        bestNetwork = network.copy();

        while (iterations -- > 0){
            network = processTrainCycle(network, trainData);
        }

        return bestNetwork;
    }

    private static Network processTrainCycle(Network network, TrainData trainData){
        double preTrainResult = getNetworkResult(network, trainData);
        Network networkCopy = network.copy();
        networkCopy.train();
        double ostTrainResult = getNetworkResult(networkCopy, trainData);

        if(ostTrainResult > preTrainResult){
            bestNetwork = networkCopy;
            return networkCopy;
        }else{
            return network;
        }
    }

    private static double getNetworkResult(Network network, TrainData trainData){
        ArrayList<ArrayList<TransputValue>> networkOutputValues = new ArrayList<>();
        ArrayList<ArrayList<TransputValue>> expectedOutputValues = new ArrayList<>();

        Transput input = network.getInput();
        for(Map.Entry<ArrayList<TransputValue>, ArrayList<TransputValue>> pair : trainData.getData().entrySet()){
            input.updateTransputValues(pair.getKey());
            try {
                networkOutputValues.add(network.getOutput(input).getTransputValues());
                expectedOutputValues.add(pair.getValue());
            } catch (InvalidNetworkInputException e) {
                e.printStackTrace();
            }
        }

        return OutputVerifier.getOutputValuesMachRate(networkOutputValues, expectedOutputValues);
    }

}
