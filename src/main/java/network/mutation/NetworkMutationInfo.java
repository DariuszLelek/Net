package network.mutation;

import component.TraceableChange;
import network.Network;

public class NetworkMutationInfo {
    private final Network network;
    private final MutationType type;
    private final double oldNormalizedValue;
    private final TraceableChange originator;

    public NetworkMutationInfo(Network network, MutationType type, double oldNormalizedValue, TraceableChange originator) {
        this.network = network;
        this.type = type;
        this.oldNormalizedValue = oldNormalizedValue;
        this.originator = originator;
    }

    public Network getNetwork() {
        return network;
    }

    public MutationType getType() {
        return type;
    }

    public double getOldNormalizedValue() {
        return oldNormalizedValue;
    }

    public TraceableChange getOriginator() {
        return originator;
    }
}
