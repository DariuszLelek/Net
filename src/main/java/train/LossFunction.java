package train;

import component.Transput;
import component.value.TransputValue;
import exception.InvalidNetworkInputException;
import network.Network;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class LossFunction {
    private static final double MAX_DISTANCE = 1;
    private static final double THRESHOLD = 0.9;

    public static double calculate(Network network, TrainData trainData){
        double value = 0.0;

        Collection<ActualExpectedPair> result = new ArrayList<>();

        Transput input = network.getInput();
        Transput output;

        for(InputOutputPair pair : trainData.getInputOutputPairs()){

            input.updateTransputValues(pair.getInput().getTransputValues());
            try {
                output = network.getOutput(input);
                assert pair.getOutput().getTransputValues().size() == output.getTransputValues().size();

                for (int i = 0; i < output.getTransputValues().size(); i++) {
                    double actual = output.getTransputValues().get(i).getValue();
                    double expected = pair.getOutput().getTransputValues().get(i).getValue();

                    value += Math.pow(actual - expected, 2);
                }
            } catch (InvalidNetworkInputException e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    public static double getValuesDiffSquaresSum(List<ArrayList<TransputValue>> networkOutputValues, List<ArrayList<TransputValue>> expectedOutputValues){
        assert networkOutputValues.size() == expectedOutputValues.size();
        return IntStream.range(0, networkOutputValues.size())
                .mapToDouble(i -> getValuesDiffSquare(networkOutputValues.get(i), expectedOutputValues.get(i)))
                .sum();
    }

    public static double getValuesDiffSquare(ArrayList<TransputValue> networkOutputValues, ArrayList<TransputValue> expectedOutputValues){
        assert networkOutputValues.size() == expectedOutputValues.size();
        return IntStream.range(0, networkOutputValues.size())
                .mapToDouble(i -> getOutputValueMatchRate(networkOutputValues.get(i), expectedOutputValues.get(i)))
                .sum() / networkOutputValues.size();
    }

    public static double getOutputValueMatchRate(TransputValue networkOutputValue, TransputValue expectedOutputValue){
        return getThresholdValue(MAX_DISTANCE - Math.abs(networkOutputValue.getNormalized() - expectedOutputValue.getNormalized()));
    }

    private static double getThresholdValue(double value){
        return value > THRESHOLD ? value : 0;
    }

    private static class ActualExpectedPair{
        private final double actual;
        private final double expected;

        public ActualExpectedPair(double actual, double expected) {
            this.actual = actual;
            this.expected = expected;
        }

        public double getActual() {
            return actual;
        }

        public double getExpected() {
            return expected;
        }
    }
}
