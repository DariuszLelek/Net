package train;


import exception.InvalidTransputDataException;
import component.Transput;

import java.util.ArrayList;
import java.util.List;

public class TrainData {
    private final List<InputOutputPair> inputOutputPairs = new ArrayList<>();

    public void addTrainData(Transput input, Transput output) throws InvalidTransputDataException {
        InputOutputPair pair = new InputOutputPair(input, output);

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
