package train;


import exception.InvalidTransputDataException;
import network.Transput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainData {
    private final List<InputOutputPair> inputOutputPairs = new ArrayList<>();

    public void addTrainData(Transput input, Transput output) throws InvalidTransputDataException {
        InputOutputPair pair = new InputOutputPair(input.copy(), output.copy());

        if(!inputOutputPairs.contains(pair)){
            inputOutputPairs.add(pair);
        }else{
            // TODO add logger
            System.out.println("Attempting to add data for same input twice!");
        }
    }

    public List<InputOutputPair> getInputOutputPairs() {
        return inputOutputPairs;
    }

    public int size(){
        return inputOutputPairs.size();
    }

}
