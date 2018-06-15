package component;

import component.value.normalized.NormalizedValue;
import network.Network;

import java.util.Collection;

public interface NormalizedValueSupplier {
    Collection<? extends NormalizedValue> get(Network network);
}
