package train;

import component.value.TransputValue;
import exception.InvalidNetworkInputException;
import network.Network;
import network.Transput;

import java.util.ArrayList;
import java.util.Map;

public class Trainer {
    private static Network bestNetwork;

    public static Network train(Network network, TrainData trainData, int iterations){
        bestNetwork = network.copy();

        while (iterations-- > 0){
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
        double value = 0.0;

        ArrayList<ArrayList<TransputValue>> networkOutputValues = new ArrayList<>();
        ArrayList<ArrayList<TransputValue>> expectedOutputValues = new ArrayList<>();

        Transput input = network.getInput();
        for(InputOutputPair pair : trainData.getInputOutputPairs()){
            networkOutputValues.clear();
            expectedOutputValues.clear();

            input.updateTransputValues(pair.getInput().getTransputValues());
            try {
                networkOutputValues.add(network.getOutput(input).getTransputValues());
                expectedOutputValues.add(pair.getOutput().getTransputValues());
            } catch (InvalidNetworkInputException e) {
                e.printStackTrace();
            }

            value += OutputVerifier.getOutputValuesMachRate(networkOutputValues, expectedOutputValues);
        }

        return value / trainData.size();
    }

}
