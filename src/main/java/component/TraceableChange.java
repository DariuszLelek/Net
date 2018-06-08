package component;

public interface TraceableChange {
    String getId();
    void setOriginal(double originalNormalizedValue);

    default boolean match(TraceableChange other){
        return this.getId().equals(other.getId());
    }
}
