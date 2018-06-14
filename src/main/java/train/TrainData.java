package train;


import exception.InvalidTransputDataException;
import component.Transput;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class TrainData {
    private final Set<InputOutputPair> inputOutputPairs = new HashSet<>();

    public void addTrainData(Transput input, Transput output) throws InvalidTransputDataException {
        inputOutputPairs.add(new InputOutputPair(input, output));
    }

    public Collection<InputOutputPair> getInputOutputPairs() {
        return inputOutputPairs;
    }

    public int size(){
        return inputOutputPairs.size();
    }

}
