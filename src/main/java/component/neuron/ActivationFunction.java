package component.neuron;

import component.value.normalized.LayerBias;
import component.value.normalized.NeuronThreshold;

import java.util.List;

class ActivationFunction {
    static double calculateNormalized(List<Double> values, LayerBias layerBias, NeuronThreshold neuronThreshold){
        double calc = values.stream().mapToDouble(Double::doubleValue).sum() / values.size();
        double result = calc + layerBias.getNormalized() > 1 ? 1 : calc;

        return result >= neuronThreshold.getNormalized() ? result : 0;
    }
}
