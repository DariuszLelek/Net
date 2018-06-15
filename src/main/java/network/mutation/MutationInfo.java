package network.mutation;

import component.value.normalized.Originator;

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

    public Originator getNormalizedValue() {
        return originator;
    }

    @Override
    public String toString() {
        return "MutationInfo{" + originator.getType() +
                ", oldNormalizedValue=" + oldNormalizedValue +
                ", newNormalizedValue=" + newNormalizedValue +
                '}';
    }
}
