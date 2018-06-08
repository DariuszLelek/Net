package train;

import component.value.TransputValue;
import exception.InvalidNetworkInputException;
import exception.TraceableNotFoundException;
import network.Network;
import component.Transput;
import network.mutation.NetworkMutationInfo;
import network.mutation.NetworkMutator;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Trainer {

    public static Network train(final Network network, TrainData trainData, int iterations){
        IntStream.range(0, iterations).forEach(i -> processTrainCycle(network, trainData));
        return network;
    }
    private static Network processTrainCycle(Network network, TrainData trainData){
        double result = getNetworkResult(network, trainData);

        NetworkMutationInfo mutationInfo = NetworkMutator.mutate(network);

        double postResult = getNetworkResult(network, trainData);

        if(postResult < result){
            try {
                NetworkMutator.removeMutation(mutationInfo);
            } catch (TraceableNotFoundException e) {
                // TODO add logger
            }
        }

        double postResult2 = getNetworkResult(network, trainData);

        return network;
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
