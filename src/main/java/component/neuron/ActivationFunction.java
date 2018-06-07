package component.neuron;

import component.value.normalized.Bias;

import java.util.List;

public class ActivationFunction {

    public static double calculateNormalized(List<Double> values, Bias bias){
        double calc = values.stream().mapToDouble(Double::doubleValue).sum() / values.size();
        return calc + bias.getNormalized() > 1 ? 1 : calc;
    }
}
