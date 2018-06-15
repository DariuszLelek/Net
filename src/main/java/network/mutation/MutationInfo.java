package network.mutation;

import component.value.normalized.Originator;
import component.value.normalized.type.Type;

public class MutationInfo {
    private final Originator originator;
    private final double oldNormalizedValue;

    public MutationInfo(Originator originator, double oldNormalizedValue) {
        this.originator = originator;
        this.oldNormalizedValue = oldNormalizedValue;
    }

    public double getOldNormalizedValue() {
        return oldNormalizedValue;
    }

    public Originator getOriginator() {
        return originator;
    }

    @Override
    public String toString() {
        return String.format("MutationInfo{%-" + Type.size + "s, oldNormalizedValue=%.6f}",
                originator.getType().toString(), oldNormalizedValue);
    }
}
