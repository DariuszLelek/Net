package train;

import component.value.TransputValue;
import exception.InvalidTransputDataException;
import network.Transput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainData {
    private final Map<ArrayList<TransputValue>, ArrayList<TransputValue>> data = new HashMap<>();

    public void addTrainData(ArrayList<TransputValue> inputValues, ArrayList<TransputValue> expectedOutputValues) throws InvalidTransputDataException {
        data.put(inputValues, expectedOutputValues);
    }

    public Map<ArrayList<TransputValue>, ArrayList<TransputValue>> getData() {
        return data;
    }

    public int size(){
        return data.size();
    }

}
