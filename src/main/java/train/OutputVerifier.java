package train;

import component.value.TransputValue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class OutputVerifier {
    private static final double MAX_DISTANCE = 1;

    public static double getOutputValuesMachRate(List<ArrayList<TransputValue>> networkOutputValues, List<ArrayList<TransputValue>> expectedOutputValues){
        assert networkOutputValues.size() == expectedOutputValues.size();
        return IntStream.range(0, networkOutputValues.size())
                .mapToDouble(i -> getOutputValuesMachRate(networkOutputValues.get(i), expectedOutputValues.get(i)))
                .sum() / networkOutputValues.size();
    }

    public static double getOutputValuesMachRate(ArrayList<TransputValue> networkOutputValues, ArrayList<TransputValue> expectedOutputValues){
        assert networkOutputValues.size() == expectedOutputValues.size();
        return IntStream.range(0, networkOutputValues.size())
                .mapToDouble(i -> getOutputValueMatchRate(networkOutputValues.get(i), expectedOutputValues.get(i)))
                .sum() / networkOutputValues.size();
    }

    public static double getOutputValueMatchRate(TransputValue networkOutputValue, TransputValue expectedOutputValue){
        return MAX_DISTANCE - Math.abs(networkOutputValue.getNormalized() - expectedOutputValue.getNormalized());
    }

}
