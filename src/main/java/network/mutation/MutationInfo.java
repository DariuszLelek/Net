package network.mutation;

import component.value.normalized.Originator;
import component.value.normalized.type.Type;

public class MutationInfo {
    private final Originator originator;
    private final double oldNormalizedValue;
    private final double newNormalizedValue;

    public MutationInfo(Originator originator, double oldNormalizedValue, double newNormalizedValue) {
        this.originator = originator;
        this.oldNormalizedValue = oldNormalizedValue;
        this.newNormalizedValue = newNormalizedValue;
    }

    public double getOldNormalizedValue() {
        return oldNormalizedValue;
    }

    public double getNewNormalizedValue() {
        return newNormalizedValue;
    }

    public Originator getOriginator() {
        return originator;
    }

    @Override
    public String toString() {
        return String.format("MutationInfo{%-" + Type.size + "s, oldNormalizedValue=%.6f, newNormalizedValue=%.6f}",
                originator.getType().toString(), oldNormalizedValue, newNormalizedValue);
    }
}
