package component;

import component.TraceableChange;
import network.Network;

import java.util.Collection;

public interface TraceableChangeSupplier{
    Collection<? extends TraceableChange> get(Network network);
}
