package network;

import component.Transput;
import component.value.TransputValue;
import exception.InvalidNetworkParametersException;

import java.util.ArrayList;
import java.util.UUID;

public class TestNetworkProvider {
    private static final double DEFAULT_MAX = 20.0d;
    private static final double DEFAULT_MIN = 0.0d;

    private static final int[] DEFAULT_NEURONS_BY_LAYER = new int[]{2, 2};

    public static Network getDefaultTestNetwork() throws InvalidNetworkParametersException {
        return new Network(getDefaultTransput(), getDefaultTransput(), DEFAULT_NEURONS_BY_LAYER);
    }

    private static Transput getDefaultTransput() {
        return new Transput(new ArrayList<TransputValue>() {{
            add(getDefaultTransputValue());
            add(getDefaultTransputValue());
        }});
    }

    private static TransputValue getDefaultTransputValue(){
        return new TransputValue(UUID.randomUUID().toString(), DEFAULT_MIN, DEFAULT_MAX);
    }
}
