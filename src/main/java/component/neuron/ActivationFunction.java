package component.neuron;

import component.value.normalized.Bias;
import component.value.normalized.Threshold;

import java.util.List;

public class ActivationFunction {

    public static double calculateNormalized(List<Double> values, Bias bias, Threshold threshold){
        double calc = values.stream().mapToDouble(Double::doubleValue).sum() / values.size();
        double result = calc + bias.getNormalized() > 1 ? 1 : calc;

        return result >= threshold.getNormalized() ? result : 0;
    }
}
