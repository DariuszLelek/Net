package network.mutation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum MutationType {
    CONNECTION_WEIGHT, NEURON_THRESHOLD, LAYER_BIAS;

    private static final List<MutationType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();

    public static MutationType getRandom(){
        if (new Random().nextInt(2) == 1){
            return NEURON_THRESHOLD;
        }else{
            return CONNECTION_WEIGHT;
        }

//        return CONNECTION_WEIGHT;
//        return VALUES.get(new Random().nextInt(SIZE));
    }
}
