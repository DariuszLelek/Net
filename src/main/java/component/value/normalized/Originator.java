package component.value.normalized;

import component.value.normalized.type.NormalizedType;

public interface Originator extends NormalizedType {
    String getId();

    default boolean match(Originator other){
        return this.getId().equals(other.getId());
    }
}
