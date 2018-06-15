package network;

import component.Transput;
import component.value.TransputValue;
import exception.InvalidNetworkParametersException;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class TestNetworkDataProvider {
    private static final double DEFAULT_MAX = 20.0d;
    private static final double DEFAULT_MIN = 0.0d;

    private static final int[] DEFAULT_NEURONS_BY_LAYER = new int[]{2, 2};

    public static Network getDefaultTestNetwork(int inputValues, int outputValues) throws InvalidNetworkParametersException {
        return new Network(getDefaultTransput(inputValues), getDefaultTransput(outputValues), DEFAULT_NEURONS_BY_LAYER);
    }

    public static double getRandomDoubleForTransputValue(TransputValue tranpsutValue){
        return ThreadLocalRandom.current().nextDouble(tranpsutValue.getMin(), tranpsutValue.getMax());
    }

    private static Transput getDefaultTransput(int transputValuesSize) {
        return new Transput(new ArrayList<TransputValue>() {{
            IntStream.range(0, transputValuesSize).forEach(i -> add(getDefaultTransputValue()));
        }});
    }

    private static TransputValue getDefaultTransputValue(){
        return new TransputValue(UUID.randomUUID().toString(), DEFAULT_MIN, DEFAULT_MAX);
    }
}
