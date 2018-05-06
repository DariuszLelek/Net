package component.neuron;

import java.util.List;

public class ActivationFunction {

    public static double calculateByNeuronType(ActivationFunctionType functionType, List<Double> values){
        switch (functionType){
            case SIGMOID:
                return values.stream().mapToDouble(ActivationFunction::calculateSigmoid).sum() / values.size();
            case NORMALIZED:
            default:
                return values.stream().mapToDouble(Double::doubleValue).sum() / values.size();
        }
    }

    private static double calculateSigmoid(double value){
        return 1 / (1 + Math.pow(Math.E, -value));
    }
}
