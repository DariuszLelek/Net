package train;

import network.Transput;

public class InputOutputPair {
    private final Transput input;
    private final Transput output;

    public InputOutputPair(Transput input, Transput output) {
        this.input = input;
        this.output = output;
    }

    public Transput getInput() {
        return input;
    }

    public Transput getOutput() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InputOutputPair)) return false;

        InputOutputPair that = (InputOutputPair) o;

        return (getInput() != null ? getInput().equals(that.getInput()) : that.getInput() == null) && (getOutput() != null ? getOutput().equals(that.getOutput()) : that.getOutput() == null);
    }

    @Override
    public int hashCode() {
        int result = getInput() != null ? getInput().hashCode() : 0;
        result = 31 * result + (getOutput() != null ? getOutput().hashCode() : 0);
        return result;
    }
}
