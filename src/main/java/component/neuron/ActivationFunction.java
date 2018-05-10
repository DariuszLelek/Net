package component.neuron;

import component.value.NormalizedValue;

import java.util.List;

public class ActivationFunction {

    public static double calculateNormalized(List<Double> values){
        return values.stream().mapToDouble(Double::doubleValue).sum() / values.size();
    }

    private static double calculateSigmoid(double value){
        return 1 / (1 + Math.pow(Math.E, -value));
    }
}
