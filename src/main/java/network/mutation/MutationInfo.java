package network.mutation;

import component.TraceableChange;
import component.value.normalized.NormalizedValue;
import network.Network;

public class MutationInfo {
    private final MutationType type;
    private final double oldNormalizedValue;
    private final double newNormalizedValue;
    private final TraceableChange originator;

    public MutationInfo(MutationType type, double oldNormalizedValue, NormalizedValue originator, double newNormalizedValue) {
        this.type = type;
        this.oldNormalizedValue = oldNormalizedValue;
        this.newNormalizedValue = newNormalizedValue;
        this.originator = originator;
    }

    public MutationType getType() {
        return type;
    }

    public double getOldNormalizedValue() {
        return oldNormalizedValue;
    }

    public double getNewNormalizedValue() {
        return newNormalizedValue;
    }

    public TraceableChange getOriginator() {
        return originator;
    }

    @Override
    public String toString() {
        return "MutationInfo{" +
                "type=" + type +
                ", oldNormalizedValue=" + oldNormalizedValue +
                ", newNormalizedValue=" + newNormalizedValue +
                ", originator=" + originator.getId() +
                '}';
    }
}
