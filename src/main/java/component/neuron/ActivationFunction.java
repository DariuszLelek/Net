package component.neuron;

import component.value.Bias;

import java.util.List;

public class ActivationFunction {

    public static double calculateNormalized(List<Double> values, Bias bias){
        return Math.min(values.stream().mapToDouble(Double::doubleValue).sum() / values.size() + bias.getNormalized(), 1);
    }
}
