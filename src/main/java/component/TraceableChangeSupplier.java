package component;

import network.Network;

import java.util.Collection;

public interface TraceableChangeSupplier{
    Collection<? extends TraceableChange> get(Network network);
}
